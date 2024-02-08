package fr.paymybuddy.spring.api.testesUnitaires.springContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.paymybuddy.spring.api.enums.StatusTypeEnum;
import fr.paymybuddy.spring.api.models.ConnectionBuddy;
import fr.paymybuddy.spring.api.models.DTO.ConnectionBuddyDTO;
import fr.paymybuddy.spring.api.models.DTO.UserDTO;
import fr.paymybuddy.spring.api.models.User;
import fr.paymybuddy.spring.api.services.ConnectionBuddyService;
import fr.paymybuddy.spring.api.services.UserService;
import fr.paymybuddy.spring.api.services.authServices.JwtTokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
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
public class BuddyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ConnectionBuddyService connectionBuddyService;
    @MockBean
    private UserService userService;
    @MockBean
    private JwtTokenService jwtTokenService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static User user;

    @BeforeAll
    public static void setUser(){
        user = new User();
        user.setId(1L);
        user.setIdUser("ABCD12FGHI");
        user.setPrenom("Robert");
        user.setNom("Dupont");
        user.setEmail("robert@email.com");
        user.setPassword("123456789");
        user.setConfirmPassword("123456789");
        user.setStatus(StatusTypeEnum.ACTIVE);
    }

    @Test
    public void getBuddyListTest() throws Exception {

        Mockito.when(jwtTokenService.recoveryToken(any(HttpServletRequest.class))).thenReturn("");
        Mockito.when(jwtTokenService.getSubjectFromToken(anyString())).thenReturn("");
        Mockito.when(userService.findOneByEmail(anyString())).thenReturn(Optional.of(new UserDTO(user)));
        Mockito.when(connectionBuddyService.getBuddyList(1L)).thenReturn(Arrays.asList());

        mockMvc.perform(get("/buddy/buddy-list"))
                .andExpect( status().isOk() );
    }

    @Test
    public void notFoundTrouverBuddyTest() throws Exception {

        Mockito.when(userService.findOneByEmail(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(post("/buddy/trouver-buddy").content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON))
                .andExpect( status().isNotFound() );
    }

    @Test
    public void trouverBuddyTest() throws Exception {

        Mockito.when(userService.findOneByEmailWithOutAdresse(anyString())).thenReturn(Optional.of(new UserDTO(user)));

        mockMvc.perform(post("/buddy/trouver-buddy").content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.nom", containsString("Dupont")),
                        jsonPath("$.prenom", containsString("Robert")),
                        jsonPath("$.password").doesNotExist(),
                        jsonPath("$.adresse").doesNotExist()

                );
    }

    @Test
    public void internalErrorAddBuddyTest() throws Exception {

        Mockito.when(jwtTokenService.recoveryToken(any(HttpServletRequest.class))).thenReturn("");
        Mockito.when(jwtTokenService.getSubjectFromToken(anyString())).thenReturn("");
        Mockito.when(userService.findOneByEmail(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(post("/buddy/add-buddy").content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON))
                .andExpect( status().isInternalServerError() );
    }

    @Test
    public void badRequestErrorAddBuddyTest() throws Exception {

        Mockito.when(jwtTokenService.recoveryToken(any(HttpServletRequest.class))).thenReturn("");
        Mockito.when(jwtTokenService.getSubjectFromToken(anyString())).thenReturn("");
        Mockito.when(userService.findOneByEmail(anyString())).thenReturn(Optional.of(new UserDTO(user)));
        Mockito.when(connectionBuddyService.verifyConnection(any(UserDTO.class), any(UserDTO.class))).thenReturn(true);

        mockMvc.perform(post("/buddy/add-buddy").content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON))
                .andExpect( status().isBadRequest() );
    }

    @Test
    public void addBuddyTest() throws Exception {

        Mockito.when(jwtTokenService.recoveryToken(any(HttpServletRequest.class))).thenReturn("");
        Mockito.when(jwtTokenService.getSubjectFromToken(anyString())).thenReturn("");
        Mockito.when(userService.findOneByEmail(anyString())).thenReturn(Optional.of(new UserDTO(user)));
        Mockito.when(connectionBuddyService.verifyConnection(any(UserDTO.class), any(UserDTO.class))).thenReturn(false);
        Mockito.when(connectionBuddyService.save(any(ConnectionBuddy.class))).thenReturn(new ConnectionBuddyDTO(newConnection()));

        mockMvc.perform(post("/buddy/add-buddy").content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.user1.nom", containsString("Dupont")),
                        jsonPath("$.user2.nom", containsString("Michael")),
                        jsonPath("$.user1.prenom", containsString("Robert")),
                        jsonPath("$.user2.prenom", containsString("Jean")),
                        jsonPath("$.user1.email", containsString("robert@email.com")),
                        jsonPath("$.user2.email", containsString("jean@gmail.com"))
                );
    }

    private ConnectionBuddy newConnection(){
        User user2 = new User();
        user2.setId(2L);
        user2.setEmail("jean@gmail.com");
        user2.setNom("Michael");
        user2.setPrenom("Jean");
        user2.setIdUser("123GHJ78");
        user2.setStatus(StatusTypeEnum.ACTIVE);
        user2.setPassword("ABCDEFGHI");

        ConnectionBuddy connectionBuddy = new ConnectionBuddy();
        connectionBuddy.setUser1(user);
        connectionBuddy.setUser2(user2);
        return connectionBuddy;
    }

}
