package fr.paymybuddy.spring.api.repositories;

import fr.paymybuddy.spring.api.models.Depot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface DepotRepository extends JpaRepository<Depot, Long> {

    @Query("SELECT d FROM Depot d WHERE d.portefeuille.user.email = :email")
    public List<Depot> getDepots(String email);
}
