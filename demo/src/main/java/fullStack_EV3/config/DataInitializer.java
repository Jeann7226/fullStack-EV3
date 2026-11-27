package fullStack_EV3.config;

import fullStack_EV3.model.*;
import fullStack_EV3.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final EstadoRepository estadoRepository;
    private final RegionRepository regionRepository;
    private final ComunaRepository comunaRepository;
    private final UsuarioRepository usuarioRepository;
    private final MarcaRepository marcaRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;
    private final ImagenRepository imagenRepository;
    private final CategoriaProductoRepository categoriaProductoRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RolRepository rolRepository,
                           EstadoRepository estadoRepository,
                           RegionRepository regionRepository,
                           ComunaRepository comunaRepository,
                           UsuarioRepository usuarioRepository,
                           MarcaRepository marcaRepository,
                           CategoriaRepository categoriaRepository,
                           ProductoRepository productoRepository,
                           ImagenRepository imagenRepository,
                           CategoriaProductoRepository categoriaProductoRepository,
                           PasswordEncoder passwordEncoder) {
        this.rolRepository = rolRepository;
        this.estadoRepository = estadoRepository;
        this.regionRepository = regionRepository;
        this.comunaRepository = comunaRepository;
        this.usuarioRepository = usuarioRepository;
        this.marcaRepository = marcaRepository;
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
        this.imagenRepository = imagenRepository;
        this.categoriaProductoRepository = categoriaProductoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        initRoles();
        initEstados();
        initRegionesYComunas();
        initMarcasYCategorias();
        initUsuarios();
        initProductos();
    }

    private void initRoles() {
        crearRolSiNoExiste("ADMIN");
        crearRolSiNoExiste("VENDEDOR");
        crearRolSiNoExiste("CLIENTE");
    }

    private Rol crearRolSiNoExiste(String nombreRol) {
        return rolRepository.findByNombreRol(nombreRol)
                .orElseGet(() -> rolRepository.save(new Rol(nombreRol)));
    }

    private void initEstados() {
        crearEstadoSiNoExiste("PENDIENTE");
        crearEstadoSiNoExiste("PAGADO");
        crearEstadoSiNoExiste("ENVIADO");
        crearEstadoSiNoExiste("ENTREGADO");
        crearEstadoSiNoExiste("CANCELADO");
    }

    private void crearEstadoSiNoExiste(String nombreEstado) {
        if (estadoRepository.findByNombreEstado(nombreEstado).isEmpty()) {
            estadoRepository.save(new Estado(nombreEstado));
        }
    }

    private void initRegionesYComunas() {
        if (regionRepository.count() > 0) {
            return;
        }

        crearRegionConComunas("Región Metropolitana de Santiago", List.of("Santiago", "Puente Alto", "Maipú", "La Florida", "Las Condes", "Providencia", "Ñuñoa"));
        crearRegionConComunas("Región de Valparaíso", List.of("Valparaíso", "Viña del Mar", "Quilpué", "Villa Alemana"));
        crearRegionConComunas("Región del Biobío", List.of("Concepción", "Talcahuano", "Los Ángeles", "San Pedro de la Paz"));
        crearRegionConComunas("Región de La Araucanía", List.of("Temuco", "Padre Las Casas", "Villarrica"));
        crearRegionConComunas("Región de Ñuble", List.of("Chillán", "San Carlos", "Bulnes"));
        crearRegionConComunas("Región de Coquimbo", List.of("La Serena", "Coquimbo", "Ovalle"));
        crearRegionConComunas("Región de Antofagasta", List.of("Antofagasta", "Calama", "Tocopilla"));
        crearRegionConComunas("Región de Tarapacá", List.of("Iquique", "Alto Hospicio"));
        crearRegionConComunas("Región del Maule", List.of("Talca", "Curicó", "Linares"));
        crearRegionConComunas("Región de Los Lagos", List.of("Puerto Montt", "Osorno", "Castro"));
        crearRegionConComunas("Región de Magallanes", List.of("Punta Arenas", "Puerto Natales"));
        crearRegionConComunas("Región de Arica y Parinacota", List.of("Arica", "Putre"));
        crearRegionConComunas("Región de O'Higgins", List.of("Rancagua", "San Fernando"));
    }

    private void crearRegionConComunas(String nombreRegion, List<String> comunas) {
        Region region = regionRepository.save(new Region(nombreRegion));
        for (String c : comunas) {
            comunaRepository.save(new Comuna(region, c));
        }
    }

    private void initMarcasYCategorias() {
        crearMarcaSiNoExiste("Nike");
        crearMarcaSiNoExiste("Adidas");
        crearMarcaSiNoExiste("Puma");
        crearMarcaSiNoExiste("Under Armour");

        crearCategoriaSiNoExiste("Selecciones Nacionales");
        crearCategoriaSiNoExiste("Clubes Chilenos");
        crearCategoriaSiNoExiste("Clubes Internacionales");
    }

    private Marca crearMarcaSiNoExiste(String nombre) {
        return marcaRepository.findByNombreMarca(nombre)
                .orElseGet(() -> marcaRepository.save(new Marca(nombre)));
    }

    private Categoria crearCategoriaSiNoExiste(String nombre) {
        return categoriaRepository.findByNombreCategoria(nombre)
                .orElseGet(() -> categoriaRepository.save(new Categoria(nombre)));
    }

    private void initUsuarios() {
        Rol rolAdmin = rolRepository.findByNombreRol("ADMIN").orElse(null);
        Rol rolVendedor = rolRepository.findByNombreRol("VENDEDOR").orElse(null);
        Rol rolCliente = rolRepository.findByNombreRol("CLIENTE").orElse(null);

        Comuna comunaSantiago = comunaRepository.findByNombreComuna("Santiago").orElse(null);
        Comuna comunaVina = comunaRepository.findByNombreComuna("Viña del Mar").orElse(null);
        Comuna comunaMaipu = comunaRepository.findByNombreComuna("Maipú").orElse(null);

        if (!usuarioRepository.existsByRun("111111111")) {
            Usuario admin = new Usuario("111111111", comunaSantiago, rolAdmin, "Admin", "Sistema",
                    "admin@duoc.cl", LocalDate.of(1980, 1, 1), "Calle 1", passwordEncoder.encode("123456"));
            usuarioRepository.save(admin);
        }

        if (!usuarioRepository.existsByRun("222222222")) {
            Usuario vendedor = new Usuario("222222222", comunaVina, rolVendedor, "Vendedor", "Uno",
                    "vendedor1@duoc.cl", LocalDate.of(1990, 5, 15), "Avenida 2", passwordEncoder.encode("123456"));
            usuarioRepository.save(vendedor);
        }

        if (!usuarioRepository.existsByRun("444444444")) {
            Usuario cliente = new Usuario("444444444", comunaMaipu, rolCliente, "Cliente", "A",
                    "clienteA@gmail.com", LocalDate.of(1995, 12, 10), "Calle 4", passwordEncoder.encode("123456"));
            usuarioRepository.save(cliente);
        }
    }

    private void initProductos() {
        if (productoRepository.count() > 0) {
            return;
        }

        Marca nike = crearMarcaSiNoExiste("Nike");
        Marca adidas = crearMarcaSiNoExiste("Adidas");
        Marca puma = crearMarcaSiNoExiste("Puma");

        Categoria selecciones = crearCategoriaSiNoExiste("Selecciones Nacionales");
        Categoria clubesChilenos = crearCategoriaSiNoExiste("Clubes Chilenos");

        crearProductoConDatos(nike, "Camiseta Brasil Local 2026", 49990,
                "Polera oficial de la seleccion brasilena con tejido transpirable.", 50, 5, "M",
                "/assets/images/brasil/brasil1.jpeg", "/assets/images/brasil/brasil2.png", selecciones);

        crearProductoConDatos(adidas, "Camiseta Universidad de Chile Local", 44990,
                "Camiseta oficial azul con cuello v y detalles clasicos del club.", 45, 5, "L",
                "/assets/images/u_de_chile/lau1.webp", "/assets/images/u_de_chile/lau2.webp", clubesChilenos);

        crearProductoConDatos(nike, "Camiseta Francia Local 2026", 49990,
                "Edicion oficial de la seleccion francesa con detalles en dorado.", 30, 5, "M",
                "/assets/images/francia/francia1.png", "/assets/images/francia/francia2.png", selecciones);

        crearProductoConDatos(adidas, "Camiseta España Local 2026", 49990,
                "Camiseta de futbol inspirada en la camiseta local de Espana.", 20, 5, "L",
                "/assets/images/españa/españa2.jpg", "/assets/images/españa/españa3.jpg", selecciones);

        crearProductoConDatos(adidas, "Camiseta Argentina Tres Estrellas", 52990,
                "Polera albiceleste con el parche de campeon y tecnologia dry fit.", 15, 2, "XL",
                "/assets/images/argentina/argentina1.jpg", "/assets/images/argentina/argentina2.jpg", selecciones);

        crearProductoConDatos(adidas, "Camiseta Colo-Colo Local 2026", 49990,
                "Camiseta blanca tradicional con el king de fondo.", 10, 3, "L",
                "/assets/images/zorras/zorra1.jpg", "/assets/images/zorras/zorra2.jpg", clubesChilenos);
    }

    private void crearProductoConDatos(Marca marca, String titulo, Integer precio, String descripcion,
                                       Integer stock, Integer stockCritico, String talla,
                                       String img1, String img2, Categoria categoria) {
        Producto p = new Producto(null, marca, titulo, precio, descripcion, stock, stockCritico, talla);
        Producto saved = productoRepository.save(p);

        List<Imagen> imagenes = new ArrayList<>();
        if (img1 != null) {
            imagenes.add(new Imagen(saved, img1, true));
        }
        if (img2 != null) {
            imagenes.add(new Imagen(saved, img2, false));
        }
        imagenRepository.saveAll(imagenes);
        saved.setImagenes(imagenes);

        if (categoria != null) {
            CategoriaProducto cp = new CategoriaProducto(saved, categoria);
            categoriaProductoRepository.save(cp);
            saved.setCategorias(List.of(cp));
        }
    }
}
