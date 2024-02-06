package fr.paymybuddy.spring.api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.paymybuddy.spring.api.models.Carte;
import fr.paymybuddy.spring.api.models.DTO.CarteDTO;
import fr.paymybuddy.spring.api.models.DTO.DepotDTO;
import fr.paymybuddy.spring.api.models.DTO.PortefeuilleDTO;
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
    public ResponseEntity<List<DepotDTO>> getDepots(HttpServletRequest req){
        String token = jwtTokenService.recoveryToken(req);
        String email = jwtTokenService.getSubjectFromToken(token);
        return ResponseEntity.ok(depotService.getDepots(email));
    }

    @PostMapping("/make-depot")
    public ResponseEntity<DepotDTO> makeDepot(HttpServletRequest req, @RequestBody String depot) throws JsonProcessingException {
        JsonNode node = new ObjectMapper().readTree(depot);
        String token = jwtTokenService.recoveryToken(req);
        String email = jwtTokenService.getSubjectFromToken(token);

        String numeroCarte = node.get("numeroCarte").asText().replaceAll("\\s+", "");

        Optional<PortefeuilleDTO> portefeuilleDTO = portefeuilleService.getPortefeuilleByEmail(email);
        Optional<CarteDTO> optionalCarte = carteService.verifyCartePortefeuille(numeroCarte, email);

        if (optionalCarte.isEmpty()){
            Carte newCarte = new Carte();

            Calendar calendar = Calendar.getInstance();
            calendar.set(node.get("annee").asInt(), node.get("mois").asInt(), 1);

            newCarte.setPortefeuille(new Portefeuille(portefeuilleDTO.get()));
            newCarte.setNomCarte(node.get("nomCarte").asText());
            newCarte.setNumCarte(numeroCarte);
            newCarte.setMoisCarte(calendar.getTime());
            newCarte.setCryptogramme(node.get("cvv").asInt());
            optionalCarte = Optional.of(carteService.save(newCarte));
        }

        Depot newDepot = new Depot();
        newDepot.setDateDepot(new Date());
        newDepot.setPortefeuille(new Portefeuille(portefeuilleDTO.get()));
        newDepot.setCarte(new Carte(optionalCarte.get()));
        newDepot.setMontantTotal(node.get("montant").decimalValue());

        return ResponseEntity.ok(depotService.save(newDepot));
    }
}
