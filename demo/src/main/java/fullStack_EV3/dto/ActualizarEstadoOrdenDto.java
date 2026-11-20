package fullStack_EV3.dto;

import jakarta.validation.constraints.NotBlank;

public class ActualizarEstadoOrdenDto {

    @NotBlank(message = "El nombre del estado es obligatorio")
    private String nuevoEstado; // Valores válidos: PENDIENTE, PAGADO, ENVIADO, ENTREGADO, CANCELADO

    public ActualizarEstadoOrdenDto() {}

    public ActualizarEstadoOrdenDto(String nuevoEstado) {
        this.nuevoEstado = nuevoEstado;
    }

    public String getNuevoEstado() {
        return nuevoEstado;
    }

    public void setNuevoEstado(String nuevoEstado) {
        this.nuevoEstado = nuevoEstado;
    }
}