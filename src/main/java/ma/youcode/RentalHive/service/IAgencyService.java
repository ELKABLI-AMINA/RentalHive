package ma.youcode.RentalHive.service;

import ma.youcode.RentalHive.dto.AgencyDTO.AgencyCreateAndUpdateRequestDTO;
import ma.youcode.RentalHive.dto.AgencyDTO.AgencyResponseDTO;

import java.util.List;

public interface IAgencyService {

    List<AgencyResponseDTO> getAllAgency();
    AgencyResponseDTO createAgency(AgencyCreateAndUpdateRequestDTO agency);
    AgencyResponseDTO updateAgency(Long id, AgencyCreateAndUpdateRequestDTO agency);
    AgencyResponseDTO getAgencyById(Long id);
    void deleteAgencyById(Long id);
}
