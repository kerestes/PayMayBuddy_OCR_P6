package fr.paymybuddy.spring.api.utils;

import fr.paymybuddy.spring.api.models.TokenAuthEmail;
import fr.paymybuddy.spring.api.models.User;
import fr.paymybuddy.spring.api.services.TokenAuthEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.mail.javamail.*;

import java.util.Date;
import java.util.Optional;

@Component
@Setter
public class EmailRegistrationUtil implements Runnable{

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TokenAuthEmailService tokenAuthEmailService;

    private String MessageStandard = "<h2>Soyez le Bienvenue au Pay My Buddy</h2> " +
            "<p>Pour confirmer votre inscription veuillez <a href=\"http://localhost:4200/home?token=NEW_TOKEN\">cliquer Ici</a></p>";
    private User user;

    public void sendEmailRegistration(User user){
        String token = tokenPersist(user);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

            message.setContent(MessageStandard.replace("NEW_TOKEN", token), "text/html");
            helper.setTo(user.getEmail());
            helper.setSubject("Bienvenu au Pay My Buddy");
            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String tokenPersist(User user){
        String token = "";
        Boolean tokenVerified = false;

        while (!tokenVerified){
            token = TokenAuthMailUtil.tokenGenerate(8);
            Optional<TokenAuthEmail> tokenAuthEmail = tokenAuthEmailService.findOneByToken(token);
            if(tokenAuthEmail.isEmpty())
                tokenVerified = true;
        }

        TokenAuthEmail tokenAuthEmail = new TokenAuthEmail();
        tokenAuthEmail.setUser(user);
        tokenAuthEmail.setToken(token);
        tokenAuthEmail.setUpdateDate(new Date());
        tokenAuthEmailService.save(tokenAuthEmail);
        return token;
    }

    @Override
    public void run() {
        sendEmailRegistration(this.user);
    }
}
