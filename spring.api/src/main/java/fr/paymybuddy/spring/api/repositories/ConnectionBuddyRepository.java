package fr.paymybuddy.spring.api.repositories;

import fr.paymybuddy.spring.api.models.ConnectionBuddy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface ConnectionBuddyRepository extends JpaRepository<ConnectionBuddy, Long> {

    @Query( value = "select id_user, prenom, nom, email from users where id in (select id_user2 from connection_buddys where id_user1 = :id)", nativeQuery = true)
    public List<Object[]> getBuddyList(Long id);

    @Query("SELECT cb FROM ConnectionBuddy cb WHERE cb.user1.id = :idUser1 and cb.user2.id = :idUser2")
    public Optional<ConnectionBuddy> findOneByIdUser1andIdUser2(Long idUser1, Long idUser2);
}
