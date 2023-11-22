package ma.youcode.batispro.service;


import ma.youcode.batispro.dto.AgencyDTO.AgencyCreateAndUpdateRequestDTO;
import ma.youcode.batispro.dto.AgencyDTO.AgencyResponseDTO;
import ma.youcode.batispro.exception.AgencyNotFoundException;

import java.util.List;

public interface IAgencyService {

    List<AgencyResponseDTO> getAllAgency();
    AgencyResponseDTO createAgency(AgencyCreateAndUpdateRequestDTO agency) throws AgencyNotFoundException;
    AgencyResponseDTO updateAgency(Long id, AgencyCreateAndUpdateRequestDTO agency) throws AgencyNotFoundException;
    AgencyResponseDTO getAgencyById(Long id);
    void deleteAgencyById(Long id);
}
