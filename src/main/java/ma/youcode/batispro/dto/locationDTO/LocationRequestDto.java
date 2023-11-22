package ma.youcode.batispro.dto.locationDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record LocationRequestDto (
        UUID reference,
        @NotEmpty String equipmentReference,
        @NotEmpty Integer quantity,
        @NotEmpty LocalDate startDate,
        @NotEmpty LocalDate endDate,
        @NotEmpty String status,
        @NotEmpty String paymentStatus
){
}
