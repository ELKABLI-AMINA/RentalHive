package ma.youcode.RentalHive.dto.AgencyDTO;

import lombok.Builder;
import ma.youcode.RentalHive.domain.entity.Agency;
import ma.youcode.RentalHive.domain.entity.Client;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Builder
public record AgencyCreateAndUpdateRequestDTO(@NotNull String local, Boolean is_enabled, @NotNull Client client) {
    public static Agency agencyFromAgencyCreateDTO(AgencyCreateAndUpdateRequestDTO agency){
//        return Agency.builder()
//                        .local(agency.local)
//                .is_enabled(agency.is_enabled)
//                .client(agency.client)
//                .build();
        return null;
    }


    public static Agency agencyFromAgencyUpdateDTO(Long id, AgencyCreateAndUpdateRequestDTO agency){
        return new Agency(
                id,
                agency.local,
                agency.is_enabled,
                agency.client
        );
    }
}
