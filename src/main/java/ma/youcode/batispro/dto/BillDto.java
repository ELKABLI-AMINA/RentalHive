package ma.youcode.batispro.dto;

import lombok.Builder;

import java.util.List;
@Builder
public record BillDto(
        String billNumber,
        String creationDate,
        String billStatus,
        String billPaymentStatus,
        String dossierNumber,
        String clientName,
        String clientEmail,
        String billTotal,
        BillDetailsDto billDetails
) {
}
