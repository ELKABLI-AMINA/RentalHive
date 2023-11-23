package ma.youcode.RentalHive.repository;

import ma.youcode.RentalHive.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRespository extends JpaRepository<Client, Long> {

    Optional<Client> findByCin(String cin);
}
