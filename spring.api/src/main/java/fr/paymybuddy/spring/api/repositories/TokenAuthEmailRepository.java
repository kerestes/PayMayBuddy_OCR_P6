package fr.paymybuddy.spring.api.repositories;

import fr.paymybuddy.spring.api.models.TokenAuthEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface TokenAuthEmailRepository extends JpaRepository<TokenAuthEmail, Long> {

    public Optional<TokenAuthEmail> findByToken(String token);

}
