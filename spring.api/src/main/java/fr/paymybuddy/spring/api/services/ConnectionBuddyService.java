package fr.paymybuddy.spring.api.services;

import fr.paymybuddy.spring.api.models.ConnectionBuddy;
import fr.paymybuddy.spring.api.models.User;
import fr.paymybuddy.spring.api.repositories.ConnectionBuddyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConnectionBuddyService {

    @Autowired
    private ConnectionBuddyRepository connectionBuddyRepository;

    public List<User> getBuddyList(Long id){
        List<Object[]> userObjectList = connectionBuddyRepository.getBuddyList(id);
        return userObjectList.stream().map(user -> {
            User newUser = new User();
            newUser.setIdUser((String)user[0]);
            newUser.setPrenom((String)user[1]);
            newUser.setNom((String)user[2]);
            newUser.setEmail((String)user[3]);
            return newUser;
        }).collect(Collectors.toList());
    }

    public ConnectionBuddy save(ConnectionBuddy connectionBuddy){return connectionBuddyRepository.save(connectionBuddy);}

    public Boolean verifyConnection(User user1, User user2){
        Optional<ConnectionBuddy> connectionBuddyOptional = connectionBuddyRepository.findOneByIdUser1andIdUser2(user1.getId(), user2.getId());
        if(connectionBuddyOptional.isPresent())
            return true;
        return false;
    }
}
