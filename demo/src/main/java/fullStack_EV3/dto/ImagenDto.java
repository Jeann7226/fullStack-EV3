package fullStack_EV3.dto;

public class ImagenDto {

    private Long idImagen;
    private String urlImagen;
    private Boolean principal;

    public ImagenDto() {
    }

    public ImagenDto(Long idImagen, String urlImagen, Boolean principal) {
        this.idImagen = idImagen;
        this.urlImagen = urlImagen;
        this.principal = principal;
    }

    public ImagenDto(String urlImagen, Boolean principal) {
        this.urlImagen = urlImagen;
        this.principal = principal;
    }

    public Long getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(Long idImagen) {
        this.idImagen = idImagen;
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
