package fr.paymybuddy.spring.api.controllers;

import fr.paymybuddy.spring.api.enums.StatusTypeEnum;
import fr.paymybuddy.spring.api.exception.InvalidStatusException;
import fr.paymybuddy.spring.api.models.Portefeuille;
import fr.paymybuddy.spring.api.models.TokenAuthEmail;
import fr.paymybuddy.spring.api.models.User;
import fr.paymybuddy.spring.api.services.PortefeuilleService;
import fr.paymybuddy.spring.api.services.TokenAuthEmailService;
import fr.paymybuddy.spring.api.services.UserService;
import fr.paymybuddy.spring.api.utils.EmailRegistrationUtil;
import fr.paymybuddy.spring.api.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private PortefeuilleService portefeuilleService;
    @Autowired
    private EmailRegistrationUtil emailRegistrationUtil;
    @Autowired
    private TokenAuthEmailService tokenAuthEmailService;

    @PostMapping("/registration")
    public List<String> registration(@RequestBody User user){
        List<String> errorReturn = ValidatorUtil.UserValidation(user);
        if(errorReturn.isEmpty()){
            if(userService.findOneByEmail(user.getEmail()).isEmpty()){
                user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
                user.setStatus(StatusTypeEnum.CONFIRME_AUTH);
                user.newIdUser();
                User newUser = userService.save(user);
                if(newUser != null){
                    emailRegistrationUtil.setUser(newUser);
                    emailRegistrationUtil.run();
                    return Arrays.asList("success");
                } else {
                    //internal error
                }
            } else {
                return Arrays.asList("UserExists");
            }
        }
        return errorReturn;
    }

    @GetMapping("/registration/confirm")
    public Boolean confirmRegistration(@RequestParam String token){
        Optional<TokenAuthEmail> tokenAuthEmailOptional = tokenAuthEmailService.findOneByToken(token);
        if (tokenAuthEmailOptional.isPresent()){
            User user = tokenAuthEmailOptional.get().getUser();
            user.setStatus(StatusTypeEnum.ACTIVE);
            Portefeuille portefeuille = new Portefeuille();
            portefeuille.setUser(user);
            portefeuille.setSolde(new BigDecimal(0));
            portefeuille.setUpdateDate(new Date());
            userService.save(user);
            portefeuilleService.save(portefeuille);
        } else {
            System.out.println("erro: TokenAuthEmail nao esta registrado");
            return false;
        }
        return true;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){
        try{
            String token = userService.authenticateUser(user);
            return ResponseEntity.ok().body(token);
        }catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        }catch(InvalidStatusException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("");
        }
    }
}
