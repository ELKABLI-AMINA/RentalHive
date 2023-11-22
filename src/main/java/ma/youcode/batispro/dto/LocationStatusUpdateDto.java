package ma.youcode.batispro.dto;

import lombok.Builder;
import ma.youcode.batispro.domain.enums.Location.LocationStatus;
import ma.youcode.batispro.domain.enums.PaymentStatus;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Builder
public record LocationStatusUpdateDto(
        @NotBlank UUID locationRequestReference,
        @NotBlank String equipmentUnitReference,
        @NotBlank LocationStatus status
) {
}
