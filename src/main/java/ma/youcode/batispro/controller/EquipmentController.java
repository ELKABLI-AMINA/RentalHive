package ma.youcode.batispro.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.batispro.domain.enums.Equipment.EquipmentStatus;
import ma.youcode.batispro.domain.enums.Equipment.EquipmentType;
import ma.youcode.batispro.dto.equipmentDTO.EquipmentCreationRequestDTO;
import ma.youcode.batispro.dto.equipmentDTO.EquipmentResponseDTO;
import ma.youcode.batispro.dto.equipmentDTO.EquipmentUpdateRequestDTO;
import ma.youcode.batispro.service.IEquipmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/v1/equipments")
@RequiredArgsConstructor
@Slf4j
@RestController
public class EquipmentController {

    private final IEquipmentService  equipmentService;


    @GetMapping({"/",""})
    public ResponseEntity<List<EquipmentResponseDTO>>
    getAllEquipment(){
        log.info("Request received to retrieve all equipments");
        return ResponseEntity.ok(equipmentService.getAllEquipments());
    }


    @PostMapping("/create")
    public ResponseEntity<EquipmentResponseDTO> createEquipment(@Valid @RequestBody EquipmentCreationRequestDTO requestDTO){
        log.info("Request received to create a new equipment.");
        return ResponseEntity.status(HttpStatus.CREATED).body(equipmentService.createEquipment(requestDTO));
    }

    @PutMapping("/{equipmentId}")
    public ResponseEntity<EquipmentResponseDTO> updateEquipment(@Valid @PathVariable("equipmentId") Long equipmentId,@Valid @RequestBody EquipmentUpdateRequestDTO equipmentUpdateRequestDTO){
        log.info("Request received to update a new equipment.");
        return ResponseEntity.status(HttpStatus.OK).body(equipmentService.updateEquipment(equipmentId, equipmentUpdateRequestDTO));
    }


    @GetMapping("/{equipmentId}")
    public ResponseEntity<EquipmentResponseDTO> getEquipmentById(@PathVariable Long equipmentId){
        log.info("Request received to get equipment by ID: {}", equipmentId);
        return ResponseEntity.ok(equipmentService.getEquipmentById(equipmentId));
    }
    @GetMapping("/search")
    public ResponseEntity<List<EquipmentResponseDTO>> searchEquipment(@RequestParam String name, @RequestParam EquipmentType equipmentType, @RequestParam EquipmentStatus equipmentStatus) {
        log.info("Request received to search equipment. Name: {}, Type: {}, Status: {}", name, equipmentType, equipmentStatus);
        List<EquipmentResponseDTO> result = equipmentService.searchEquipment(name, equipmentType, equipmentStatus);
        return ResponseEntity.ok(result);
    }
    
    @DeleteMapping("/{equipmentId}/delete")
    public ResponseEntity<Void> deleteEquipment(@PathVariable Long equipmentId){
        log.info("Request received to delete equipment with ID: {}.", equipmentId);
        equipmentService.deleteEquipmentById(equipmentId);
        return ResponseEntity.noContent().build();
    }

}
