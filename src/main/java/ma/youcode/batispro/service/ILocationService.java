package ma.youcode.batispro.service;

import ma.youcode.batispro.domain.enums.Location.LocationStatus;
import ma.youcode.batispro.dto.LocationStatusUpdateDto;
import ma.youcode.batispro.dto.locationDTO.LocationCreationRequestDto;
import ma.youcode.batispro.dto.locationDTO.LocationFolderDetailsDto;
import ma.youcode.batispro.dto.locationDTO.LocationRequestDto;
import ma.youcode.batispro.exception.DossierNotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ILocationService {
    public LocationFolderDetailsDto createLocationFolder(LocationCreationRequestDto locationRequest);
    public LocationFolderDetailsDto consultLocationFolder(String dossierNumber) throws DossierNotFoundException;

    public LocationFolderDetailsDto acceptLocationFolder(String dossierNumber) throws DossierNotFoundException;

    public LocationFolderDetailsDto resolveLocationFolder(String locationFolderNumber,  List<LocationStatusUpdateDto> statusUpdates) throws DossierNotFoundException;

    public List<LocationFolderDetailsDto> findAllLocationFolders(Pageable pageable);

    public List<LocationRequestDto> findAllLocations(Pageable pageable);

    public List<LocationRequestDto> findAllLocationsByStatus(LocationStatus status);

    LocationFolderDetailsDto findLocationFolderByNumber(String dossierNumber) throws DossierNotFoundException;
}
