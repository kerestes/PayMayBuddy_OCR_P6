package fr.paymybuddy.spring.api.services;

import fr.paymybuddy.spring.api.models.Carte;
import fr.paymybuddy.spring.api.models.DTO.CarteDTO;
import fr.paymybuddy.spring.api.repositories.CarteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarteService {

    @Autowired
    private CarteRepository carteRepository;

    public Optional<CarteDTO> verifyCartePortefeuille(String numeroCarte, String email){
         Optional<Carte> optionalCarte = carteRepository.verifyCartePortefeuille(numeroCarte, email);
         if (optionalCarte.isPresent())
             return Optional.of(new CarteDTO(optionalCarte.get()));
         return Optional.empty();
    }

    public CarteDTO save(Carte carte){
        return new CarteDTO(carteRepository.save(carte));
    }
}
