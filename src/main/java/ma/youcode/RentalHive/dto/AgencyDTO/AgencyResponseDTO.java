package ma.youcode.RentalHive.dto.AgencyDTO;

import ma.youcode.RentalHive.domain.entity.Agency;
import ma.youcode.RentalHive.domain.entity.Users;

import java.util.List;

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
