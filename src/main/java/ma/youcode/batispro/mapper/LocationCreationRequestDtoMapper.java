package ma.youcode.batispro.mapper;

import ma.youcode.batispro.domain.entity.Location;
import ma.youcode.batispro.dto.locationDTO.LocationRequestDto;
import org.springframework.stereotype.Component;

@Component
public class LocationCreationRequestDtoMapper {


    public LocationRequestDto mapToDto(Location location){
        return LocationRequestDto.builder()
                .reference(location.getReference())
                .startDate(location.getStartDate())
                .endDate(location.getEndDate())
                .quantity(location.getQuantity())
                .model(location.getEquipment().getModel())
                .build();
    }
}
