package ma.youcode.batispro.dto.AgencyDTO;

import ma.youcode.batispro.domain.entity.Agency;
import ma.youcode.batispro.domain.entity.Users;

public record AgencyResponseDTO(Long id, String local, Boolean is_enabled, Users usersList) {
    public static AgencyResponseDTO fromAgency(Agency agency){
        return new AgencyResponseDTO(
                agency.getId(),
                agency.getLocal(),
                agency.getIs_enabled(),
                agency.getClient()
        );
    }
}
