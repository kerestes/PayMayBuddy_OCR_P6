package fr.paymybuddy.spring.api.controllers;

import fr.paymybuddy.spring.api.models.Retrait;
import fr.paymybuddy.spring.api.services.RetraitService;
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
@RequestMapping("/retrait")
public class RetraitController {

    @Autowired
    private RetraitService retraitService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @GetMapping
    public ResponseEntity<List<Retrait>> getRetraits(HttpServletRequest request){
        String token = jwtTokenService.recoveryToken(request);
        String email = jwtTokenService.getSubjectFromToken(token);
        return ResponseEntity.ok(retraitService.getRetraits(email));
    }
}
