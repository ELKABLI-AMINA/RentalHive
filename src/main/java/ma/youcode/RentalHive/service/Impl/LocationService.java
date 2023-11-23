package ma.youcode.RentalHive.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.RentalHive.domain.entity.*;
import ma.youcode.RentalHive.domain.enums.Equipment.EquipmentStatus;
import ma.youcode.RentalHive.domain.enums.Location.LocationFolderStatus;
import ma.youcode.RentalHive.domain.enums.Location.LocationStatus;
import ma.youcode.RentalHive.domain.enums.PaymentStatus;
import ma.youcode.RentalHive.domain.enums.UserRole;
import ma.youcode.RentalHive.dto.LocationStatusUpdateDto;
import ma.youcode.RentalHive.dto.clientDTO.ClientDossierRequestDto;
import ma.youcode.RentalHive.dto.locationDTO.*;
import ma.youcode.RentalHive.exception.DossierNotFoundException;
import ma.youcode.RentalHive.exception.EquipmentNotFoundException;
import ma.youcode.RentalHive.mapper.LocationCreationRequestDtoMapper;
import ma.youcode.RentalHive.mapper.LocationFolderDetailsDtoMapper;
import ma.youcode.RentalHive.repository.EquipmentRepository;
import ma.youcode.RentalHive.repository.EquipmentUnitRepository;
import ma.youcode.RentalHive.repository.LocationFolderRepository;
import ma.youcode.RentalHive.repository.LocationRepository;
import ma.youcode.RentalHive.service.ILocationService;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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

    @Override
    public LocationFolderDetailsDto createLocationFolder(@Valid LocationCreationRequestDto locationRequest) {
        List<LocationRequestDto> locationRequestDtos = locationRequest.locationRequests();
        boolean anyInvalidDateForLocation = locationRequestDtos.stream()
                .anyMatch(lr -> lr.startDate().isBefore(LocalDate.now()) ||
                        lr.endDate().isBefore(lr.startDate()));
        if (anyInvalidDateForLocation) {
            throw new IllegalArgumentException("Invalid date for location");
        }
        List<Location> locationsRequest = new ArrayList<>();
        locationRequestDtos
                .stream()
                .forEach(lr -> {
                    Equipment equipment = equipmentRepository.findByModel(lr.equipmentReference())
                            .orElseThrow(() -> new EquipmentNotFoundException("Equipment not found"));
                    List<EquipmentUnit> equipmentUnits = equipment.getEquipmentUnits();
                    long availableEquipmentUnits = equipmentUnits.stream()
                            .filter(eu -> eu.getEquipmentStatus().equals(EquipmentStatus.valueOf(lr.status())))
                            .map(EquipmentUnit::getQuantity)
                            .reduce(0, Integer::sum);
                    if (availableEquipmentUnits < lr.quantity()) {
                        throw new EquipmentNotFoundException("Equipment out of stock");
                    }
                    // lastly
                    Location location = Location.builder()
                            .reference(UUID.randomUUID())
                            .quantity(lr.quantity())
                            .startDate(lr.startDate())
                            .endDate(lr.endDate())
                            .equipment(equipment)
                            .build();
                    locationsRequest.add(location);
                });

        String uniqueDossierNumber = "D" + LocalDateTime.now().getNano();
        ClientDossierRequestDto clientDossierRequestDto = locationRequest.clientDossierRequestDto();
         Client client = new Client(
                String.join(" ", clientDossierRequestDto.firstName(), clientDossierRequestDto.lastName()),
                clientDossierRequestDto.email(),
                    UserRole.ROLE_CLIENT,
                true,
                clientDossierRequestDto.address(),
                clientDossierRequestDto.phone(),
                clientDossierRequestDto.cin()
        );
        DossierLocation dossierLocation = DossierLocation.builder()
                .dossierNumber(uniqueDossierNumber)
                .dateCreation(LocalDateTime.now())
                .location(locationsRequest)
                .client(client)
                .status(LocationFolderStatus.PENDING)
                .build();
        DossierLocation savedDossier = locationFolderRepository.save(dossierLocation);
        locationsRequest
                .forEach(l -> {
                    l.setStatus(LocationStatus.PENDING);
                    l.setPaymentStatus(PaymentStatus.PENDING);
                    l.setDossierLocation(savedDossier);
                    locationRepository.save(l);
                });
        return LocationFolderDetailsDto.builder()
                .folderNumber(uniqueDossierNumber)
                .locationDetails(
                        LocationDetailsDto.builder()
                                .locationRequest(locationRequest)
                                .status(LocationFolderStatus.PENDING)
                                .build()
                ).build();
    }

    @Override
    public LocationFolderDetailsDto consultLocationFolder(String dossierNumber) throws DossierNotFoundException {
        DossierLocation dossierLocation = getDossierLocation(dossierNumber);
        List<Location> locationList = dossierLocation.getLocation();
        List<LocationRequestDto> locationRequestDtos = locationList
                .stream()
                .map(locationCreationRequestDtoMapper::mapToDto)
                .toList();
        LocationDetailsDto locationDetailsDto = LocationDetailsDto.builder()
                .locationRequest(
                        LocationCreationRequestDto.builder()
                                .locationRequests(
                                        locationRequestDtos
                                ).build()
                )
                .status(dossierLocation.getStatus())
                .build();
        return LocationFolderDetailsDto.builder()
                .locationDetails(
                       locationDetailsDto
                ).dateSubmission(dossierLocation.getDateCreation().toString())
                .build();
    }

    private DossierLocation getDossierLocation(String dossierNumber) throws DossierNotFoundException {
        Optional<DossierLocation> byDossierNumber = locationFolderRepository.findByDossierNumber(dossierNumber);
        return  byDossierNumber.orElseThrow(() -> new DossierNotFoundException("Dossier not found"));
    }

    @Override
    public LocationFolderDetailsDto acceptLocationFolder(String dossierNumber) throws DossierNotFoundException {
        DossierLocation dossierLocation = getDossierLocation(dossierNumber);
        dossierLocation.setStatus(LocationFolderStatus.ACCEPTED);
        dossierLocation.getLocation()
                .stream()
                .forEach(l->l.setStatus(LocationStatus.ACCEPTED));
        DossierLocation updated = locationFolderRepository.save(dossierLocation);
        return   locationFolderDetailsDtoMapper.mapToDto(updated);
    }

    @Override
    public LocationFolderDetailsDto resolveLocationFolder(String locationFolderNumber, List<LocationStatusUpdateDto> statusUpdates) throws DossierNotFoundException {
        DossierLocation dossierLocation = getDossierLocation(locationFolderNumber);
        List<Location> locationList = dossierLocation.getLocation();
        if(locationList.size() < statusUpdates.size())
            throw new IllegalArgumentException("Invalid number of status updates");
        for (var locationStatusUpdate : statusUpdates) {
            Optional<Location> location = locationList.stream()
                    .filter(l -> l.getReference().equals(locationStatusUpdate.locationRequestReference()))
                    .findFirst();
            if(location.isEmpty())
                throw new IllegalArgumentException("Invalid equipment reference");

            Location locationToUpdate = location.get();
            locationToUpdate.setStatus(locationStatusUpdate.status());
            locationToUpdate.setPaymentStatus(locationStatusUpdate.paymentStatus());
            EquipmentUnit equipmentUnitWanted = equipmentUnitRepository.findByRef(locationStatusUpdate.equipmentUnitReference())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid equipment unit reference"));
            // check if the equipment unit is available
            boolean couldBePlaced = checkIfLocationCouldBePlaced(locationCreationRequestDtoMapper.mapToDto(locationToUpdate));
            if(!couldBePlaced)
                throw new IllegalArgumentException(String.format("Could not resolve location with reference %s with the given equipment unit %s", locationToUpdate.getReference(), equipmentUnitWanted.getRef()));
            locationToUpdate.setEquipmentUnit(equipmentUnitWanted);
            locationRepository.save(locationToUpdate);
        }
        return LocationFolderDetailsDto.builder()
                .folderNumber(locationFolderNumber)
                .validatedBy("Admin")
                .locationDetails(
                        LocationDetailsDto.builder()
                                .locationRequest(
                                        LocationCreationRequestDto.builder()
                                                .locationRequests(
                                                        locationList.stream()
                                                                .map(locationCreationRequestDtoMapper::mapToDto)
                                                                .toList()
                                                ).build()
                                )
                                .status(LocationFolderStatus.ACCEPTED)
                                .build()
                ).build();
    }

    @Override
    public List<LocationFolderDetailsDto> findAllLocationFolders() {
        return locationFolderRepository.findAll()
                .stream()
                .map(locationFolderDetailsDtoMapper::mapToDto)
                .toList();
    }

    @Override
    public List<LocationRequestDto> findAllLocations() {
        return locationRepository.findAll().stream()
                .map(locationCreationRequestDtoMapper::mapToDto)
                .toList();
    }

    @Override
    public List<LocationRequestDto> findAllLocationsByStatus(LocationStatus status) {
        List<Location> locationList = locationRepository.findByStatus(status);
        return locationList.stream()
                        .map(l-> LocationRequestDto.builder()
                                    .startDate(l.getStartDate())
                                    .endDate(l.getEndDate())
                                    .quantity(l.getQuantity())
                                    .equipmentReference(l.getEquipmentUnit().getEquipment().getModel())
                                    .status(l.getStatus().toString())
                                    .build()
                        ).toList();
    }


    public boolean checkIfLocationCouldBePlaced(@Valid LocationRequestDto location){
        List<Location> locations = locationRepository.
                findByStartDateBetweenAndStatusAndEquipmentUnit_EquipmentModelOrEndDateBetweenAndStatusAndEquipmentUnit_EquipmentModel(location.startDate(), location.endDate(),LocationStatus.ACCEPTED,location.equipmentReference(), location.startDate(), location.endDate(),LocationStatus.ACCEPTED,location.equipmentReference());
        System.out.println("locations : "+locations.size());
        Integer reservedQuantityFromTheRequiredModel = locations.stream()
                .map(l -> {
                    Set<LocalDate> reservedDatesForEquipment = getReservedDatesForEquipment(l);
                    if (reservedDatesForEquipment.contains(location.startDate()) || reservedDatesForEquipment.contains(location.endDate()))
                        return l.getQuantity();
                    else return 0;
                })
                .reduce(0, Integer::sum);
        Optional<Equipment> equipment = equipmentRepository.findByModel(location.equipmentReference());
        if(equipment.isEmpty())
            throw new  IllegalArgumentException(String.format("Equipment with reference %s not found", location.equipmentReference()));
        int totalEquipmentUnitForRequiredEquipment = equipment.get().getEquipmentUnits()
                .stream()
                .map(EquipmentUnit::getQuantity)
                .reduce(0, Integer::sum);
        return totalEquipmentUnitForRequiredEquipment - reservedQuantityFromTheRequiredModel >= location.quantity();
    }

    // TODO : add caching
    public Set<LocalDate>  getReservedDatesForEquipment(Location location){
        Set<LocalDate> dates = new HashSet<>();
        for (LocalDate date = location.getStartDate(); date.isBefore(location.getEndDate()); date = date.plusDays(1)) {
            dates.add(date);
        }
        return dates;
    }
}
