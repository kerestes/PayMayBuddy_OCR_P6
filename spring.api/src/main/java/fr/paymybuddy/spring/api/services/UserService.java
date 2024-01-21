package fr.paymybuddy.spring.api.services;

import fr.paymybuddy.spring.api.enums.StatusTypeEnum;
import fr.paymybuddy.spring.api.exception.InvalidStatusException;
import fr.paymybuddy.spring.api.models.User;
import fr.paymybuddy.spring.api.models.security.UserDetailsImpl;
import fr.paymybuddy.spring.api.repositories.UserRepository;
import fr.paymybuddy.spring.api.services.authServices.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenService jwtTokenService;

    public Optional<User> findOneByEmail(String email){return userRepository.findOneByEmail(email);}

    public User save(User user){return userRepository.save(user);}

    public String authenticateUser(User user){

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        }catch(BadCredentialsException e){
            //System.out.println(e.getStackTrace());
            throw new BadCredentialsException("Incorrect username or Password");
        }

        Optional<User> userOptional = userRepository.findOneByEmail(user.getEmail());
        if(userOptional.isPresent() && userOptional.get().getStatus() == StatusTypeEnum.ACTIVE ){
            UserDetailsImpl userDetails = new UserDetailsImpl(userOptional.get());
            return jwtTokenService.generateToken(userDetails);
        }else{
            throw new InvalidStatusException("User email not confirmed");
        }
    }

}
