package fullStack_EV3.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class ProductoRequestDto {

    @NotBlank(message = "El titulo es obligatorio")
    private String titulo;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "El precio no puede ser negativo")
    private Integer precio;

    private String descripcion;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @NotNull(message = "El stock critico es obligatorio")
    @Min(value = 0, message = "El stock critico no puede ser negativo")
    private Integer stockCritico;

    private String talla;

    private Long idMarca;
    private String nombreMarca;

    private List<Long> categoriasIds = new ArrayList<>();
    private List<String> categoriasNombres = new ArrayList<>();

    // Imagenes (soporta formato lista o atributos simples img/img2/urlImagen)
    private String img;
    private String img2;
    private String urlImagen;
    private List<ImagenDto> imagenes = new ArrayList<>();

    public ProductoRequestDto() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStockCritico() {
        return stockCritico;
    }

    public void setStockCritico(Integer stockCritico) {
        this.stockCritico = stockCritico;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public Long getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Long idMarca) {
        this.idMarca = idMarca;
    }

    public String getNombreMarca() {
        return nombreMarca;
    }

    public void setNombreMarca(String nombreMarca) {
        this.nombreMarca = nombreMarca;
    }

    public List<Long> getCategoriasIds() {
        return categoriasIds;
    }

    public void setCategoriasIds(List<Long> categoriasIds) {
        this.categoriasIds = categoriasIds;
    }

    public List<String> getCategoriasNombres() {
        return categoriasNombres;
    }

    public void setCategoriasNombres(List<String> categoriasNombres) {
        this.categoriasNombres = categoriasNombres;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getUrlImagen() {
        return urlImagen != null && !urlImagen.isBlank() ? urlImagen : img;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public List<ImagenDto> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<ImagenDto> imagenes) {
        this.imagenes = imagenes;
    }
}
