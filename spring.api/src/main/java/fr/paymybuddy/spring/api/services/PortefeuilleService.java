package fr.paymybuddy.spring.api.services;

import fr.paymybuddy.spring.api.models.DTO.PortefeuilleDTO;
import fr.paymybuddy.spring.api.models.Portefeuille;
import fr.paymybuddy.spring.api.models.User;
import fr.paymybuddy.spring.api.repositories.PortefeuilleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PortefeuilleService {

    @Autowired
    private PortefeuilleRepository portefeuilleRepository;

    public Portefeuille save(Portefeuille portefeuille){return portefeuilleRepository.save(portefeuille);}

    public Optional<PortefeuilleDTO> getPortefeuilleByEmail(String email){
        Optional<Portefeuille> optionalPortefeuille = portefeuilleRepository.getPortefeuilleByEmail(email);
        if(optionalPortefeuille.isPresent())
            return Optional.of(new PortefeuilleDTO(optionalPortefeuille.get()));
        return Optional.empty();
    }
}
