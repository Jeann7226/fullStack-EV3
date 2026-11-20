package fullStack_EV3.dto;

import java.util.ArrayList;
import java.util.List;

public class DetalleOrdenItemDto {

    private Long idProducto;
    private String titulo;
    private Integer precio;
    private Integer cantidad;
    private String talla;

    public DetalleOrdenItemDto() {
    }

    public DetalleOrdenItemDto(Long idProducto, String titulo, Integer precio, Integer cantidad, String talla) {
        this.idProducto = idProducto;
        this.titulo = titulo;
        this.precio = precio;
        this.cantidad = cantidad;
        this.talla = talla;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
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

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }
}
