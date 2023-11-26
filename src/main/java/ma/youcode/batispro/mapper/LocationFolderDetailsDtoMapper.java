package ma.youcode.batispro.mapper;

import lombok.RequiredArgsConstructor;
import ma.youcode.batispro.domain.entity.DossierLocation;
import ma.youcode.batispro.dto.locationDTO.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LocationFolderDetailsDtoMapper {

    private final LocationCreationRequestDtoMapper locationCreationRequestDtoMapper;



//    public LocationFolderDetailsDto mapToDto(DossierLocation dossierLocation){
//        List<LocationRequestDto> locationRequestDtos = dossierLocation.getLocation()
//                .stream()
//                .map(locationCreationRequestDtoMapper::mapToDto)
//                .toList();
//        return LocationFolderDetailsDto.builder()
//                .dateSubmission(dossierLocation.getDateCreation().toString())
//                .locationDetails(
//                        LocationDetailsDto.builder()
//                                .locationRequest(
//                                        LocationCreationRequestDto.builder()
//                                                .locationRequests(locationRequestDtos
//                                                ).build()
//                                ).status(dossierLocation.getStatus())
//                                .build()
//                )
//                .build();
//    }
}
