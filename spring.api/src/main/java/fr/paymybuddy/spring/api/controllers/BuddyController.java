package fr.paymybuddy.spring.api.controllers;

import fr.paymybuddy.spring.api.models.ConnectionBuddy;
import fr.paymybuddy.spring.api.models.User;
import fr.paymybuddy.spring.api.services.ConnectionBuddyService;
import fr.paymybuddy.spring.api.services.UserService;
import fr.paymybuddy.spring.api.services.authServices.JwtTokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/buddy")
public class BuddyController {

    @Autowired
    private ConnectionBuddyService connectionBuddyService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenService jwtTokenService;

    @GetMapping("/buddy-list")
    public ResponseEntity<List<User>> getBuddyList(HttpServletRequest request){
        String token = jwtTokenService.recoveryToken(request);
        String email = jwtTokenService.getSubjectFromToken(token);
        Long id = userService.findOneByEmail(email).get().getId();
        System.out.println(id);
        return ResponseEntity.ok(connectionBuddyService.getBuddyList(id));
    }

    @PostMapping("/trouver-buddy")
    public ResponseEntity<User> trouverBuddy(@RequestBody User user){
        Optional<User> userOptional = userService.findOneByEmail(user.getEmail());
        if(userOptional.isPresent()){
            User newUser = userOptional.get();
            newUser.setStatus(null);
            newUser.setPassword(null);
            newUser.setAdresse(null);
            newUser.setConfirmPassword(null);
            return ResponseEntity.ok(newUser);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/add-buddy")
    public ResponseEntity<ConnectionBuddy> addBuddy(@RequestBody User user, HttpServletRequest request){
        String token = jwtTokenService.recoveryToken(request);
        String email = jwtTokenService.getSubjectFromToken(token);
        Optional<User> userOrigineOptional = userService.findOneByEmail(email);
        Optional<User> userDestinationOptional = userService.findOneByEmail(user.getEmail());
        if(userOrigineOptional.isPresent() && userDestinationOptional.isPresent()){
            if(!connectionBuddyService.verifyConnection(userOrigineOptional.get(), userDestinationOptional.get())){
                ConnectionBuddy connectionBuddy = new ConnectionBuddy();
                connectionBuddy.setUser1(userOrigineOptional.get());
                connectionBuddy.setUser2(userDestinationOptional.get());
                return ResponseEntity.ok(connectionBuddyService.save(connectionBuddy));
            }
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.internalServerError().build();
    }
}
