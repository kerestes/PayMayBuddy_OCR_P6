package fr.paymybuddy.spring.api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.paymybuddy.spring.api.models.Carte;
import fr.paymybuddy.spring.api.models.Depot;
import fr.paymybuddy.spring.api.models.Portefeuille;
import fr.paymybuddy.spring.api.services.CarteService;
import fr.paymybuddy.spring.api.services.DepotService;
import fr.paymybuddy.spring.api.services.PortefeuilleService;
import fr.paymybuddy.spring.api.services.authServices.JwtTokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/depot")
public class DepotController {

    @Autowired
    private DepotService depotService;

    @Autowired
    private CarteService carteService;

    @Autowired
    private PortefeuilleService portefeuilleService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @GetMapping
    public ResponseEntity<List<Depot>> getDepots(HttpServletRequest req){
        String token = jwtTokenService.recoveryToken(req);
        String email = jwtTokenService.getSubjectFromToken(token);
        return ResponseEntity.ok(depotService.getDepots(email));
    }

    @PostMapping("/make-depot")
    public ResponseEntity<?> makeDepot(HttpServletRequest req, @RequestBody String depot) throws JsonProcessingException {
        JsonNode node = new ObjectMapper().readTree(depot);
        String token = jwtTokenService.recoveryToken(req);
        String email = jwtTokenService.getSubjectFromToken(token);

        Calendar calendar = Calendar.getInstance();
        calendar.set(node.get("annee").asInt(), node.get("mois").asInt(), 1);

        String numeroCarte = node.get("numeroCarte").asText().replaceAll("\\s+", "");
        Date carteDate = calendar.getTime();

        Portefeuille portefeuille = portefeuilleService.getPortefeuilleByEmail(email);
        Optional<Carte> optionalCarte = carteService.verifyCartePortefeuille(numeroCarte, email);
        Carte newCarte = new Carte();
        if (optionalCarte.isEmpty()){
            newCarte.setPortefeuille(portefeuille);
            newCarte.setNomCarte(node.get("nomCarte").asText());
            newCarte.setNumCarte(numeroCarte);
            newCarte.setMoisCarte(carteDate);
            newCarte.setCryptogramme(node.get("cvv").asInt());
            newCarte = carteService.save(newCarte);
        } else {
            newCarte = optionalCarte.get();
        }

        Depot newDepot = new Depot();
        newDepot.setDateDepot(new Date());
        newDepot.setPortefeuille(portefeuille);
        newDepot.setCarte(newCarte);
        newDepot.setMontantTotal(node.get("montant").decimalValue());

        return ResponseEntity.ok(depotService.save(newDepot));
    }
}
