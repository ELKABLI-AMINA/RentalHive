package ma.youcode.batispro.repository;

import ma.youcode.batispro.domain.entity.DossierLocation;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface LocationFolderRepository extends PagingAndSortingRepository<DossierLocation, Long> {
    Optional<DossierLocation> findByDossierNumber(String dossierNumber);
}
