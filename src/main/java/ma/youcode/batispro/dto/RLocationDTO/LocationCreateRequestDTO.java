package ma.youcode.batispro.dto.RLocationDTO;

import lombok.Data;
import ma.youcode.batispro.domain.entity.Equipment;
import ma.youcode.batispro.domain.entity.Location;
import ma.youcode.batispro.domain.enums.Location.LocationStatus;
import ma.youcode.batispro.dto.equipmentDTO.EquipmentCreationRequestDTO;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Stream;

public record LocationCreateRequestDTO(@NotNull Integer quantity, @NotNull LocalDate startDate, @NotNull LocalDate endDate) {
    public static Location equipmentFromEquipmentCreationRequestDTO(LocationCreateRequestDTO locationCreateRequestDTO) {
        return Location.builder()
                .quantity(locationCreateRequestDTO.quantity)
                .startDate(locationCreateRequestDTO.startDate)
                .endDate(locationCreateRequestDTO.endDate)
                .build();
    }

}
