package fr.paymybuddy.spring.api.models.DTO;

import fr.paymybuddy.spring.api.models.ConnectionBuddy;
import lombok.Getter;

@Getter
public class ConnectionBuddyDTO {

    private Long id;
    private UserDTO user1;
    private UserDTO user2;

    public ConnectionBuddyDTO(ConnectionBuddy connectionBuddy){
        this.id = connectionBuddy.getId();
        this.user1 = new UserDTO(connectionBuddy.getUser1());
        this.user2 = new UserDTO(connectionBuddy.getUser2());
    }
}
