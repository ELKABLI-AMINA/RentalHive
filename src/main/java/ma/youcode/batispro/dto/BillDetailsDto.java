package ma.youcode.batispro.dto;

import lombok.Builder;

@Builder
public record BillDetailsDto(
        String dossierNumber,
        String equipmentPrice,
        String equipmentQuantity,
        String totalPrice
) {
}
