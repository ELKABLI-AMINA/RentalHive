package ma.youcode.batispro.dto.equipmentDTO;

import ma.youcode.batispro.domain.entity.Equipment;
import ma.youcode.batispro.domain.enums.Equipment.EquipmentStatus;
import ma.youcode.batispro.domain.enums.Equipment.EquipmentType;

import javax.validation.constraints.NotNull;

public record EquipmentUpdateRequestDTO(
                                        @NotNull String name,
                                        @NotNull String model,
                                        @NotNull EquipmentType equipmentType,
                                        @NotNull String description,
                                        @NotNull EquipmentStatus equipmentStatus) {
    public static Equipment equipmentFromEquipmentUpdateRequestDTO(long id, EquipmentUpdateRequestDTO equipmentUpdateRequestDTO){
        return new Equipment(id,
                equipmentUpdateRequestDTO.name,
                equipmentUpdateRequestDTO.equipmentType,
                equipmentUpdateRequestDTO.model,
                equipmentUpdateRequestDTO.description,
                equipmentUpdateRequestDTO.equipmentStatus);
    }
}
