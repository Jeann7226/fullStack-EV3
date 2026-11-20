package fullStack_EV3.service;

import fullStack_EV3.dto.CrearOrdenRequestDto;
import fullStack_EV3.dto.DetalleOrdenItemDto;
import fullStack_EV3.dto.OrdenResponseDto;
import fullStack_EV3.model.*;
import fullStack_EV3.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrdenService {

    private final OrdenRepository ordenRepository;
    private final DetalleRepository detalleRepository;
    private final UsuarioRepository usuarioRepository;
    private final EstadoRepository estadoRepository;
    private final PagoRepository pagoRepository;
    private final BoletaRepository boletaRepository;
    private final ProductoRepository productoRepository;

    public OrdenService(OrdenRepository ordenRepository,
                        DetalleRepository detalleRepository,
                        UsuarioRepository usuarioRepository,
                        EstadoRepository estadoRepository,
                        PagoRepository pagoRepository,
                        BoletaRepository boletaRepository,
                        ProductoRepository productoRepository) {
        this.ordenRepository = ordenRepository;
        this.detalleRepository = detalleRepository;
        this.usuarioRepository = usuarioRepository;
        this.estadoRepository = estadoRepository;
        this.pagoRepository = pagoRepository;
        this.boletaRepository = boletaRepository;
        this.productoRepository = productoRepository;
    }

    @Transactional(readOnly = true)
    public List<OrdenResponseDto> getAll() {
        return ordenRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<OrdenResponseDto> getByUserRun(String run) {
        // TODO: [FULL STACK] Validar que si el usuario que consulta tiene rol CLIENTE,
        // solo pueda consultar su propio RUN y no el de otros clientes.
        return ordenRepository.findByUsuarioRun(run).stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public OrdenResponseDto create(CrearOrdenRequestDto request, String authenticatedEmail) {
        // Determinamos el usuario comprador
        Usuario usuario;
        if (request.getUserRun() != null && !request.getUserRun().isBlank()) {
            usuario = usuarioRepository.findById(request.getUserRun())
                    .orElseGet(() -> usuarioRepository.findByCorreo(authenticatedEmail)
                            .orElseThrow(() -> new RuntimeException("Usuario no encontrado")));
        } else {
            usuario = usuarioRepository.findByCorreo(authenticatedEmail)
                    .orElseThrow(() -> new RuntimeException("Usuario autenticado no encontrado: " + authenticatedEmail));
        }

        // Estado inicial de la orden
        Estado estado = estadoRepository.findByNombreEstado("PENDIENTE")
                .or(() -> estadoRepository.findByNombreEstado("PAGADO"))
                .orElseGet(() -> estadoRepository.save(new Estado("PENDIENTE")));

        // Generación de identificador único de orden
        String orderId = "ORD-" + System.currentTimeMillis() % 1000000;

        Orden orden = new Orden();
        orden.setIdOrden(orderId);
        orden.setUsuario(usuario);
        orden.setEstado(estado);
        orden.setFecha(LocalDateTime.now());
        orden.setTotal(request.getTotal());

        // TODO: [FULL STACK] 1. DESCUENTO DE STOCK
        // Iterar sobre request.getItems(), buscar cada Producto en productoRepository,
        // verificar que producto.getStock() >= item.getCantidad(), descontar producto.setStock(...)
        // y lanzar excepcion si no hay stock suficiente para evitar compras invalidas.

        // TODO: [FULL STACK] 2. GENERACION DE REGISTRO DE PAGO
        // Si el metodo de pago viene presente (request.getMetodoPago()), instanciar y guardar
        // un objeto Pago (metodoPago, fechaPago = now, montoTotal = request.getTotal()) en pagoRepository
        // y asignarlo a la orden: orden.setPago(pagoGuardado);

        // TODO: [FULL STACK] 3. EMISION DE BOLETA ASOCIADA
        // Tras registrar el pago exitoso, crear una Boleta en boletaRepository con numeroBoleta correlativo
        // unico (ej: "BOL-" + System.currentTimeMillis()), fechaEmision y vincularla al Pago.

        Orden ordenGuardada = ordenRepository.save(orden);

        // Guardar detalles de la orden
        List<Detalle> detalles = new ArrayList<>();
        if (request.getItems() != null) {
            for (DetalleOrdenItemDto item : request.getItems()) {
                if (item.getIdProducto() != null) {
                    Producto producto = productoRepository.findById(item.getIdProducto()).orElse(null);
                    if (producto != null) {
                        Detalle detalle = new Detalle();
                        detalle.setOrden(ordenGuardada);
                        detalle.setProducto(producto);
                        detalle.setCantidad(item.getCantidad() != null ? item.getCantidad() : 1);
                        detalle.setPrecioUnitario(item.getPrecio() != null ? item.getPrecio() : producto.getPrecio());
                        detalles.add(detalle);
                    }
                }
            }
        }

        if (!detalles.isEmpty()) {
            detalleRepository.saveAll(detalles);
            ordenGuardada.setDetalles(detalles);
        }

        return mapToResponse(ordenGuardada);
    }

    private OrdenResponseDto mapToResponse(Orden o) {
        OrdenResponseDto dto = new OrdenResponseDto();
        dto.setIdOrden(o.getIdOrden());
        dto.setId(o.getIdOrden());
        dto.setUserRun(o.getUsuario() != null ? o.getUsuario().getRun() : null);
        dto.setCustomerName(o.getUsuario() != null ? (o.getUsuario().getNombre() + " " + o.getUsuario().getApellidos()) : "Cliente");
        dto.setFecha(o.getFecha());
        dto.setTotal(o.getTotal());
        dto.setEstado(o.getEstado() != null ? o.getEstado().getNombreEstado() : "PENDIENTE");
        dto.setIdPago(o.getPago() != null ? o.getPago().getIdPago() : null);

        List<DetalleOrdenItemDto> items = new ArrayList<>();
        if (o.getDetalles() != null) {
            for (Detalle d : o.getDetalles()) {
                if (d.getProducto() != null) {
                    items.add(new DetalleOrdenItemDto(
                            d.getProducto().getIdProducto(),
                            d.getProducto().getTitulo(),
                            d.getPrecioUnitario(),
                            d.getCantidad(),
                            d.getProducto().getTalla()
                    ));
                }
            }
        }
        dto.setItems(items);
        return dto;
    }
}
