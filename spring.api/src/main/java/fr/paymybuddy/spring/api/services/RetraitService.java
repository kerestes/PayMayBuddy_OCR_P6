package fr.paymybuddy.spring.api.services;

import fr.paymybuddy.spring.api.models.Retrait;
import fr.paymybuddy.spring.api.repositories.RetraitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RetraitService {

    @Autowired
    private RetraitRepository retraitRepository;

    public List<Retrait> getRetraits(String email){
        List<Retrait> retraitList = retraitRepository.getRetraits(email);
        retraitList.forEach(retrait -> {
            retrait.setPortefeuille(null);
        });
        return retraitList;
    }

    public Retrait save(Retrait retrait){
        return retraitRepository.save(retrait);
    }
}
