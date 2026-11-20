package fullStack_EV3.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "IMAGEN")
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_imagen")
    private Long idImagen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false)
    @JsonIgnore
    private Producto producto;

    @Column(name = "url_imagen", nullable = false)
    private String urlImagen;

    @Column(name = "principal")
    private Boolean principal;

    public Imagen() {
    }

    public Imagen(Long idImagen, Producto producto, String urlImagen, Boolean principal) {
        this.idImagen = idImagen;
        this.producto = producto;
        this.urlImagen = urlImagen;
        this.principal = principal;
    }

    public Imagen(Producto producto, String urlImagen, Boolean principal) {
        this.producto = producto;
        this.urlImagen = urlImagen;
        this.principal = principal;
    }

    public Long getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(Long idImagen) {
        this.idImagen = idImagen;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public Boolean getPrincipal() {
        return principal;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }
}
