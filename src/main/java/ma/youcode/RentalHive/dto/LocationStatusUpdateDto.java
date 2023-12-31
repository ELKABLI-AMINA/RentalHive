package ma.youcode.RentalHive.dto;

import lombok.Builder;
import ma.youcode.RentalHive.domain.enums.Location.LocationStatus;
import ma.youcode.RentalHive.domain.enums.PaymentStatus;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Builder
public record LocationStatusUpdateDto(
        @NotBlank UUID locationRequestReference,
        @NotBlank String equipmentUnitReference,
        @NotBlank LocationStatus status,
        @NotBlank PaymentStatus paymentStatus
) {
}
