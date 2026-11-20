package fullStack_EV3.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class CrearOrdenRequestDto {

    private String userRun;
    private String metodoPago;

    @NotNull(message = "El monto total es obligatorio")
    private Integer total;

    @NotEmpty(message = "La orden debe contener al menos un producto")
    private List<DetalleOrdenItemDto> items = new ArrayList<>();

    public CrearOrdenRequestDto() {
    }

    public CrearOrdenRequestDto(String userRun, String metodoPago, Integer total, List<DetalleOrdenItemDto> items) {
        this.userRun = userRun;
        this.metodoPago = metodoPago;
        this.total = total;
        this.items = items;
    }

    public String getUserRun() {
        return userRun;
    }

    public void setUserRun(String userRun) {
        this.userRun = userRun;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<DetalleOrdenItemDto> getItems() {
        return items;
    }

    public void setItems(List<DetalleOrdenItemDto> items) {
        this.items = items;
    }
}
