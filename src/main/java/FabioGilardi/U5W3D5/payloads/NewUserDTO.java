package FabioGilardi.U5W3D5.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewUserDTO(
        @NotEmpty(message = "name is mandatory")
        @Size(min = 3, message = "name must be longer than 3 chars")
        String name,

        @NotEmpty(message = "surname is mandatory")
        @Size(min = 3, message = "surname must be longer than 3 chars")
        String surname,

        @NotEmpty(message = "email is mandatory")
        @Email(message = "you must insert a valid email")
        String email,

        @NotEmpty(message = "username is mandatory")
        @Size(min = 3, message = "username must be longer than 3 chars")
        String username,

        @NotEmpty(message = "password is mandatory")
        @Size(min = 4, message = "password must be longer than 4 chars")
        String password
) {
}
