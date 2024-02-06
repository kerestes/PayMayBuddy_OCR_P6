package fr.paymybuddy.spring.api.controllers;

import fr.paymybuddy.spring.api.models.DTO.PortefeuilleDTO;
import fr.paymybuddy.spring.api.models.Portefeuille;
import fr.paymybuddy.spring.api.models.User;
import fr.paymybuddy.spring.api.services.PortefeuilleService;
import fr.paymybuddy.spring.api.services.authServices.JwtTokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/portefeuille")
public class PortfeuilleController {

    @Autowired
    private PortefeuilleService portefeuilleService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @GetMapping
    public ResponseEntity<PortefeuilleDTO> getPortefeuille(HttpServletRequest req){
        String token = jwtTokenService.recoveryToken(req);
        String email = jwtTokenService.getSubjectFromToken(token);
        Optional<PortefeuilleDTO> portefeuilleDTO = portefeuilleService.getPortefeuilleByEmail(email);
        if(portefeuilleDTO.isPresent())
            return ResponseEntity.ok(portefeuilleDTO.get());
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
