package ma.youcode.batispro.service;

import ma.youcode.batispro.domain.enums.Equipment.EquipmentStatus;
import ma.youcode.batispro.domain.enums.Equipment.EquipmentType;
import ma.youcode.batispro.dto.equipmentDTO.EquipmentCreationRequestDTO;
import ma.youcode.batispro.dto.equipmentDTO.EquipmentResponseDTO;
import ma.youcode.batispro.dto.equipmentDTO.EquipmentUpdateRequestDTO;

import java.util.List;

public interface IEquipmentService {

    EquipmentResponseDTO createEquipment(EquipmentCreationRequestDTO equipment);
    EquipmentResponseDTO updateEquipment(Long id, EquipmentUpdateRequestDTO equipmentUpdateRequestDTO);
    EquipmentResponseDTO getEquipmentById(Long id);
    List<EquipmentResponseDTO> getAllEquipments();
    void deleteEquipmentById(Long id) ;
    List<EquipmentResponseDTO> searchEquipment(String name, EquipmentType equipmentType, EquipmentStatus equipmentStatus);
}
