package fr.paymybuddy.spring.api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.paymybuddy.spring.api.models.DTO.PortefeuilleDTO;
import fr.paymybuddy.spring.api.models.DTO.TransactionBuddysDTO;
import fr.paymybuddy.spring.api.models.Portefeuille;
import fr.paymybuddy.spring.api.models.TransactionBuddys;
import fr.paymybuddy.spring.api.services.ConnectionBuddyService;
import fr.paymybuddy.spring.api.services.PortefeuilleService;
import fr.paymybuddy.spring.api.services.TransactionBuddysService;
import fr.paymybuddy.spring.api.services.authServices.JwtTokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TransactionController {

    @Autowired
    private TransactionBuddysService transactionBuddysService;
    @Autowired
    private PortefeuilleService portefeuilleService;
    @Autowired
    private ConnectionBuddyService connectionBuddyService;
    @Autowired
    private JwtTokenService jwtTokenService;

    @GetMapping("/transfer-list")
    public ResponseEntity<List<TransactionBuddysDTO>> getTransaction(HttpServletRequest req){
        String token = jwtTokenService.recoveryToken(req);
        String email = jwtTokenService.getSubjectFromToken(token);
        return ResponseEntity.ok(transactionBuddysService.getTransactions(email));
    }

    @PostMapping("/make-transfer")
    public ResponseEntity<TransactionBuddysDTO> makeTransaction(HttpServletRequest req, @RequestBody String makeTransferObject) throws JsonProcessingException {
        JsonNode node = new ObjectMapper().readTree(makeTransferObject);
        String token = jwtTokenService.recoveryToken(req);
        String email = jwtTokenService.getSubjectFromToken(token);
        Optional<PortefeuilleDTO> portefeuilleOrigine = portefeuilleService.getPortefeuilleByEmail(email);
        Optional<PortefeuilleDTO>  portefeuilleDestination = portefeuilleService.getPortefeuilleByEmail(node.get("email").asText());
        BigDecimal montantTotal = new BigDecimal(node.get("montant").asDouble());
        if(portefeuilleOrigine.isPresent() && portefeuilleOrigine.get().getSolde().doubleValue() > montantTotal.doubleValue()){
           if(connectionBuddyService.verifyConnection(portefeuilleOrigine.get().getUser(), portefeuilleDestination.get().getUser())){
               TransactionBuddys newTransaction = new TransactionBuddys();
               newTransaction.setMontantTotal(montantTotal);
               newTransaction.setDateTransaction(new Date());
               newTransaction.setPortefeuilleOrigine(new Portefeuille(portefeuilleOrigine.get()));
               newTransaction.setPortefeuilleDestination(new Portefeuille(portefeuilleDestination.get()));
               return ResponseEntity.ok(transactionBuddysService.save(newTransaction));
           } else {
               return ResponseEntity.notFound().build();
           }
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }
}
