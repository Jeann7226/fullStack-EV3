package fullStack_EV3.service;

import fullStack_EV3.dto.ImagenDto;
import fullStack_EV3.dto.ProductoRequestDto;
import fullStack_EV3.dto.ProductoResponseDto;
import fullStack_EV3.model.Categoria;
import fullStack_EV3.model.CategoriaProducto;
import fullStack_EV3.model.Imagen;
import fullStack_EV3.model.Marca;
import fullStack_EV3.model.Producto;
import fullStack_EV3.repository.CategoriaProductoRepository;
import fullStack_EV3.repository.CategoriaRepository;
import fullStack_EV3.repository.ImagenRepository;
import fullStack_EV3.repository.MarcaRepository;
import fullStack_EV3.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final MarcaRepository marcaRepository;
    private final CategoriaRepository categoriaRepository;
    private final CategoriaProductoRepository categoriaProductoRepository;
    private final ImagenRepository imagenRepository;

    public ProductoService(ProductoRepository productoRepository,
                           MarcaRepository marcaRepository,
                           CategoriaRepository categoriaRepository,
                           CategoriaProductoRepository categoriaProductoRepository,
                           ImagenRepository imagenRepository) {
        this.productoRepository = productoRepository;
        this.marcaRepository = marcaRepository;
        this.categoriaRepository = categoriaRepository;
        this.categoriaProductoRepository = categoriaProductoRepository;
        this.imagenRepository = imagenRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductoResponseDto> getAll() {
        return productoRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductoResponseDto getById(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        return mapToResponse(producto);
    }

    @Transactional
    public ProductoResponseDto create(ProductoRequestDto dto) {
        Marca marca = null;
        if (dto.getIdMarca() != null) {
            marca = marcaRepository.findById(dto.getIdMarca()).orElse(null);
        } else if (dto.getNombreMarca() != null && !dto.getNombreMarca().isBlank()) {
            final String marcaName = dto.getNombreMarca();
            marca = marcaRepository.findByNombreMarca(marcaName)
                    .orElseGet(() -> marcaRepository.save(new Marca(marcaName)));
        }

        Producto producto = new Producto();
        producto.setTitulo(dto.getTitulo());
        producto.setPrecio(dto.getPrecio());
        producto.setDescripcion(dto.getDescripcion());
        producto.setStock(dto.getStock());
        producto.setStockCritico(dto.getStockCritico());
        producto.setTalla(dto.getTalla());
        producto.setMarca(marca);

        Producto saved = productoRepository.save(producto);

        // Procesar imágenes
        procesarImagenes(saved, dto);

        // Procesar categorías
        procesarCategorias(saved, dto);

        return mapToResponse(saved);
    }

    @Transactional
    public ProductoResponseDto update(Long id, ProductoRequestDto dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        producto.setTitulo(dto.getTitulo());
        producto.setPrecio(dto.getPrecio());
        producto.setDescripcion(dto.getDescripcion());
        producto.setStock(dto.getStock());
        producto.setStockCritico(dto.getStockCritico());
        producto.setTalla(dto.getTalla());

        if (dto.getIdMarca() != null) {
            Marca marca = marcaRepository.findById(dto.getIdMarca()).orElse(null);
            producto.setMarca(marca);
        } else if (dto.getNombreMarca() != null && !dto.getNombreMarca().isBlank()) {
            final String marcaName = dto.getNombreMarca();
            Marca marca = marcaRepository.findByNombreMarca(marcaName)
                    .orElseGet(() -> marcaRepository.save(new Marca(marcaName)));
            producto.setMarca(marca);
        }

        // Limpiar imágenes existentes y reasignar
        imagenRepository.deleteAll(producto.getImagenes());
        producto.getImagenes().clear();
        procesarImagenes(producto, dto);

        // Limpiar categorías existentes y reasignar
        categoriaProductoRepository.deleteAll(producto.getCategorias());
        producto.getCategorias().clear();
        procesarCategorias(producto, dto);

        Producto updated = productoRepository.save(producto);
        return mapToResponse(updated);
    }

    @Transactional
    public void delete(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado con ID: " + id);
        }
        productoRepository.deleteById(id);
    }

    private void procesarImagenes(Producto producto, ProductoRequestDto dto) {
        List<Imagen> lista = new ArrayList<>();

        String primaryImg = (dto.getImg() != null && !dto.getImg().isBlank())
                ? dto.getImg()
                : dto.getUrlImagen();

        if (primaryImg != null && !primaryImg.isBlank()) {
            lista.add(new Imagen(producto, primaryImg, true));
        }

        if (dto.getImg2() != null && !dto.getImg2().isBlank()) {
            lista.add(new Imagen(producto, dto.getImg2(), false));
        }

        if (dto.getImagenes() != null) {
            for (ImagenDto imgDto : dto.getImagenes()) {
                if (imgDto.getUrlImagen() != null && !imgDto.getUrlImagen().isBlank()) {
                    lista.add(new Imagen(producto, imgDto.getUrlImagen(), Boolean.TRUE.equals(imgDto.getPrincipal())));
                }
            }
        }

        if (!lista.isEmpty()) {
            imagenRepository.saveAll(lista);
            producto.setImagenes(lista);
        }
    }

    private void procesarCategorias(Producto producto, ProductoRequestDto dto) {
        List<CategoriaProducto> categoriasRelacionadas = new ArrayList<>();

        if (dto.getCategoriasIds() != null) {
            for (Long idCat : dto.getCategoriasIds()) {
                categoriaRepository.findById(idCat).ifPresent(cat ->
                        categoriasRelacionadas.add(new CategoriaProducto(producto, cat))
                );
            }
        }

        if (dto.getCategoriasNombres() != null) {
            for (String nomCat : dto.getCategoriasNombres()) {
                if (nomCat != null && !nomCat.isBlank()) {
                    Categoria cat = categoriaRepository.findByNombreCategoria(nomCat)
                            .orElseGet(() -> categoriaRepository.save(new Categoria(nomCat)));
                    categoriasRelacionadas.add(new CategoriaProducto(producto, cat));
                }
            }
        }

        if (!categoriasRelacionadas.isEmpty()) {
            categoriaProductoRepository.saveAll(categoriasRelacionadas);
            producto.setCategorias(categoriasRelacionadas);
        }
    }

    private ProductoResponseDto mapToResponse(Producto p) {
        ProductoResponseDto dto = new ProductoResponseDto();
        dto.setIdProducto(p.getIdProducto());
        dto.setId(p.getIdProducto());
        dto.setTitulo(p.getTitulo());
        dto.setPrecio(p.getPrecio());
        dto.setDescripcion(p.getDescripcion());
        dto.setStock(p.getStock());
        dto.setStockCritico(p.getStockCritico());
        dto.setTalla(p.getTalla());

        if (p.getMarca() != null) {
            dto.setIdMarca(p.getMarca().getIdMarca());
            dto.setNombreMarca(p.getMarca().getNombreMarca());
        }

        List<ImagenDto> imagenesDto = new ArrayList<>();
        String mainImg = null;
        String secondImg = null;

        if (p.getImagenes() != null && !p.getImagenes().isEmpty()) {
            for (Imagen img : p.getImagenes()) {
                imagenesDto.add(new ImagenDto(img.getIdImagen(), img.getUrlImagen(), img.getPrincipal()));
                if (Boolean.TRUE.equals(img.getPrincipal()) && mainImg == null) {
                    mainImg = img.getUrlImagen();
                } else if (secondImg == null && !img.getUrlImagen().equals(mainImg)) {
                    secondImg = img.getUrlImagen();
                }
            }
            if (mainImg == null && !imagenesDto.isEmpty()) {
                mainImg = imagenesDto.get(0).getUrlImagen();
            }
            if (secondImg == null && imagenesDto.size() > 1) {
                secondImg = imagenesDto.get(1).getUrlImagen();
            }
        }

        dto.setImagenes(imagenesDto);
        dto.setImg(mainImg);
        dto.setImg2(secondImg);

        List<String> categoriasNombres = new ArrayList<>();
        if (p.getCategorias() != null && !p.getCategorias().isEmpty()) {
            for (CategoriaProducto cp : p.getCategorias()) {
                if (cp.getCategoria() != null) {
                    categoriasNombres.add(cp.getCategoria().getNombreCategoria());
                }
            }
            dto.setCategoria(categoriasNombres.get(0));
        }

        dto.setCategorias(categoriasNombres);
        return dto;
    }
}
