package FabioGilardi.U5W3D5.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserLoginDTO(
        @NotEmpty(message = "email is mandatory")
        @Email
        String email,

        @NotEmpty(message = "password is mandatory")
        @Size(min = 4, message = "password must be longer than 4")
        String password
) {
}
