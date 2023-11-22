package ma.youcode.RentalHive.service;

import ma.youcode.RentalHive.dto.AgencyDTO.AgencyCreateAndUpdateRequestDTO;
import ma.youcode.RentalHive.dto.AgencyDTO.AgencyResponseDTO;
import ma.youcode.RentalHive.exception.AgencyNotFoundException;

import java.util.List;

public interface IAgencyService {

    List<AgencyResponseDTO> getAllAgency();
    AgencyResponseDTO createAgency(AgencyCreateAndUpdateRequestDTO agency) throws AgencyNotFoundException;
    AgencyResponseDTO updateAgency(Long id, AgencyCreateAndUpdateRequestDTO agency) throws AgencyNotFoundException;
    AgencyResponseDTO getAgencyById(Long id);
    void deleteAgencyById(Long id);
}
