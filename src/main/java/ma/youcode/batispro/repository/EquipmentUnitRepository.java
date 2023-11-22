package ma.youcode.batispro.repository;

import ma.youcode.batispro.domain.entity.EquipmentUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EquipmentUnitRepository extends JpaRepository<EquipmentUnit, Long> {
    Optional<EquipmentUnit> findByRef(String ref);
}
