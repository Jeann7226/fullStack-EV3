package fullStack_EV3.dto;

public class ComunaResponseDto {

    private Long idComuna;
    private String nombreComuna;
    private Long idRegion;
    private String nombreRegion;

    public ComunaResponseDto() {
    }

    public ComunaResponseDto(Long idComuna, String nombreComuna, Long idRegion, String nombreRegion) {
        this.idComuna = idComuna;
        this.nombreComuna = nombreComuna;
        this.idRegion = idRegion;
        this.nombreRegion = nombreRegion;
    }

    public Long getIdComuna() {
        return idComuna;
    }

    public void setIdComuna(Long idComuna) {
        this.idComuna = idComuna;
    }

    public String getNombreComuna() {
        return nombreComuna;
    }

    public void setNombreComuna(String nombreComuna) {
        this.nombreComuna = nombreComuna;
    }

    public Long getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(Long idRegion) {
        this.idRegion = idRegion;
    }

    public String getNombreRegion() {
        return nombreRegion;
    }

    public void setNombreRegion(String nombreRegion) {
        this.nombreRegion = nombreRegion;
    }
}
