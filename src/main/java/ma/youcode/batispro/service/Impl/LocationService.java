package ma.youcode.batispro.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.batispro.domain.entity.*;
import ma.youcode.batispro.domain.enums.Equipment.EquipmentStatus;
import ma.youcode.batispro.domain.enums.Location.LocationFolderStatus;
import ma.youcode.batispro.domain.enums.Location.LocationStatus;
import ma.youcode.batispro.domain.enums.UserRole;
import ma.youcode.batispro.dto.LocationStatusUpdateDto;
import ma.youcode.batispro.dto.RLocationDTO.LocationCreateRequestDTO;
import ma.youcode.batispro.dto.RLocationDTO.LocationResponseDTO;
import ma.youcode.batispro.dto.clientDTO.ClientDossierRequestDto;
import ma.youcode.batispro.dto.locationDTO.LocationCreationRequestDto;
import ma.youcode.batispro.dto.locationDTO.LocationDetailsDto;
import ma.youcode.batispro.dto.locationDTO.LocationFolderDetailsDto;
import ma.youcode.batispro.dto.locationDTO.LocationRequestDto;
import ma.youcode.batispro.exception.DossierNotFoundException;
import ma.youcode.batispro.exception.EquipmentNotFoundException;
import ma.youcode.batispro.exception.EquipmentOutOfStockException;
import ma.youcode.batispro.mapper.LocationCreationRequestDtoMapper;
import ma.youcode.batispro.mapper.LocationFolderDetailsDtoMapper;
import ma.youcode.batispro.repository.*;
import ma.youcode.batispro.service.ILocationService;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationService implements ILocationService {

    private final LocationRepository locationRepository;
    private final LocationFolderRepository locationFolderRepository;
    private final EquipmentRepository equipmentRepository;
    private final EquipmentUnitRepository equipmentUnitRepository;
    private final LocationCreationRequestDtoMapper locationCreationRequestDtoMapper;
    private final LocationFolderDetailsDtoMapper locationFolderDetailsDtoMapper;
    private final ClientRespository clientRespository;

    @Override
    public LocationResponseDTO createLocation(LocationCreationRequestDto locationRequest) {
        List<LocationRequestDto> locationRequests = locationRequest.locationRequests();

        //Get User Manual
        Client client = clientRespository.findByCin(locationRequest.cinUser())
                .orElseThrow(() -> new IllegalArgumentException("Client Not Found"));

        //Check date location
        boolean anyInvalidDateForLocation = locationRequests.stream()
                .anyMatch(l -> l.startDate().isBefore(LocalDate.now()) ||
                        l.endDate().isBefore(l.startDate()));
        if(anyInvalidDateForLocation){
            throw new IllegalArgumentException("Invalid date for location");
        }


        List<Location> locationList = new ArrayList<>();
        locationRequests.stream().forEach(e -> {
            //Check Equipment If Exist
            Equipment equipment = equipmentRepository.findByModel(e.model())
                    .orElseThrow(() -> new EquipmentNotFoundException("This is Equipment not found"));
            //Check status equipment
            if(!equipment.getEquipmentStatus().equals(EquipmentStatus.valueOf(e.status()))){
                throw new IllegalArgumentException("this is status not valid");
            }
            //Get EquipmentUnit
            List<EquipmentUnit> equipmentUnit =  equipmentUnitRepository.findByEquipmentUnitWithEquipment(equipment.getId());
            //Check Available Quantity Equipment
            Integer availableQuantityEquipment = equipmentUnit.stream()
                    .map(EquipmentUnit::getQuantity)
                    .reduce(0, Integer::sum);

            if(availableQuantityEquipment < e.quantity()){
                throw new IllegalArgumentException("Equipment out of stock");
            }

            //Save locations in array
            Location location = Location.builder()
                    .reference(UUID.randomUUID())
                    .quantity(e.quantity())
                    .startDate(e.startDate())
                    .endDate(e.endDate())
                    .equipment(equipment)
                    .build();
            locationList.add(location);
        });

        //Generate UniqueDossier
        String uniqueDossierNumber = "D" + LocalDateTime.now().getNano();

        //Save All dossierLocations
        locationList.stream().forEach(location -> {
            DossierLocation dossierLocation = DossierLocation.builder()
                    .dossierNumber(uniqueDossierNumber)
                    .dateCreation(LocalDateTime.now())
                    .client(client)
                    .Location(location)
                    .status(LocationStatus.PENDING)
                    .build();
            locationFolderRepository.save(dossierLocation);
        });

        //Save All location
        locationRepository.saveAll(locationList);

        return null;

    }


//    @Override
//    public LocationResponseDTO createLocation(LocationCreationRequestDto locationRequest) {
////        //Get equipment is found
////        Optional<Equipment> equipment = Optional.ofNullable(equipmentRepository.findByModel(model_equipment)
////                .orElseThrow(() -> new IllegalArgumentException("Equipment Not Found")));
////
////        //Get User is found
////        Optional<Client> client = Optional.ofNullable(clientRespository.findByCin(cin_client)
////                .orElseThrow(() -> new IllegalArgumentException("Client Not Found")));
//
////        //Get quantity equipment
////        EquipmentUnit equipmentUnit =  equipmentUnitRepository.findByEquipmentUnitWithEquipment(equipment.get().getId());
////        Integer equipmentQuantity = equipmentUnit.getQuantity();
//
//        //Check Date
//        LocalDate startDate = locationRequest.startDate();
//        LocalDate endDate = locationRequest.endDate();
//        if(startDate.isBefore(LocalDate.now()) || endDate.isBefore(startDate)){
//            throw new IllegalArgumentException("Invalid date for location");
//        }
//
////        //Check Quantity
////        Boolean isQuantitySufficient  = equipmentQuantity < locationRequest.quantity();
////        if (isQuantitySufficient){
////            throw new IllegalArgumentException("This equipment cannot be reserved due to quantity");
////        }
//
//        // Stock location
//        UUID randomRef = UUID.randomUUID();
//        Location location = Location.builder()
//                .reference(randomRef)
//                .quantity(locationRequest.quantity())
//                .startDate(locationRequest.startDate())
//                .endDate(locationRequest.endDate())
//                .equipment(equipment.get())
//                .build();
//        locationRepository.save(location);
//
//        //Stock dossierLocation
//        LocalDateTime dateNow = LocalDateTime.now();
//        String uniqueDossierNumber = "D" + LocalDateTime.now().getNano();
//        DossierLocation dossierLocation = DossierLocation.builder()
//                .dossierNumber(uniqueDossierNumber)
//                .dateCreation(dateNow)
//                .Location(location)
//                .client(client.get())
//                .status(LocationStatus.PENDING)
//                .build();
//        locationFolderRepository.save(dossierLocation);
//
//
////        return LocationCreationRequestDtoMapper::mapToDto(location);
//        return null;
//    }
}
