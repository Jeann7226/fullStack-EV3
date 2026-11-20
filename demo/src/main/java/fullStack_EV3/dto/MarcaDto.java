package fullStack_EV3.dto;

import jakarta.validation.constraints.NotBlank;

public class MarcaDto {

    private Long idMarca;

    @NotBlank(message = "El nombre de la marca es obligatorio")
    private String nombreMarca;

    public MarcaDto() {
    }

    public MarcaDto(Long idMarca, String nombreMarca) {
        this.idMarca = idMarca;
        this.nombreMarca = nombreMarca;
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
}
