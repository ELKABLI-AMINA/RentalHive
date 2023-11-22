package ma.youcode.batispro.dto.equipmentDTO;

import ma.youcode.batispro.domain.entity.Equipment;
import ma.youcode.batispro.domain.enums.Equipment.EquipmentStatus;
import ma.youcode.batispro.domain.enums.Equipment.EquipmentType;

import javax.validation.constraints.NotNull;

public record EquipmentCreationRequestDTO(
        @NotNull
        String name,
        @NotNull
        String model,
        @NotNull
        EquipmentType equipmentType,
        @NotNull
        String description,
        @NotNull
        EquipmentStatus equipmentStatus

) {
    public static Equipment equipmentFromEquipmentCreationRequestDTO(EquipmentCreationRequestDTO equipment) {
        return new Equipment(
                null,
                equipment.name,
                equipment.equipmentType,
                equipment.model,
                equipment.description,
                equipment.equipmentStatus

        );
    }
}
