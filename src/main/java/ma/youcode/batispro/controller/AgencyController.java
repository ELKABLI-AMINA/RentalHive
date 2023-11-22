package ma.youcode.batispro.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.batispro.dto.AgencyDTO.AgencyCreateAndUpdateRequestDTO;
import ma.youcode.batispro.dto.AgencyDTO.AgencyResponseDTO;
import ma.youcode.batispro.exception.AgencyNotFoundException;
import ma.youcode.batispro.service.IAgencyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/v1/agency")
@RequiredArgsConstructor
@RestController
@Slf4j
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

//    @GetMapping("/{cin}")
//    public ResponseEntity<AgencyResponseDTO> getAgencyByCIN(@Valid @PathVariable("cin") String cin){
//        return ResponseEntity.ok(AgencyService.getAgencyById(cin));
//    }
//
//    @GetMapping("/{local}")
//    public ResponseEntity<AgencyResponseDTO> getAgencyByLocal(@Valid @PathVariable("local") String local){
//        return ResponseEntity.ok(AgencyService.getAgencyById(local));
//    }

    @PostMapping
    public ResponseEntity<AgencyResponseDTO> createAgency(@Valid @RequestBody AgencyCreateAndUpdateRequestDTO requestDTO) throws AgencyNotFoundException, AgencyNotFoundException {
        AgencyResponseDTO createAgency = AgencyService.createAgency(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createAgency);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgencyResponseDTO> updateAgency(@Valid @PathVariable Long id, @Valid @RequestBody AgencyCreateAndUpdateRequestDTO requestDTO) throws AgencyNotFoundException {
        AgencyResponseDTO createAgency = AgencyService.updateAgency(id, requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(createAgency);
    }

    @DeleteMapping
    public ResponseEntity<AgencyResponseDTO> deleteAgency(@Valid @PathVariable Long id){
        AgencyService.deleteAgencyById(id);
        return ResponseEntity.noContent().build();
    }
}
