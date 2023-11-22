package ma.youcode.batispro.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.batispro.domain.entity.Equipment;
import ma.youcode.batispro.domain.enums.Equipment.EquipmentStatus;
import ma.youcode.batispro.domain.enums.Equipment.EquipmentType;
import ma.youcode.batispro.dto.equipmentDTO.EquipmentCreationRequestDTO;
import ma.youcode.batispro.dto.equipmentDTO.EquipmentResponseDTO;
import ma.youcode.batispro.dto.equipmentDTO.EquipmentUpdateRequestDTO;
import ma.youcode.batispro.repository.EquipmentRepository;
import ma.youcode.batispro.service.IEquipmentService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import ma.youcode.batispro.exception.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class EquipmentServiceImpl implements IEquipmentService {

    private final EquipmentRepository equipmentRepository;


    @Override
    public EquipmentResponseDTO createEquipment(EquipmentCreationRequestDTO equipment) {
        try {
          Equipment equipment1 =  equipmentRepository.save(EquipmentCreationRequestDTO.equipmentFromEquipmentCreationRequestDTO(equipment));
          log.info("Equipment created succefully");
          return EquipmentResponseDTO.fromEquipment(equipment1);
        }catch (DataAccessException e){
            log.error("Error occured during saving equipment", e);
            throw new RuntimeException("Failed to save equipment" , e);
        }

    }

    @Override
    public EquipmentResponseDTO updateEquipment(Long id, EquipmentUpdateRequestDTO updateRequestDTO){
        try {
            Objects.requireNonNull(id, "Equipment ID must not be null");
            Equipment equipment = equipmentRepository.save(EquipmentUpdateRequestDTO.equipmentFromEquipmentUpdateRequestDTO(id, updateRequestDTO));
            log.info("Equipment Updated Successfully");
            return EquipmentResponseDTO.fromEquipment(equipment);
        }catch (DataAccessException e){
            log.error("Error occurred during updating equipment", e);
            throw new RuntimeException("Failed to update equipment");
        }
    }

    @Override
    public EquipmentResponseDTO getEquipmentById(Long id) {
       try {
           Objects.requireNonNull(id, "Equipment ID must not be null");
           return equipmentRepository.findById(id)
                   .map(EquipmentResponseDTO::fromEquipment)
                   .orElseThrow(()->new EquipmentNotFoundException(String.format("Equipment with id %d not found", id)));
       } catch(DataAccessException e){
           log.error(String.format("Error occurred during fetching equipment with id %d", id), e);
           throw new RuntimeException("Failed to fetch product", e);
        }
    }

    @Override
    public List<EquipmentResponseDTO> getAllEquipments(){
        try {
            return equipmentRepository.findAll().stream()
                    .map(EquipmentResponseDTO::fromEquipment)
                    .toList();
        }catch (DataAccessException e){
            log.error("Error occurred during fetching all equipments", e);
            throw new RuntimeException("Failed to fetch equipments", e);
        }
    }


    @Override
    public void deleteEquipmentById(Long id) {
        try{
            Objects.requireNonNull(id, "Equipment ID must not be null");
            if(equipmentRepository.findById(id).isPresent()){
                equipmentRepository.deleteById(id);
                log.info(String.format("Equipment with id %d deleted successfully", id));
            }else{
                throw new EquipmentNotFoundException(String.format("Product with id %d not found", id));
            }
        }catch(DataAccessException e){
            log.error(String.format("Error occurred during equipment deleting for id %d", id), e);
            throw new RuntimeException("Failed to delete equipment", e);
        }
    }

    @Override
    public List<EquipmentResponseDTO> searchEquipment(String name, EquipmentType equipmentType, EquipmentStatus equipmentStatus) {
        try {
            List<Equipment> equipmentList = equipmentRepository.findByNameAndEquipmentTypeAndEquipmentStatus(name, equipmentType, equipmentStatus);
            if (equipmentList.isEmpty()) {
                throw new EquipmentNotFoundException("No equipment found with the specified criteria");
            }
            return equipmentList.stream()
                    .map(EquipmentResponseDTO::fromEquipment)
                    .collect(Collectors.toList());
        }catch (DataAccessException e){
            log.error("Error occurred during searching an equipment", e);
            throw new RuntimeException("Failed to search equipment", e);

        }
    }



}
