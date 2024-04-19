package FabioGilardi.U5W3D5.payloads;

import java.time.LocalDateTime;

public record ErrorDTO(
        String message,
        LocalDateTime timestamp
) {
}
