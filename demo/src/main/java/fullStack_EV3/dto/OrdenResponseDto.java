package fullStack_EV3.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrdenResponseDto {

    private String id; // Alias para compatibilidad con el frontend
    private String idOrden;
    private String userRun;
    private String customerName;
    private LocalDateTime fecha;
    private Integer total;
    private String estado;
    private Long idPago;
    private List<DetalleOrdenItemDto> items = new ArrayList<>();

    public OrdenResponseDto() {
    }

    public String getId() {
        return id != null ? id : idOrden;
    }

    public void setId(String id) {
        this.id = id;
        this.idOrden = id;
    }

    public String getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(String idOrden) {
        this.idOrden = idOrden;
        this.id = idOrden;
    }

    public String getUserRun() {
        return userRun;
    }

    public void setUserRun(String userRun) {
        this.userRun = userRun;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getIdPago() {
        return idPago;
    }

    public void setIdPago(Long idPago) {
        this.idPago = idPago;
    }

    public List<DetalleOrdenItemDto> getItems() {
        return items;
    }

    public void setItems(List<DetalleOrdenItemDto> items) {
        this.items = items;
    }
}
