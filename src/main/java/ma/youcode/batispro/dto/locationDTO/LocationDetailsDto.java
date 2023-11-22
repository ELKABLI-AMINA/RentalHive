package ma.youcode.batispro.dto.locationDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import ma.youcode.batispro.domain.entity.Location;
import ma.youcode.batispro.domain.enums.Location.LocationFolderStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record LocationDetailsDto(
        LocationFolderStatus status,
        LocationCreationRequestDto locationRequest
) {
    public static LocationDetailsDto locationDetailsDto(Location location){
        return null;
    }
}
