package fr.paymybuddy.spring.api.controllers;

import fr.paymybuddy.spring.api.models.Depot;
import fr.paymybuddy.spring.api.services.DepotService;
import fr.paymybuddy.spring.api.services.authServices.JwtTokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/depot")
public class DepotController {

    @Autowired
    private DepotService depotService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @GetMapping
    public ResponseEntity<List<Depot>> getDepots(HttpServletRequest req){
        String token = jwtTokenService.recoveryToken(req);
        String email = jwtTokenService.getSubjectFromToken(token);
        return ResponseEntity.ok(depotService.getDepots(email));
    }
}
