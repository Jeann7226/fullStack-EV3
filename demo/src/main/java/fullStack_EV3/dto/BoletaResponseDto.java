package fullStack_EV3.dto;

import java.time.LocalDateTime;

public class BoletaResponseDto {

    private Long idBoleta;
    private Long idPago;
    private String numeroBoleta;
    private LocalDateTime fechaEmision;
    private Integer montoTotal;

    public BoletaResponseDto() {
    }

    public BoletaResponseDto(Long idBoleta, Long idPago, String numeroBoleta, LocalDateTime fechaEmision, Integer montoTotal) {
        this.idBoleta = idBoleta;
        this.idPago = idPago;
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

    public Long getIdPago() {
        return idPago;
    }

    public void setIdPago(Long idPago) {
        this.idPago = idPago;
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
