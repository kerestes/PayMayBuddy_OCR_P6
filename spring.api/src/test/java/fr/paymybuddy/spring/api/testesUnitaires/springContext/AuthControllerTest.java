package fr.paymybuddy.spring.api.testesUnitaires.springContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.paymybuddy.spring.api.enums.StatusTypeEnum;
import fr.paymybuddy.spring.api.exception.InvalidStatusException;
import fr.paymybuddy.spring.api.models.DTO.UserDTO;
import fr.paymybuddy.spring.api.models.Portefeuille;
import fr.paymybuddy.spring.api.models.TokenAuthEmail;
import fr.paymybuddy.spring.api.models.User;
import fr.paymybuddy.spring.api.services.PortefeuilleService;
import fr.paymybuddy.spring.api.services.TokenAuthEmailService;
import fr.paymybuddy.spring.api.services.UserService;
import fr.paymybuddy.spring.api.utils.EmailRegistrationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;

import javax.sound.sampled.Port;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private EmailRegistrationUtil emailRegistrationUtil;
    @MockBean
    private TokenAuthEmailService tokenAuthEmailService;
    @MockBean
    private PortefeuilleService portefeuilleService;

    ObjectMapper objectMapper = new ObjectMapper();

    private User user = new User();

    @BeforeEach
    public void setUser(){
        user.setPrenom("Robert");
        user.setNom("Dupont");
        user.setEmail("robert@email.com");
        user.setPassword("123456789");
        user.setConfirmPassword("123456789");
    }

    //Registration Tests

    @Test
    public void registrationFormErrorTest() throws Exception {

        user.setConfirmPassword("123123123");

        mockMvc.perform(post("/registration").content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                status().isOk(),
                jsonPath("$[0]", containsString("mismatchPassword"))
        );
    }

    @Test
    public void registrationUserExistsErrorTest() throws Exception {

        Mockito.when(userService.findOneByEmail(anyString())).thenReturn(Optional.of(new UserDTO(user)));

        mockMvc.perform(post("/registration").content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isUnprocessableEntity()
                );
    }

    @Test
    public void registrationSuccessTest() throws Exception {

        Mockito.when(userService.findOneByEmail(anyString())).thenReturn(Optional.empty());
        Mockito.when(userService.save(any(User.class))).thenReturn(Optional.of(new UserDTO(user)));
        Mockito.doNothing().when(emailRegistrationUtil).run();

        mockMvc.perform(post("/registration").content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$[0]", containsString("success"))
                );
    }

    //Confirm Registration Test

    @Test
    public void confirmRegistrationNoTokenAuthFindTest() throws Exception {
        Mockito.when(tokenAuthEmailService.findOneByToken(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(get("/registration/confirm?token=ABCDEFGH"))
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    public void confirmRegistrationSuccessTest() throws Exception {
        user.setStatus(StatusTypeEnum.CONFIRME_AUTH);
        TokenAuthEmail tokenAuth = new TokenAuthEmail();
        Portefeuille portefeuille = new Portefeuille();
        tokenAuth.setUser(user);

        Mockito.when(tokenAuthEmailService.findOneByToken(anyString())).thenReturn(Optional.of(tokenAuth));
        Mockito.when(userService.save(any(User.class))).thenReturn(Optional.of(new UserDTO(user)));
        Mockito.when(portefeuilleService.save(any(Portefeuille.class))).thenReturn(portefeuille);

        mockMvc.perform(get("/registration/confirm?token=ABCDEFGH"))
                .andExpectAll(
                        status().isOk()
                );
    }

    //Login Tests

    @Test
    public void badCredentialsLoginTest() throws Exception {
        Mockito.when(userService.authenticateUser(any(User.class))).thenThrow(BadCredentialsException.class);

        mockMvc.perform(post("/login").content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    public void invalidStatusLoginTest() throws Exception {
        Mockito.when(userService.authenticateUser(any(User.class))).thenThrow(InvalidStatusException.class);

        mockMvc.perform(post("/login").content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isForbidden()
                );
    }

    @Test
    public void loginSuccessTest() throws Exception {
        Mockito.when(userService.authenticateUser(any(User.class))).thenReturn("ABCDEFGH123123123");

        mockMvc.perform(post("/login").content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$", containsString("ABCDEFGH123123123"))
                );
    }
}
