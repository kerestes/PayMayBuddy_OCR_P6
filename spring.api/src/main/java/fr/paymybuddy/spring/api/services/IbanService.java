package fr.paymybuddy.spring.api.services;

import fr.paymybuddy.spring.api.models.Iban;
import fr.paymybuddy.spring.api.repositories.IbanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IbanService {

    @Autowired
    private IbanRepository ibanRepository;

    public Optional<Iban> findByCompteAdresse(String iban, String codeBanque, String codeGuichet, String numeroCompte, String cleRib, Long portefeuilleId){
        return ibanRepository.findByCompteAdresse(iban, codeBanque, codeGuichet, numeroCompte, cleRib, portefeuilleId);
    }

    public Iban save(Iban iban){
        return ibanRepository.save(iban);
    }
}
