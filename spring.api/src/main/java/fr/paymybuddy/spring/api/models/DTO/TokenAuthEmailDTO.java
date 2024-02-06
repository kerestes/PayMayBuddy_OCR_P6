package fr.paymybuddy.spring.api.models.DTO;

import fr.paymybuddy.spring.api.models.TokenAuthEmail;
import lombok.Getter;

import java.util.Date;

@Getter
public class TokenAuthEmailDTO {

    private Long id;
    private UserDTO user;
    private String token;
    private Date updateDate;

    public TokenAuthEmailDTO(TokenAuthEmail tokenAuthEmail){
        this.id = tokenAuthEmail.getId();
        this.user = new UserDTO(tokenAuthEmail.getUser());
        this.token = tokenAuthEmail.getToken();
        this.updateDate = tokenAuthEmail.getUpdateDate();
    }
}
