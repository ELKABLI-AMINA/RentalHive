package ma.youcode.batispro.dto.RLocationDTO;

import lombok.Builder;
import ma.youcode.batispro.domain.entity.Equipment;
import ma.youcode.batispro.domain.entity.Location;
import ma.youcode.batispro.domain.enums.Location.LocationStatus;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record LocationResponseDTO(Long id, Integer quantity, UUID reference, LocalDate startDate, LocalDate endDate, Equipment equipment) {
    public static LocationResponseDTO mapToDTO(Location location){
//        return LocationResponseDTO.builder()
//                .id(location.getId())
//                .reference(location.getReference())
//                .quantity(location.getQuantity())
//                .startDate(location.getStartDate())
//                .endDate(location.getEndDate())
//                .equipment(location.getEquipment())
//                .build();

        return new LocationResponseDTO(
                location.getId(),
                location.getQuantity(),
                location.getReference(),
                location.getStartDate(),
                location.getEndDate(),
                location.getEquipment()
        );

    }
}
