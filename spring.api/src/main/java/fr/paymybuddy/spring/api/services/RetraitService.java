package fr.paymybuddy.spring.api.services;

import fr.paymybuddy.spring.api.models.DTO.RetraitDTO;
import fr.paymybuddy.spring.api.models.Retrait;
import fr.paymybuddy.spring.api.repositories.RetraitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RetraitService {

    @Autowired
    private RetraitRepository retraitRepository;

    public List<RetraitDTO> getRetraits(String email){
        List<Retrait> retraitList = retraitRepository.getRetraits(email);
        return retraitList.stream().map(retrait -> cleanRetrait(new RetraitDTO(retrait))).collect(Collectors.toList());
    }

    public RetraitDTO save(Retrait retrait){
        return cleanRetrait(new RetraitDTO(retraitRepository.save(retrait)));
    }

    public RetraitDTO cleanRetrait(RetraitDTO newRetrait){
        newRetrait.getPortefeuille().nullSolde();
        newRetrait.getPortefeuille().getUser().nullAdresse();
        return newRetrait;
    }
}
