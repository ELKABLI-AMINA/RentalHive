package ma.youcode.batispro.service.Impl;

import ma.youcode.batispro.domain.enums.Equipment.EquipmentStatus;
import ma.youcode.batispro.domain.enums.Equipment.EquipmentType;
import ma.youcode.batispro.dto.equipmentDTO.EquipmentCreationRequestDTO;
import ma.youcode.batispro.dto.equipmentDTO.EquipmentResponseDTO;
import ma.youcode.batispro.dto.equipmentDTO.EquipmentUpdateRequestDTO;
import ma.youcode.batispro.exception.EquipmentNotFoundException;
import ma.youcode.batispro.repository.EquipmentRepository;
import ma.youcode.batispro.domain.entity.Equipment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EquipmentServiceImplTest {

    @InjectMocks
    private EquipmentServiceImpl equipmentServiceImpl;

    @Mock
    private EquipmentRepository equipmentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_deleteEquipment_success(){
        when(equipmentRepository.findById(1L)).thenReturn(Optional.of(new Equipment()));
        equipmentServiceImpl.deleteEquipmentById(1L);
        Mockito.verify(equipmentRepository, Mockito.times(1)).deleteById(1L);
    }


    @Test
    public void test_deleteEquipment_notFound() {
        when(equipmentRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EquipmentNotFoundException.class, () -> {
            equipmentServiceImpl.deleteEquipmentById(1L);
        });
    }





   /*  @Test
        public void test_deleteEquipment_dataAccessException() {
        when(equipmentRepository.findById(1L)).thenThrow(DataAccessException.class);
        assertThrows(RuntimeException.class, () -> {
            equipmentServiceImpl.deleteEquipmentById(1L);
        });
    }

    */

    @Test
    public void test_deleteEquipment_multipleEquipments() {
        when(equipmentRepository.findById(1L)).thenReturn(Optional.of(new Equipment()));
        when(equipmentRepository.findById(2L)).thenReturn(Optional.of(new Equipment()));
        equipmentServiceImpl.deleteEquipmentById(1L);
        Mockito.verify(equipmentRepository, Mockito.times(1)).deleteById(1L);
        equipmentServiceImpl.deleteEquipmentById(2L);
        Mockito.verify(equipmentRepository, Mockito.times(1)).deleteById(2L);
    }

    @Test
    public void test_getEquipmentById_success() {

        Equipment mockEquipment = new Equipment(1L, "Equipment 1", EquipmentType.EXCAVATOR, "Model 1", "Description 1", EquipmentStatus.NEW);
        Mockito.when(equipmentRepository.findById(1L)).thenReturn(Optional.of(mockEquipment));

        EquipmentResponseDTO result = equipmentServiceImpl.getEquipmentById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Equipment 1", result.name());
        assertEquals(EquipmentType.EXCAVATOR, result.equipmentType());
        assertEquals("Model 1", result.model());
        assertEquals("Description 1", result.description());
        assertEquals(EquipmentStatus.NEW, result.equipmentStatus());
    }
    @Test
    public void test_getEquipmentById_notFound() {
        Mockito.when(equipmentRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EquipmentNotFoundException.class, () -> equipmentServiceImpl.getEquipmentById(1L));
    }

    @Test
    public void test_getEquipmentById_dataAccessException() {
        Mockito.when(equipmentRepository.findById(1L)).thenThrow(new RuntimeException("Database error"));
        assertThrows(RuntimeException.class, () -> equipmentServiceImpl.getEquipmentById(1L));
    }
    @Test
    public void test_getAllEquipments_success() {
        Equipment equipment1 = new Equipment(1L, "Equipment 1", EquipmentType.EXCAVATOR, "Model 1", "Description 1", EquipmentStatus.NEW);
        Equipment equipment2 = new Equipment(2L, "Equipment 2", EquipmentType.BETONNIERE, "Model 2", "Description 2", EquipmentStatus.USED);
        when(equipmentRepository.findAll()).thenReturn(Arrays.asList(equipment1, equipment2));
        List<EquipmentResponseDTO> result = equipmentServiceImpl.getAllEquipments();
        assertEquals(2, result.size());
        assertEquals("Equipment 1", result.get(0).name());
        assertEquals("Equipment 2", result.get(1).name());

    }
    @Test
    public void test_getAllEquipments_emptyList() {

        when(equipmentRepository.findAll()).thenReturn(List.of());
        List<EquipmentResponseDTO> result = equipmentServiceImpl.getAllEquipments();
        assertEquals(0, result.size());
    }
    @Test
    public void test_getAllEquipments_exception() {
        when(equipmentRepository.findAll()).thenThrow(new RuntimeException("Error occurred"));
        assertThrows(RuntimeException.class, () -> equipmentServiceImpl.getAllEquipments());
    }
    @Test
    public void test_searchEquipment_success() {

        EquipmentType equipmentType = EquipmentType.EXCAVATOR;
        EquipmentStatus equipmentStatus = EquipmentStatus.NEW;
        List<Equipment> mockEquipmentList = Collections.singletonList(new Equipment());
        Mockito.when(equipmentRepository.findByNameAndEquipmentTypeAndEquipmentStatus("Excavator", equipmentType, equipmentStatus))
                .thenReturn(mockEquipmentList);
        List<EquipmentResponseDTO> result = equipmentServiceImpl.searchEquipment("Excavator", equipmentType, equipmentStatus);
        assertEquals(mockEquipmentList.size(), result.size());
    }
    @Test
    public void test_searchEquipment_noResult() {
        EquipmentType equipmentType = EquipmentType.EXCAVATOR;
        EquipmentStatus equipmentStatus = EquipmentStatus.NEW;
        Mockito.when(equipmentRepository.findByNameAndEquipmentTypeAndEquipmentStatus("Nonexistent", equipmentType, equipmentStatus))
                .thenReturn(Collections.emptyList());
        assertThrows(EquipmentNotFoundException.class, () ->
                equipmentServiceImpl.searchEquipment("Nonexistent", equipmentType, equipmentStatus));
    }
    @Test
    public void test_searchEquipment_emptyCriteria() {
        String name = null;
        EquipmentType equipmentType = null;
        EquipmentStatus equipmentStatus = null;
        assertThrows(EquipmentNotFoundException.class, () ->
                equipmentServiceImpl.searchEquipment(name, equipmentType, equipmentStatus));
    }

    @Test
    public void test_searchEquipment_multipleResults() {


        EquipmentType equipmentType = EquipmentType.EXCAVATOR;
        EquipmentStatus equipmentStatus = EquipmentStatus.NEW;
        List<Equipment> mockEquipmentList = List.of(
                new Equipment(),
                new Equipment()
        );

        Mockito.when(equipmentRepository.findByNameAndEquipmentTypeAndEquipmentStatus("Excavator", equipmentType, equipmentStatus))
                .thenReturn(mockEquipmentList);

        List<EquipmentResponseDTO> result = equipmentServiceImpl.searchEquipment("Excavator", equipmentType, equipmentStatus);
        assertEquals(mockEquipmentList.size(), result.size());

    }
    @Test
    public void test_createEquipment_success() {

        EquipmentCreationRequestDTO equipmentRequest = new EquipmentCreationRequestDTO("Equipment 1", "Model 1", EquipmentType.EXCAVATOR, "Description 1", EquipmentStatus.NEW);
        Equipment expectedEquipment = new Equipment(1L, "Equipment 1", EquipmentType.EXCAVATOR, "Model 1", "Description 1", EquipmentStatus.NEW);

        Mockito.when(equipmentRepository.save(Mockito.any(Equipment.class))).thenReturn(expectedEquipment);


        EquipmentResponseDTO result = equipmentServiceImpl.createEquipment(equipmentRequest);

        // Assert
        assertNotNull(result);
        assertEquals(expectedEquipment.getId(), result.id());
        assertEquals(expectedEquipment.getName(), result.name());
        assertEquals(expectedEquipment.getEquipmentType(), result.equipmentType());
        assertEquals(expectedEquipment.getModel(), result.model());
        assertEquals(expectedEquipment.getDescription(), result.description());
        assertEquals(expectedEquipment.getEquipmentStatus(), result.equipmentStatus());

        Mockito.verify(equipmentRepository).save(Mockito.any(Equipment.class));
    }

    @Test
   public void test_createEquipment_dataAccessException() {

       EquipmentCreationRequestDTO equipmentRequest = new EquipmentCreationRequestDTO("Equipment 1", "Model 1", EquipmentType.EXCAVATOR, "Description 1", EquipmentStatus.NEW);
       when(equipmentRepository.save(any())).thenThrow(new DataAccessException("Simulated data access exception") {});
       assertThrows(RuntimeException.class, () -> equipmentServiceImpl.createEquipment(equipmentRequest));
   }
    @Test
    public void test_createEquipment_emptyFields() {

        EquipmentCreationRequestDTO equipmentRequest = new EquipmentCreationRequestDTO("", "", null, "", null);

        assertThrows(NullPointerException.class, () -> {
            equipmentServiceImpl.createEquipment(equipmentRequest);
        });
    }


    @Test
    public void test_updateEquipment_shouldThrowNullPointerExceptionIfIdIsNull() {
        // Arrange
        Long id = null;
        EquipmentUpdateRequestDTO updateRequestDTO = new EquipmentUpdateRequestDTO("Updated Name", "Updated Model", EquipmentType.BETONNIERE, "Updated Description", EquipmentStatus.OLD);

        // Act and Assert
        assertThrows(NullPointerException.class, () -> equipmentServiceImpl.updateEquipment(id, updateRequestDTO));
    }




}
