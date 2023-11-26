package ma.youcode.batispro.service.Impl;

import ma.youcode.batispro.domain.entity.Equipment;
import ma.youcode.batispro.domain.entity.EquipmentUnit;
import ma.youcode.batispro.domain.entity.Location;
import ma.youcode.batispro.domain.enums.Equipment.EquipmentStatus;
import ma.youcode.batispro.domain.enums.Location.LocationStatus;
import ma.youcode.batispro.dto.locationDTO.LocationRequestDto;
import ma.youcode.batispro.repository.EquipmentRepository;
import ma.youcode.batispro.repository.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
//@ExtendWith(MockitoExtension.class)
class LocationServiceImplTest {

    @Mock
    private LocationRepository locationRepository ;
    @Mock
    private EquipmentRepository equipmentRepository ;

    @InjectMocks
    private LocationService locationService ;

    private List<Location> existReservations;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        String model = "model";
//        // 4 reservation of model-x between 2023-09-30 and 2023-10-25   with total quantity = 8
//        existReservations = List.of(
//                Location.builder()
//                        .id(1L)
//                        .equipmentUnit(EquipmentUnit.builder().equipment(Equipment.builder().model(model).build()).build())
//                        .startDate(LocalDate.of(2023, 9,30 )).endDate(LocalDate.of(2023, 10, 5))
//                        .status(LocationStatus.ACCEPTED)
//                        .quantity(1)
//                        .build(),
//                Location.builder()
//                        .id(2L)
//                        .equipmentUnit(EquipmentUnit.builder().equipment(Equipment.builder().model(model).build()).build())
//                        .startDate(LocalDate.of(2023, 10, 1))
//                        .endDate(LocalDate.of(2023, 10, 10))
//                        .status(LocationStatus.ACCEPTED)
//                        .quantity(2)
//                        .build(),
//                Location.builder().id(3L)
//                        .equipmentUnit(EquipmentUnit.builder().equipment(Equipment.builder().model(model).build()).build())
//                        .startDate(LocalDate.of(2023, 10, 15))
//                        .endDate(LocalDate.of(2023, 10, 20))
//                        .status(LocationStatus.ACCEPTED)
//                        .quantity(3)
//                        .build(),
//                Location.builder()
//                        .id(4L)
//                        .equipmentUnit(EquipmentUnit.builder().equipment(Equipment.builder().model(model).build()).build())
//                        .startDate(LocalDate.of(2023, 10, 20))
//                        .endDate(LocalDate.of(2023, 10, 25))
//                        .status(LocationStatus.ACCEPTED)
//                        .quantity(2)
//                        .build()
//        );

//    }

//    @Test
//    void should_return_true_if_equipment_has_enough_quantity_available() {
//        LocalDate startDate = LocalDate.of(2023, 10, 10);
//        LocalDate endDate = LocalDate.of(2023, 10, 20);
//        String model ="model";
//        LocationRequestDto locationRequest = LocationRequestDto.builder()
//                .equipmentReference(model)
//                .status("USED")
//                .quantity(3)
//                .startDate(startDate)
//                .endDate(endDate)
//                .build();
//        Equipment equipment = Equipment.builder()
//                .model(model)
//                .equipmentStatus(EquipmentStatus.USED)
//                .equipmentUnits(
//                        List.of(
//                                EquipmentUnit.builder().id(1L).build(),
//                                EquipmentUnit.builder().id(2L).build(),
//                                EquipmentUnit.builder().id(3L).build(),
//                                EquipmentUnit.builder().id(4L).build(),
//                                EquipmentUnit.builder().id(5L).build(),
//                                EquipmentUnit.builder().id(6L).build(),
//                                EquipmentUnit.builder().id(7L).build(),
//                                EquipmentUnit.builder().id(8L).build(),
//                                EquipmentUnit.builder().id(9L).build(),
//                                EquipmentUnit.builder().id(10L).build()
//                        )
//                )
//                .build();
//        when(locationRepository
//                .findByStartDateBetweenAndStatusAndEquipmentUnit_EquipmentModelOrEndDateBetweenAndStatusAndEquipmentUnit_EquipmentModel(startDate,endDate,LocationStatus.ACCEPTED,model,startDate,endDate, LocationStatus.ACCEPTED,model))
//                .thenReturn(existReservations);
//        when(equipmentRepository
//                .findByModel(model))
//                .thenReturn(Optional.of(equipment));
//
//        boolean result = locationService.checkIfLocationCouldBePlaced(locationRequest);
//
//        verify(locationRepository).findByStartDateBetweenAndStatusAndEquipmentUnit_EquipmentModelOrEndDateBetweenAndStatusAndEquipmentUnit_EquipmentModel(startDate, endDate,LocationStatus.ACCEPTED,model, startDate, endDate, LocationStatus.ACCEPTED,model );
//        verify(equipmentRepository).findByModel(model);
//        assertTrue(result);
//    }
}