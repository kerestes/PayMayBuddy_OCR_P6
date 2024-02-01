package fr.paymybuddy.spring.api.controllers;

import fr.paymybuddy.spring.api.models.User;
import fr.paymybuddy.spring.api.services.UserService;
import fr.paymybuddy.spring.api.services.authServices.JwtTokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenService jwtTokenService;

    @GetMapping
    public ResponseEntity<User> getUser(HttpServletRequest req){
        String token = jwtTokenService.recoveryToken(req);
        String email = jwtTokenService.getSubjectFromToken(token);
        User user = userService.findOneByEmail(email).get();
        user.setPassword(null);
        user.setConfirmPassword(null);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> saveUser(HttpServletRequest req, @RequestBody User user ){
        String token = jwtTokenService.recoveryToken(req);
        String email = jwtTokenService.getSubjectFromToken(token);
        User findUser = userService.findOneByEmail(email).get();
        if (findUser.getEmail().equals(user.getEmail())){
            findUser.setAdresse(user.getAdresse());
            findUser.setVille(user.getVille());
            findUser.setCodePostal(user.getCodePostal());
            user = userService.save(findUser);
            user.setPassword(null);
            user.setConfirmPassword(null);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().build();
    }
}
