package fullStack_EV3.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequestDto {

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El formato de correo no es valido")
    private String correo;

    @NotBlank(message = "La contrasena es obligatoria")
    private String password;

    public LoginRequestDto() {
    }

    public LoginRequestDto(String correo, String password) {
        this.correo = correo;
        this.password = password;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
