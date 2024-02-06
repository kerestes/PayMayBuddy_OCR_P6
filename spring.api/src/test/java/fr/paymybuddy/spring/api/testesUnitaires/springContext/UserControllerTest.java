package fr.paymybuddy.spring.api.testesUnitaires.springContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.paymybuddy.spring.api.models.DTO.UserDTO;
import fr.paymybuddy.spring.api.models.User;
import fr.paymybuddy.spring.api.services.UserService;
import fr.paymybuddy.spring.api.services.authServices.JwtTokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters=false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private JwtTokenService jwtTokenService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void getUserNotFoudErrorTest() throws Exception {
        Mockito.when(jwtTokenService.recoveryToken(any(HttpServletRequest.class))).thenReturn("");
        Mockito.when(jwtTokenService.getSubjectFromToken(anyString())).thenReturn("");
        Mockito.when(userService.findOneByEmail(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(get("/user"))
                .andExpect( status().isNotFound() );
    }

    @Test
    public void getUserTest() throws Exception {
        Mockito.when(jwtTokenService.recoveryToken(any(HttpServletRequest.class))).thenReturn("");
        Mockito.when(jwtTokenService.getSubjectFromToken(anyString())).thenReturn("");
        Mockito.when(userService.findOneByEmail(anyString())).thenReturn(Optional.of(new UserDTO( makeUser())));

        mockMvc.perform(get("/user"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.nom", containsString("Dupont"))
                );
    }

    @Test
    public void updateUserBadRequestErrorTest() throws Exception {
        Mockito.when(jwtTokenService.recoveryToken(any(HttpServletRequest.class))).thenReturn("");
        Mockito.when(jwtTokenService.getSubjectFromToken(anyString())).thenReturn("");
        Mockito.when(userService.findOneByEmailComplet(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(post("/user").content(objectMapper.writeValueAsString(makeUser())).contentType(MediaType.APPLICATION_JSON))
                .andExpect( status().isBadRequest() );
    }

    @Test
    public void updateUserInternalServerErrorTest() throws Exception {
        Mockito.when(jwtTokenService.recoveryToken(any(HttpServletRequest.class))).thenReturn("");
        Mockito.when(jwtTokenService.getSubjectFromToken(anyString())).thenReturn("");
        Mockito.when(userService.findOneByEmailComplet(anyString())).thenReturn(Optional.of(makeUser()));
        Mockito.when(userService.save(any(User.class))).thenReturn(Optional.empty());

        mockMvc.perform(post("/user").content(objectMapper.writeValueAsString(makeUser())).contentType(MediaType.APPLICATION_JSON))
                .andExpect( status().isInternalServerError() );
    }

    @Test
    public void updateUserTest() throws Exception {

        User user = makeUser();
        user.setAdresse("Rue Test");
        user.setVille("Test");
        user.setCodePostal("999999");

        Mockito.when(jwtTokenService.recoveryToken(any(HttpServletRequest.class))).thenReturn("");
        Mockito.when(jwtTokenService.getSubjectFromToken(anyString())).thenReturn("");
        Mockito.when(userService.findOneByEmailComplet(anyString())).thenReturn(Optional.of(makeUser()));
        Mockito.when(userService.save(any(User.class))).thenReturn(Optional.of(new UserDTO(user)));

        mockMvc.perform(post("/user").content(objectMapper.writeValueAsString(makeUser())).contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.nom", containsString("Dupont")),
                        jsonPath("$.ville", containsString("Test")),
                        jsonPath("$.codePostal", containsString("99999"))
                );
    }

    private User makeUser(){
        User user = new User();
        user.setId(1L);
        user.setPrenom("Robert");
        user.setNom("Dupont");
        user.setEmail("robert@gmail.com");
        user.newIdUser();
        return user;
    }
}
