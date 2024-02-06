package fr.paymybuddy.spring.api.services;

import fr.paymybuddy.spring.api.enums.StatusTypeEnum;
import fr.paymybuddy.spring.api.exception.InvalidStatusException;
import fr.paymybuddy.spring.api.models.DTO.UserDTO;
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

    public Optional<UserDTO> findOneByEmail(String email){
        Optional<User> userOptional = userRepository.findOneByEmail(email);
        if(userOptional.isPresent())
            return Optional.of(new UserDTO(userOptional.get()));
        return Optional.empty();
    }

    public Optional<User> findOneByEmailComplet(String email){
        Optional<User> userOptional = userRepository.findOneByEmail(email);
        if(userOptional.isPresent())
            return userOptional;
        return Optional.empty();
    }

    public Optional<UserDTO> save(User user){
        Optional<User> userOptional = Optional.of(userRepository.save(user));
        if(userOptional.isPresent())
            return Optional.of(new UserDTO(userOptional.get()));
        return Optional.empty();
    }

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
