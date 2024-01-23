package fr.paymybuddy.spring.api.services;

import fr.paymybuddy.spring.api.models.Portefeuille;
import fr.paymybuddy.spring.api.repositories.PortefeuilleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PortefeuilleService {

    @Autowired
    private PortefeuilleRepository portefeuilleRepository;

    public Portefeuille save(Portefeuille portefeuille){return portefeuilleRepository.save(portefeuille);}

    public Portefeuille getPortefeuilleByEmail(String email){
        return portefeuilleRepository.getPortefeuilleByEmail(email);
    }
}
