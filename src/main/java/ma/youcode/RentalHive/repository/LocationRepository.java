package ma.youcode.RentalHive.repository;

import liquibase.pro.packaged.S;
import ma.youcode.RentalHive.domain.entity.Location;
import ma.youcode.RentalHive.domain.enums.Location.LocationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByStatus(LocationStatus status);

    List<Location> findByStartDateBetweenOrEndDateBetweenAndEquipmentUnit_EquipmentModelAndStatus(LocalDate startDate, LocalDate endDate, LocalDate startDate1, LocalDate endDate1, String model ,LocationStatus status);
}
