package ma.youcode.batispro;

import ma.youcode.batispro.domain.entity.Equipment;
import ma.youcode.batispro.repository.EquipmentRepository;
import ma.youcode.batispro.service.Impl.EquipmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {
	private Equipment equipment;

	@Mock
	private EquipmentRepository equipmentRepository;

	@InjectMocks
	private EquipmentServiceImpl equipmentService;
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}






}
