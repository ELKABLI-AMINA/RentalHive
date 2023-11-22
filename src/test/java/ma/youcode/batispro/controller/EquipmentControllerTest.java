package ma.youcode.batispro.controller;

import lombok.RequiredArgsConstructor;
import ma.youcode.batispro.domain.enums.Equipment.EquipmentStatus;
import ma.youcode.batispro.domain.enums.Equipment.EquipmentType;
import ma.youcode.batispro.dto.equipmentDTO.EquipmentResponseDTO;
import ma.youcode.batispro.service.IEquipmentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EquipmentController.class)
@RequiredArgsConstructor
public class EquipmentControllerTest {

    @MockBean
    private IEquipmentService equipmentService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void test_getAllEquipment() throws Exception {
        List<EquipmentResponseDTO> expectedEquipments = new ArrayList<>();
        expectedEquipments.add(new EquipmentResponseDTO(1L, "Equipment 1", EquipmentType.EXCAVATOR, "Model 1", "Description 1", EquipmentStatus.NEW));
        expectedEquipments.add(new EquipmentResponseDTO(2L, "Equipment 2", EquipmentType.BETONNIERE, "Model 2", "Description 2", EquipmentStatus.USED));
        Mockito.when(equipmentService.getAllEquipments()).thenReturn(expectedEquipments);

        mockMvc.perform(get("/v1/equipments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}