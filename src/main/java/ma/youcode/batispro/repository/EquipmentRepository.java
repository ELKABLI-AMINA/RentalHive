package ma.youcode.batispro.repository;

import ma.youcode.batispro.domain.entity.Equipment;
import ma.youcode.batispro.domain.enums.Equipment.EquipmentStatus;
import ma.youcode.batispro.domain.enums.Equipment.EquipmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    List<Equipment> findByNameAndEquipmentTypeAndEquipmentStatus(String name, EquipmentType equipmentType, EquipmentStatus equipmentStatus);

    Optional<Equipment> findByModel(String model);
}
