package fr.paymybuddy.spring.api.controllers;

import fr.paymybuddy.spring.api.models.TransactionBuddys;
import fr.paymybuddy.spring.api.services.TransactionBuddysService;
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
@RequestMapping("/transfer-list")
public class TransactionController {

    @Autowired
    private TransactionBuddysService transactionBuddysService;
    @Autowired
    private JwtTokenService jwtTokenService;

    @GetMapping
    public ResponseEntity<List<TransactionBuddys>> getTransaction(HttpServletRequest req){
        String token = jwtTokenService.recoveryToken(req);
        String email = jwtTokenService.getSubjectFromToken(token);
        return ResponseEntity.ok(transactionBuddysService.getTransactions(email));
    }
}
