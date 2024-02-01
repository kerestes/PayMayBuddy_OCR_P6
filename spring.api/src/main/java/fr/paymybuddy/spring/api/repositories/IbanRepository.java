package fr.paymybuddy.spring.api.repositories;

import fr.paymybuddy.spring.api.models.Iban;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IbanRepository extends JpaRepository<Iban, Long> {

    @Query("SELECT i FROM Iban i where i.iban = :iban and i.codeBanque = :codeBanque and i.codeGuichet = :codeGuichet and i.numeroCompte = :numeroCompte and i.cleRib = :cleRib and i.portefeuille.id = :portefeuilleId")
    public Optional<Iban> findByCompteAdresse(String iban, String codeBanque, String codeGuichet, String numeroCompte, String cleRib, Long portefeuilleId);
}
