package ma.youcode.batispro.controller;

import lombok.RequiredArgsConstructor;
import ma.youcode.batispro.dto.LocationStatusUpdateDto;
import ma.youcode.batispro.dto.locationDTO.LocationCreationRequestDto;
import ma.youcode.batispro.dto.locationDTO.LocationFolderDetailsDto;
import ma.youcode.batispro.dto.locationDTO.LocationRequestDto;
import ma.youcode.batispro.exception.DossierNotFoundException;
import ma.youcode.batispro.service.ILocationService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/v1/locations")
@RequiredArgsConstructor
public class LocationController {
    private final ILocationService locationService;

    @PostMapping
    public ResponseEntity<LocationFolderDetailsDto> createLocation(@RequestBody @Valid LocationCreationRequestDto locationCreationRequestDto) throws URISyntaxException {
        LocationFolderDetailsDto locationFolder = locationService.createLocationFolder(locationCreationRequestDto);
        return ResponseEntity
                .created(new URI("/v1/locations"))
                .body(locationFolder);
    }
    @GetMapping
    public ResponseEntity<List<LocationRequestDto>> getAllLocationsOpened(@RequestParam(defaultValue = "0",required = false) int page, @RequestParam(defaultValue = "10",required = false) int size){
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        List<LocationRequestDto> allLocationFolders = locationService.findAllLocations(pageable);
        return ResponseEntity.ok(allLocationFolders);
    }
    @PostMapping("/folder/{folderNumber}")
    public ResponseEntity<LocationFolderDetailsDto> consultLocationFolder(@NotBlank @PathVariable String folderNumber) throws DossierNotFoundException {
        LocationFolderDetailsDto locationFolder = locationService.consultLocationFolder(folderNumber);
        return ResponseEntity.ok(locationFolder);
    }

    @PostMapping("/folder/{folderNumber}/validate")
    public ResponseEntity<LocationFolderDetailsDto> validateLocationFolder(@NotBlank @PathVariable String folderNumber) throws DossierNotFoundException {
        LocationFolderDetailsDto locationFolder = locationService.acceptLocationFolder(folderNumber);
        return ResponseEntity.ok(locationFolder);
    }

    @GetMapping(value = "/folder")
    public ResponseEntity<List<LocationFolderDetailsDto>> getAllLocationFolders(@RequestParam(defaultValue = "0",required = false) int page, @RequestParam(defaultValue = "10",required = false) int size){
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        List<LocationFolderDetailsDto> allLocationFolders = locationService.findAllLocationFolders(pageable);
        return ResponseEntity.ok(allLocationFolders);
    }

    @PostMapping("/folder/{folderNumber}/resolve")
    public ResponseEntity<LocationFolderDetailsDto> resolveLocationFolder(@NotBlank @PathVariable String folderNumber, @RequestBody List<LocationStatusUpdateDto> statusUpdates) throws DossierNotFoundException {
        LocationFolderDetailsDto locationFolder = locationService.resolveLocationFolder(folderNumber, statusUpdates);
        return ResponseEntity.ok(locationFolder);
    }




}
