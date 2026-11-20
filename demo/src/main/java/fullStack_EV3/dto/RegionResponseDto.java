package fullStack_EV3.dto;

public class RegionResponseDto {

    private Long idRegion;
    private String nombreRegion;

    public RegionResponseDto() {
    }

    public RegionResponseDto(Long idRegion, String nombreRegion) {
        this.idRegion = idRegion;
        this.nombreRegion = nombreRegion;
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
