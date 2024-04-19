package FabioGilardi.U5W3D5.payloads;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record NewBookingDTO(

        @NotNull(message = "event id is mandatory")
        @Min(value = 0, message = "event id must be greater than 0")
        long eventId
) {
}
