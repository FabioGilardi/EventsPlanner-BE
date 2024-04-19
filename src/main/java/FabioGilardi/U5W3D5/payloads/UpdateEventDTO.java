package FabioGilardi.U5W3D5.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateEventDTO(
        @NotEmpty(message = "place is mandatory")
        @Size(min = 3, message = "place must be longer than 3 chars")
        String place,

        @NotNull(message = "date is mandatory")
        LocalDate date
) {
}
