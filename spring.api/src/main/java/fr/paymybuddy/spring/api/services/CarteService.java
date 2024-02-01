package fr.paymybuddy.spring.api.services;

import fr.paymybuddy.spring.api.models.Carte;
import fr.paymybuddy.spring.api.repositories.CarteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarteService {

    @Autowired
    private CarteRepository carteRepository;

    public Optional<Carte> verifyCartePortefeuille(String numeroCarte, String email){
        return carteRepository.verifyCartePortefeuille(numeroCarte, email);
    }

    public Carte save(Carte carte){
        return carteRepository.save(carte);
    }
}
