package fr.paymybuddy.spring.api.controllers;

import fr.paymybuddy.spring.api.models.DTO.UserDTO;
import fr.paymybuddy.spring.api.models.User;
import fr.paymybuddy.spring.api.services.UserService;
import fr.paymybuddy.spring.api.services.authServices.JwtTokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenService jwtTokenService;

    @GetMapping
    public ResponseEntity<UserDTO> getUser(HttpServletRequest req){
        String token = jwtTokenService.recoveryToken(req);
        String email = jwtTokenService.getSubjectFromToken(token);
        Optional<UserDTO> optionalUserDTO = userService.findOneByEmail(email);
        if(optionalUserDTO.isPresent())
            return ResponseEntity.ok(optionalUserDTO.get());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<UserDTO> updateUser(HttpServletRequest req, @RequestBody User user ){
        String token = jwtTokenService.recoveryToken(req);
        String email = jwtTokenService.getSubjectFromToken(token);
        Optional<User> optionalUser = userService.findOneByEmailComplet(email);
        if (optionalUser.isPresent() && optionalUser.get().getEmail().equals(user.getEmail())){
            User updateUser = optionalUser.get();
            updateUser.setAdresse(user.getAdresse());
            updateUser.setVille(user.getVille());
            updateUser.setCodePostal(user.getCodePostal());
            Optional<UserDTO> optionalUserDTO = userService.save(updateUser);
            if(optionalUserDTO.isPresent())
                return ResponseEntity.ok(optionalUserDTO.get());
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
