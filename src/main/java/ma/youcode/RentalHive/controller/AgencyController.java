package ma.youcode.RentalHive.controller;

import lombok.RequiredArgsConstructor;
import ma.youcode.RentalHive.dto.AgencyDTO.AgencyCreateAndUpdateRequestDTO;
import ma.youcode.RentalHive.dto.AgencyDTO.AgencyResponseDTO;
import ma.youcode.RentalHive.dto.equipmentDTO.EquipmentCreationRequestDTO;
import ma.youcode.RentalHive.dto.equipmentDTO.EquipmentResponseDTO;
import ma.youcode.RentalHive.service.IAgencyService;
import ma.youcode.RentalHive.service.Impl.AgencyServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/v1/agency")
@RequiredArgsConstructor
@RestController
public class AgencyController {
    private final IAgencyService AgencyService;

    @GetMapping("")
    public ResponseEntity<List<AgencyResponseDTO>> getAllAgency(){
        List<AgencyResponseDTO> agencyResponseDTOS = AgencyService.getAllAgency();
        return ResponseEntity.ok(agencyResponseDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgencyResponseDTO> getAgencyByID(@Valid @PathVariable("id") Long id){
        return ResponseEntity.ok(AgencyService.getAgencyById(id));
    }

    @PostMapping
    public ResponseEntity<AgencyResponseDTO> createAgency(@Valid @RequestBody AgencyCreateAndUpdateRequestDTO requestDTO){
        AgencyResponseDTO createAgency = AgencyService.createAgency(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createAgency);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgencyResponseDTO> updateAgency(@Valid @PathVariable Long id, @Valid @RequestBody AgencyCreateAndUpdateRequestDTO requestDTO){
        AgencyResponseDTO createAgency = AgencyService.updateAgency(id, requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(createAgency);
    }

    @DeleteMapping
    public ResponseEntity<AgencyResponseDTO> deleteAgency(@Valid @PathVariable Long id){
        AgencyService.deleteAgencyById(id);
        return ResponseEntity.noContent().build();
    }
}
