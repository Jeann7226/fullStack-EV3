package fullStack_EV3.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "BOLETA")
public class Boleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_boleta")
    private Long idBoleta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pago")
    private Pago pago;

    @Column(name = "numero_boleta", nullable = false, unique = true)
    private String numeroBoleta;

    @Column(name = "fecha_emision", nullable = false)
    private LocalDateTime fechaEmision;

    @Column(name = "monto_total", nullable = false)
    private Integer montoTotal;

    public Boleta() {
    }

    public Boleta(Long idBoleta, Pago pago, String numeroBoleta, LocalDateTime fechaEmision, Integer montoTotal) {
        this.idBoleta = idBoleta;
        this.pago = pago;
        this.numeroBoleta = numeroBoleta;
        this.fechaEmision = fechaEmision;
        this.montoTotal = montoTotal;
    }

    public Boleta(Pago pago, String numeroBoleta, LocalDateTime fechaEmision, Integer montoTotal) {
        this.pago = pago;
        this.numeroBoleta = numeroBoleta;
        this.fechaEmision = fechaEmision;
        this.montoTotal = montoTotal;
    }

    public Long getIdBoleta() {
        return idBoleta;
    }

    public void setIdBoleta(Long idBoleta) {
        this.idBoleta = idBoleta;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public String getNumeroBoleta() {
        return numeroBoleta;
    }

    public void setNumeroBoleta(String numeroBoleta) {
        this.numeroBoleta = numeroBoleta;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Integer getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Integer montoTotal) {
        this.montoTotal = montoTotal;
    }
}
