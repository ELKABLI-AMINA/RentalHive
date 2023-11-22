package ma.youcode.batispro.repository;

import ma.youcode.batispro.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRespository extends JpaRepository<Client, Long> {

    Optional<Client> findByCin(String cin);
}
