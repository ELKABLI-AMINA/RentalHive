package ma.youcode.RentalHive.service.Impl;

import liquibase.precondition.Precondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.RentalHive.domain.entity.Agency;
import ma.youcode.RentalHive.domain.entity.Client;
import ma.youcode.RentalHive.dto.AgencyDTO.AgencyCreateAndUpdateRequestDTO;
import ma.youcode.RentalHive.dto.AgencyDTO.AgencyResponseDTO;
import ma.youcode.RentalHive.dto.equipmentDTO.EquipmentResponseDTO;
import ma.youcode.RentalHive.exception.AgencyNotFoundException;
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
    public AgencyResponseDTO createAgency(AgencyCreateAndUpdateRequestDTO requestDTO) throws AgencyNotFoundException {
        Agency saveAgency = null;
        String cin = requestDTO.client().getCin();

        //check client by cin if exist
        Objects.requireNonNull(cin, "CIN cannot be null");

        //get client by cin
        Optional<Client> optionalClient = clientRepository.findByCin(cin);

        //check user if exist in agency
        Optional<Agency> agencyOptional = agencyRepository.findByClientCin(cin);
        if (agencyOptional.isPresent()){
            log.error("Sorry, but this client already exists in another agency!");
        }else {
            //if client exist save agency
            if (optionalClient != null && optionalClient.isPresent()) {
                Agency agency1 = Agency.builder()
                        .local(requestDTO.local())
                        .is_enabled(true)
                        .client(optionalClient.get()).build();
                saveAgency = agencyRepository.save(agency1);
            }
        }

        //check agency
        if (saveAgency != null) {
            log.info("Agency created successfully");
            return AgencyResponseDTO.fromAgency(saveAgency);
        } else {
            log.error("Failed to create agency");
            throw new AgencyNotFoundException("Failed to create agency");
        }
    }

    @Override
    public AgencyResponseDTO updateAgency(Long id, AgencyCreateAndUpdateRequestDTO requestDTO) throws AgencyNotFoundException {
        Agency saveAgency = null;
        String cin = requestDTO.client().getCin();

        //check client by cin if exist
        Objects.requireNonNull(cin, "CIN cannot be null");

        //get client by cin
        Optional<Client> optionalClient = clientRepository.findByCin(cin);

        //if client exist save agency
        if (optionalClient != null && optionalClient.isPresent()) {
            Agency agency1 = Agency.builder()
                    .id(id)
                    .local(requestDTO.local())
                    .is_enabled(requestDTO.is_enabled())
                    .client(optionalClient.get()).build();
            saveAgency = agencyRepository.save(agency1);
        }

        //check agency
        if (saveAgency != null) {
            log.info("Agency updated successfully");
            return AgencyResponseDTO.fromAgency(saveAgency);
        } else {
            log.error("Failed to updated agency");
            throw new AgencyNotFoundException("Failed to updated agency");
        }
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
