package fr.paymybuddy.spring.api.services;

import fr.paymybuddy.spring.api.models.DTO.TransactionBuddysDTO;
import fr.paymybuddy.spring.api.models.Portefeuille;
import fr.paymybuddy.spring.api.models.TransactionBuddys;
import fr.paymybuddy.spring.api.repositories.TransactionBuddyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionBuddysService {

    @Autowired
    private TransactionBuddyRepository transactionBuddyRepository;

    public List<TransactionBuddysDTO> getTransactions(String email){
        return transactionBuddyRepository.getTransaction(email).stream()
                .map(transactionBuddys ->  cleanTransaction(new TransactionBuddysDTO(transactionBuddys))).collect(Collectors.toList());
    }

    public TransactionBuddysDTO save(TransactionBuddys transactionBuddys){
        return cleanTransaction(new TransactionBuddysDTO(transactionBuddyRepository.save(transactionBuddys)));
    }

    private TransactionBuddysDTO cleanTransaction(TransactionBuddysDTO newTransaction){
        newTransaction.getPortefeuilleOrigine().nullSolde();
        newTransaction.getPortefeuilleOrigine().getUser().nullAdresse();
        newTransaction.getPortefeuilleDestination().nullSolde();
        newTransaction.getPortefeuilleDestination().getUser().nullAdresse();
        return newTransaction;
    }

}
