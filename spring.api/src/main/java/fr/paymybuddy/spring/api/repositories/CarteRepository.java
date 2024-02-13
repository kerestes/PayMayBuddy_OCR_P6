package fr.paymybuddy.spring.api.repositories;

import fr.paymybuddy.spring.api.models.Carte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface CarteRepository extends JpaRepository<Carte, Long> {

    @Query("SELECT c FROM Carte c where numCarte = :numeroCarte AND portefeuille.user.email = :email")
    public Optional<Carte> verifyCartePortefeuille(String numeroCarte, String email);
}
