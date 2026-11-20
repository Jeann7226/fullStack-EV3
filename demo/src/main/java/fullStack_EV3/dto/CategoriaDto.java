package fullStack_EV3.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoriaDto {

    private Long idCategoria;

    @NotBlank(message = "El nombre de la categoria es obligatorio")
    private String nombreCategoria;

    public CategoriaDto() {
    }

    public CategoriaDto(Long idCategoria, String nombreCategoria) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }
}
