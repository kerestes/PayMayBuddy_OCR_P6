package fr.paymybuddy.spring.api.services.authServices;

import fr.paymybuddy.spring.api.models.TokenAuthEmail;
import fr.paymybuddy.spring.api.repositories.TokenAuthEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TokenAuthEmailService {

    @Autowired
    private TokenAuthEmailRepository tokenAuthEmailRepository;

    public Optional<TokenAuthEmail> findOneByToken(String token){return tokenAuthEmailRepository.findByToken(token);}

    public Optional<TokenAuthEmail> save(TokenAuthEmail tokenAuthEmail){return Optional.ofNullable(tokenAuthEmailRepository.save(tokenAuthEmail));}
}
