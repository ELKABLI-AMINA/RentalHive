package ma.youcode.RentalHive.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.RentalHive.domain.entity.Agency;
import ma.youcode.RentalHive.domain.entity.Client;
import ma.youcode.RentalHive.dto.AgencyDTO.AgencyCreateAndUpdateRequestDTO;
import ma.youcode.RentalHive.dto.AgencyDTO.AgencyResponseDTO;
import ma.youcode.RentalHive.dto.equipmentDTO.EquipmentResponseDTO;
import ma.youcode.RentalHive.exception.EquipmentNotFoundException;
import ma.youcode.RentalHive.repository.AgencyRepository;
import ma.youcode.RentalHive.repository.ClientRespository;
import ma.youcode.RentalHive.service.IAgencyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AgencyServiceImpl implements IAgencyService {
    public final AgencyRepository agencyRepository;
    public final ClientRespository clientRepository;

    @Override
    public List<AgencyResponseDTO> getAllAgency() {
        return agencyRepository.findAll().stream()
                .map(AgencyResponseDTO::fromAgency)
                .collect(Collectors.toList());
    }


    @Override
    public AgencyResponseDTO getAgencyById(Long id) {
        Objects.requireNonNull(id, "Equipment ID must not be null");
        return agencyRepository.findById(id)
                .map(AgencyResponseDTO::fromAgency)
                .orElseThrow(()->new IllegalArgumentException(String.format("Agency with id %d not found", id)));
    }

    @Override
    public AgencyResponseDTO createAgency(AgencyCreateAndUpdateRequestDTO requestDTO) {
        Agency saveAgency = null;
        if (requestDTO.client().getCin() == null){
            log.info("Sorry you must enter cin client");
        }
        Optional<Client> client = clientRepository.findByCin(requestDTO.client().getCin());
        if (client.isPresent()){
            Agency agency1 = Agency.builder()
                    .local(requestDTO.local())
                    .is_enabled(requestDTO.is_enabled())
                    .client(client.get()).build();
            saveAgency = agencyRepository.save(agency1);
        }
        log.info("Agency created successfully");
        return AgencyResponseDTO.fromAgency(saveAgency);
    }

    @Override
    public AgencyResponseDTO updateAgency(Long id, AgencyCreateAndUpdateRequestDTO agency) {
        Agency agency1 = agencyRepository.save(AgencyCreateAndUpdateRequestDTO.agencyFromAgencyUpdateDTO(id, agency));
        log.info("Agency updated successfully");
        return AgencyResponseDTO.fromAgency(agency1);
    }

    @Override
    public void deleteAgencyById(Long id) {
        Objects.requireNonNull(id, "Agency ID must not be null");
        if(agencyRepository.findById(id).isPresent()){
            agencyRepository.deleteById(id);
            log.info(String.format("Agency with id %d deleted successfully", id));
        }else{
            throw new EquipmentNotFoundException(String.format("Agency with id %d not found", id));
        }
    }
}
