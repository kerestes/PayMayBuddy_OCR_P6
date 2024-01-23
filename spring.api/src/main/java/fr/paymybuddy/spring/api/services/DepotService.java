package fr.paymybuddy.spring.api.services;

import fr.paymybuddy.spring.api.models.Depot;
import fr.paymybuddy.spring.api.repositories.DepotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepotService {

    @Autowired
    private DepotRepository depotRepository;

    public List<Depot> getDepots(String email){
        List<Depot> depotList = depotRepository.getDepots(email);
        depotList.forEach(depot -> {
            depot.setPortefeuille(null);
        });
        return depotList;
    }
}
