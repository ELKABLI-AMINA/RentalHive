package ma.youcode.batispro.repository;


import ma.youcode.batispro.domain.entity.Equipment;
import ma.youcode.batispro.domain.entity.EquipmentUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

@Repository
public interface EquipmentUnitRepository extends JpaRepository<EquipmentUnit, Long> {
    Optional<EquipmentUnit> findByRef(String ref);
    @Query("SELECT u FROM EquipmentUnit u JOIN FETCH u.equipment WHERE u.id = :id")
    List<EquipmentUnit> findByEquipmentUnitWithEquipment(@Param("id") Long id);

}
