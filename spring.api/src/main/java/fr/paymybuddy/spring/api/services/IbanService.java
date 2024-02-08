package fr.paymybuddy.spring.api.services;

import fr.paymybuddy.spring.api.models.DTO.IbanDTO;
import fr.paymybuddy.spring.api.models.Iban;
import fr.paymybuddy.spring.api.repositories.IbanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IbanService {

    @Autowired
    private IbanRepository ibanRepository;

    public Optional<IbanDTO> findByCompteAdresse(String iban, String codeBanque, String codeGuichet, String numeroCompte, String cleRib, Long portefeuilleId){
        Optional<Iban> optionalIban = ibanRepository.findByCompteAdresse(iban, codeBanque, codeGuichet, numeroCompte, cleRib, portefeuilleId);
        if ((optionalIban.isPresent()))
            return Optional.of(cleanIban(new IbanDTO(optionalIban.get())));
        return Optional.empty();
    }

    public IbanDTO save(Iban iban){
        return cleanIban(new IbanDTO(ibanRepository.save(iban)));
    }

    private IbanDTO cleanIban(IbanDTO newIban){
        newIban.getPortefeuille().nullSolde();
        newIban.getPortefeuille().getUser().nullAdresse();
        return newIban;
    }
}
