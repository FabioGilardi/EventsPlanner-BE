package FabioGilardi.U5W3D5.payloads;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record NewEventDTO(
        @NotEmpty(message = "title is mandatory")
        @Size(min = 3, message = "title must be longer than 3 chars")
        String title,

        @NotEmpty(message = "description is mandatory")
        @Size(min = 3, message = "description must be longer than 3 chars")
        String description,

        @NotEmpty(message = "place is mandatory")
        @Size(min = 3, message = "place must be longer than 3 chars")
        String place,

        @NotNull(message = "date is mandatory")
        LocalDate date,

        @Min(value = 1, message = "max partecipants is mandatory")
        int maxPartecipants
) {
}
