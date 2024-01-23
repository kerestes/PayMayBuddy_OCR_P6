package fr.paymybuddy.spring.api.controllers;

import fr.paymybuddy.spring.api.models.User;
import fr.paymybuddy.spring.api.services.ConnectionBuddyService;
import fr.paymybuddy.spring.api.services.UserService;
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
@RequestMapping("/buddy-list")
public class BuddyController {

    @Autowired
    private ConnectionBuddyService connectionBuddyService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenService jwtTokenService;

    @GetMapping
    public ResponseEntity<List<User>> getBuddyList(HttpServletRequest request){
        String token = jwtTokenService.recoveryToken(request);
        String email = jwtTokenService.getSubjectFromToken(token);
        Long id = userService.findOneByEmail(email).get().getId();
        System.out.println(id);
        return ResponseEntity.ok(connectionBuddyService.getBuddyList(id));
    }
}
