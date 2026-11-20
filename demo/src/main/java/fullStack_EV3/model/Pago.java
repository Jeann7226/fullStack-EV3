package fullStack_EV3.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "PAGO")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Long idPago;

    @Column(name = "metodo_pago", nullable = false)
    private String metodoPago;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDateTime fechaPago;

    @Column(name = "monto_total", nullable = false)
    private Integer montoTotal;

    public Pago() {
    }

    public Pago(Long idPago, String metodoPago, LocalDateTime fechaPago, Integer montoTotal) {
        this.idPago = idPago;
        this.metodoPago = metodoPago;
        this.fechaPago = fechaPago;
        this.montoTotal = montoTotal;
    }

    public Pago(String metodoPago, LocalDateTime fechaPago, Integer montoTotal) {
        this.metodoPago = metodoPago;
        this.fechaPago = fechaPago;
        this.montoTotal = montoTotal;
    }

    public Long getIdPago() {
        return idPago;
    }

    public void setIdPago(Long idPago) {
        this.idPago = idPago;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Integer getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Integer montoTotal) {
        this.montoTotal = montoTotal;
    }
}
