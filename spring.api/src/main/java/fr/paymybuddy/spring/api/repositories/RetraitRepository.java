package fr.paymybuddy.spring.api.repositories;

import fr.paymybuddy.spring.api.models.Retrait;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface RetraitRepository extends JpaRepository<Retrait, Long> {

    @Query("SELECT r FROM Retrait r WHERE r.portefeuille.user.email = :email")
    public List<Retrait> getRetraits(String email);
}
