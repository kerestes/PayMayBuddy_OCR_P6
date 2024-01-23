package fr.paymybuddy.spring.api.services;

import fr.paymybuddy.spring.api.models.TransactionBuddys;
import fr.paymybuddy.spring.api.repositories.TransactionBuddyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionBuddysService {

    @Autowired
    private TransactionBuddyRepository transactionBuddyRepository;

    public List<TransactionBuddys> getTransactions(String email){
        return transactionBuddyRepository.getTransaction(email).stream()
                .map(transactionBuddys -> {
                    transactionBuddys.getPortefeuilleOrigine().setSolde(null);
                    transactionBuddys.getPortefeuilleOrigine().setUpdateDate(null);
                    transactionBuddys.getPortefeuilleOrigine().getUser().setStatus(null);
                    transactionBuddys.getPortefeuilleOrigine().getUser().setAdresse(null);
                    transactionBuddys.getPortefeuilleOrigine().getUser().setConfirmPassword(null);
                    transactionBuddys.getPortefeuilleOrigine().getUser().setPassword(null);
                    transactionBuddys.getPortefeuilleDestination().setSolde(null);
                    transactionBuddys.getPortefeuilleDestination().setUpdateDate(null);
                    transactionBuddys.getPortefeuilleDestination().getUser().setStatus(null);
                    transactionBuddys.getPortefeuilleDestination().getUser().setAdresse(null);
                    transactionBuddys.getPortefeuilleDestination().getUser().setConfirmPassword(null);
                    transactionBuddys.getPortefeuilleDestination().getUser().setPassword(null);
                    return transactionBuddys;
                }).collect(Collectors.toList());
    }
}
