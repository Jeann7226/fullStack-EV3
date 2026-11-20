package fullStack_EV3.model;

import jakarta.persistence.*;

@Entity
@Table(name = "COMUNAS")
public class Comuna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comuna")
    private Long idComuna;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_region", nullable = false)
    private Region region;

    @Column(name = "nombre_comuna", nullable = false)
    private String nombreComuna;

    public Comuna() {
    }

    public Comuna(Long idComuna, Region region, String nombreComuna) {
        this.idComuna = idComuna;
        this.region = region;
        this.nombreComuna = nombreComuna;
    }

    public Comuna(Region region, String nombreComuna) {
        this.region = region;
        this.nombreComuna = nombreComuna;
    }

    public Long getIdComuna() {
        return idComuna;
    }

    public void setIdComuna(Long idComuna) {
        this.idComuna = idComuna;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getNombreComuna() {
        return nombreComuna;
    }

    public void setNombreComuna(String nombreComuna) {
        this.nombreComuna = nombreComuna;
    }
}
