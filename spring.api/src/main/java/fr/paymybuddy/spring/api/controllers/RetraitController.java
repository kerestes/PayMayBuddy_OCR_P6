package fr.paymybuddy.spring.api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.paymybuddy.spring.api.models.Iban;
import fr.paymybuddy.spring.api.models.Portefeuille;
import fr.paymybuddy.spring.api.models.Retrait;
import fr.paymybuddy.spring.api.services.IbanService;
import fr.paymybuddy.spring.api.services.PortefeuilleService;
import fr.paymybuddy.spring.api.services.RetraitService;
import fr.paymybuddy.spring.api.services.authServices.JwtTokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/retrait")
public class RetraitController {

    @Autowired
    private RetraitService retraitService;
    @Autowired
    private PortefeuilleService portefeuilleService;
    @Autowired
    private IbanService ibanService;
    @Autowired
    private JwtTokenService jwtTokenService;

    @GetMapping
    public ResponseEntity<List<Retrait>> getRetraits(HttpServletRequest request){
        String token = jwtTokenService.recoveryToken(request);
        String email = jwtTokenService.getSubjectFromToken(token);
        return ResponseEntity.ok(retraitService.getRetraits(email));
    }

    @PostMapping("/make-retrait")
    public ResponseEntity<?> makeRetrait(HttpServletRequest request, @RequestBody String retrait) throws JsonProcessingException {
        JsonNode node = new ObjectMapper().readTree(retrait);
        String token = jwtTokenService.recoveryToken(request);
        String email = jwtTokenService.getSubjectFromToken(token);

        Portefeuille portefeuille = portefeuilleService.getPortefeuilleByEmail(email);
        if(portefeuille.getSolde().compareTo(node.get("montant").decimalValue()) > 0){
            String iban = node.get("iban").asText();

            Iban realIban = new Iban();
            realIban.setPortefeuille(portefeuille);
            realIban.setBic(node.get("bic").asText());
            realIban.setIban(iban.substring(0, 4));
            realIban.setCodeBanque(iban.substring(4, 9));
            realIban.setCodeGuichet(iban.substring(9, 14));
            realIban.setNumeroCompte(iban.substring(14, iban.length() -2));
            realIban.setCleRib(iban.substring(iban.length() -2, iban.length()));

            Optional<Iban> ibanOptional = ibanService.findByCompteAdresse(realIban.getIban(), realIban.getCodeBanque(),
                    realIban.getCodeGuichet(), realIban.getNumeroCompte(), realIban.getCleRib(), realIban.getPortefeuille().getId());
            if(ibanOptional.isPresent()){
                realIban = ibanOptional.get();
            } else {
                realIban = ibanService.save(realIban);
            }

            Retrait realRetrait = new Retrait();
            realRetrait.setPortefeuille(portefeuille);
            realRetrait.setDateRetrait(new Date());
            realRetrait.setIban(realIban);
            realRetrait.setMontantTotal(node.get("montant").decimalValue());

            return ResponseEntity.ok(retraitService.save(realRetrait));
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }
}
