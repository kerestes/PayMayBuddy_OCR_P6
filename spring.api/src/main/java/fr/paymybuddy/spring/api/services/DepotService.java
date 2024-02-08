package fr.paymybuddy.spring.api.services;

import fr.paymybuddy.spring.api.models.DTO.DepotDTO;
import fr.paymybuddy.spring.api.models.Depot;
import fr.paymybuddy.spring.api.repositories.DepotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepotService {

    @Autowired
    private DepotRepository depotRepository;

    public List<DepotDTO> getDepots(String email){
        List<Depot> depotList = depotRepository.getDepots(email);
        return depotList.stream().map(depot -> cleanDepot(new DepotDTO(depot))).collect(Collectors.toList());
    }

    public DepotDTO save(Depot depot){
        return cleanDepot(new DepotDTO(depotRepository.save(depot)));
    }

    public DepotDTO cleanDepot(DepotDTO newDepot){
        newDepot.getPortefeuille().nullSolde();
        newDepot.getPortefeuille().getUser().nullAdresse();
        return newDepot;
    }
}
