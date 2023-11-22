package ma.youcode.batispro.dto.equipmentDTO;

import ma.youcode.batispro.domain.entity.Equipment;
import ma.youcode.batispro.domain.enums.Equipment.EquipmentStatus;
import ma.youcode.batispro.domain.enums.Equipment.EquipmentType;


public record EquipmentResponseDTO(
        Long id,
        String name,
        EquipmentType equipmentType,
        String model,
        String description,
        EquipmentStatus equipmentStatus
) {
    public static EquipmentResponseDTO fromEquipment (Equipment equipment){
        return new EquipmentResponseDTO(
                equipment.getId(),
                equipment.getName(),
                equipment.getEquipmentType(),
                equipment.getModel(),
                equipment.getDescription(),
                equipment.getEquipmentStatus()
        );
    }


}
