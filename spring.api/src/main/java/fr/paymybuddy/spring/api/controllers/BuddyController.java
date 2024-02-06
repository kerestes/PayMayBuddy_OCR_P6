package fr.paymybuddy.spring.api.controllers;

import fr.paymybuddy.spring.api.models.ConnectionBuddy;
import fr.paymybuddy.spring.api.models.DTO.ConnectionBuddyDTO;
import fr.paymybuddy.spring.api.models.DTO.UserDTO;
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
    public ResponseEntity<List<UserDTO>> getBuddyList(HttpServletRequest request){
        String token = jwtTokenService.recoveryToken(request);
        String email = jwtTokenService.getSubjectFromToken(token);
        Long id = userService.findOneByEmail(email).get().getId();
        return ResponseEntity.ok(connectionBuddyService.getBuddyList(id));
    }

    @PostMapping("/trouver-buddy")
    public ResponseEntity<UserDTO> trouverBuddy(@RequestBody User user){
        Optional<UserDTO> userOptional = userService.findOneByEmail(user.getEmail());
        if(userOptional.isPresent()){
            return ResponseEntity.ok(userOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/add-buddy")
    public ResponseEntity<ConnectionBuddyDTO> addBuddy(@RequestBody User user, HttpServletRequest request){
        String token = jwtTokenService.recoveryToken(request);
        String email = jwtTokenService.getSubjectFromToken(token);
        Optional<UserDTO> userOrigineOptional = userService.findOneByEmail(email);
        Optional<UserDTO> userDestinationOptional = userService.findOneByEmail(user.getEmail());
        if(userOrigineOptional.isPresent() && userDestinationOptional.isPresent()){
            if(!connectionBuddyService.verifyConnection(userOrigineOptional.get(), userDestinationOptional.get())){
                ConnectionBuddy connectionBuddy = new ConnectionBuddy();
                connectionBuddy.setUser1(new User(userOrigineOptional.get()));
                connectionBuddy.setUser2(new User(userDestinationOptional.get()));
                return ResponseEntity.ok(connectionBuddyService.save(connectionBuddy));
            }
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.internalServerError().build();
    }
}
