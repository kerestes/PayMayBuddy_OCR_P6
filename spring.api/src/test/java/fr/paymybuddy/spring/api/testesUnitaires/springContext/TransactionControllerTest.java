package fr.paymybuddy.spring.api.testesUnitaires.springContext;

import fr.paymybuddy.spring.api.enums.StatusTypeEnum;
import fr.paymybuddy.spring.api.models.DTO.PortefeuilleDTO;
import fr.paymybuddy.spring.api.models.DTO.TransactionBuddysDTO;
import fr.paymybuddy.spring.api.models.DTO.UserDTO;
import fr.paymybuddy.spring.api.models.Portefeuille;
import fr.paymybuddy.spring.api.models.TransactionBuddys;
import fr.paymybuddy.spring.api.models.User;
import fr.paymybuddy.spring.api.services.ConnectionBuddyService;
import fr.paymybuddy.spring.api.services.PortefeuilleService;
import fr.paymybuddy.spring.api.services.TransactionBuddysService;
import fr.paymybuddy.spring.api.services.authServices.JwtTokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters=false)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TransactionBuddysService transactionBuddysService;
    @MockBean
    private PortefeuilleService portefeuilleService;
    @MockBean
    private ConnectionBuddyService connectionBuddyService;
    @MockBean
    private JwtTokenService jwtTokenService;

    @Test
    public void getTransactionTest() throws Exception {
        Mockito.when(jwtTokenService.recoveryToken(any(HttpServletRequest.class))).thenReturn("");
        Mockito.when(jwtTokenService.getSubjectFromToken(anyString())).thenReturn("");
        Mockito.when(transactionBuddysService.getTransactions(anyString())).thenReturn(null);

        mockMvc.perform(get("/transfer-list"))
                .andExpect( status().isOk() );
    }

    @Test
    public void makeTransactionNotAcceptableErrorTest() throws Exception {
        Mockito.when(jwtTokenService.recoveryToken(any(HttpServletRequest.class))).thenReturn("");
        Mockito.when(jwtTokenService.getSubjectFromToken(anyString())).thenReturn("");
        Mockito.when(portefeuilleService.getPortefeuilleByEmail(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(post("/make-transfer").content(makeTransactionString("bruno@gmail.com", "100")).contentType(MediaType.APPLICATION_JSON))
                .andExpect( status().isNotAcceptable() );
    }

    @Test
    public void makeTransactionNotAcceptableErrorByMontantTest() throws Exception {

        PortefeuilleDTO portefeuilleDTO = new PortefeuilleDTO(makePortefeuille(makeUser("Bruno", "Dupont" ), 1L));

        Mockito.when(jwtTokenService.recoveryToken(any(HttpServletRequest.class))).thenReturn("");
        Mockito.when(jwtTokenService.getSubjectFromToken(anyString())).thenReturn("");
        Mockito.when(portefeuilleService.getPortefeuilleByEmail(anyString())).thenReturn(Optional.of(portefeuilleDTO));

        mockMvc.perform(post("/make-transfer").content(makeTransactionString("bruno@gmail.com", "2000")).contentType(MediaType.APPLICATION_JSON))
                .andExpect( status().isNotAcceptable() );
    }

    @Test
    public void makeTransactionNotFoundErrorTest() throws Exception {

        PortefeuilleDTO portefeuilleDTO = new PortefeuilleDTO(makePortefeuille(makeUser("Bruno", "Dupont" ), 1L));

        Mockito.when(jwtTokenService.recoveryToken(any(HttpServletRequest.class))).thenReturn("");
        Mockito.when(jwtTokenService.getSubjectFromToken(anyString())).thenReturn("");
        Mockito.when(portefeuilleService.getPortefeuilleByEmail(anyString())).thenReturn(Optional.of(portefeuilleDTO));
        Mockito.when(connectionBuddyService.verifyConnection(any(UserDTO.class), any(UserDTO.class))).thenReturn(false);

        mockMvc.perform(post("/make-transfer").content(makeTransactionString("bruno@gmail.com", "200")).contentType(MediaType.APPLICATION_JSON))
                .andExpect( status().isNotFound() );
    }

    @Test
    public void makeTransactionTest() throws Exception {

        PortefeuilleDTO portefeuilleDTO = new PortefeuilleDTO(makePortefeuille(makeUser("Alexandre", "Kerestes"), 1L));
        PortefeuilleDTO portefeuilleDTO2 = new PortefeuilleDTO(makePortefeuille(makeUser("Bruno", "Dupont"), 1L));

        TransactionBuddys transactionBuddys = new TransactionBuddys();
        transactionBuddys.setDateTransaction(new Date());
        transactionBuddys.setPortefeuilleOrigine(new Portefeuille(portefeuilleDTO));
        transactionBuddys.setPortefeuilleDestination(new Portefeuille(portefeuilleDTO2));
        transactionBuddys.setMontantTotal(new BigDecimal(200));
        transactionBuddys.setId(1L);

        Mockito.when(jwtTokenService.recoveryToken(any(HttpServletRequest.class))).thenReturn("");
        Mockito.when(jwtTokenService.getSubjectFromToken(anyString())).thenReturn("bruno@gmail.com");
        Mockito.when(portefeuilleService.getPortefeuilleByEmail("bruno@gmail.com")).thenReturn(Optional.of(portefeuilleDTO));
        Mockito.when(portefeuilleService.getPortefeuilleByEmail("alexandre@gmail.com")).thenReturn(Optional.of(portefeuilleDTO2));
        Mockito.when(connectionBuddyService.verifyConnection(any(UserDTO.class), any(UserDTO.class))).thenReturn(true);
        Mockito.when(transactionBuddysService.save(any(TransactionBuddys.class))).thenReturn(new TransactionBuddysDTO(transactionBuddys));

        mockMvc.perform(post("/make-transfer").content(makeTransactionString("alexandre@gmail.com", "200")).contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.montantTotal", is(200)),
                        jsonPath("$.portefeuilleOrigine.user.nom", containsString("Kerestes")),
                        jsonPath("$.portefeuilleDestination.user.nom", containsString("Dupont"))
                );
    }

    private String makeTransactionString(String email, String montant){
        return "{\"email\":\"" + email + "\", " +
                "\"montant\":" + montant + "}";
    }

    private Portefeuille makePortefeuille(User user, Long id){
        Portefeuille portefeuille = new Portefeuille();
        portefeuille.setId(id);
        portefeuille.setUser(user);
        portefeuille.setSolde(new BigDecimal(1000));
        portefeuille.setUpdateDate(new Date());
        return portefeuille;
    }

    private User makeUser(String prenom, String nom){
        User user = new User();
        user.setId(1L);
        user.setEmail(prenom.toLowerCase() + "@gmail.com");
        user.setNom(nom);
        user.setPrenom(prenom);
        user.setPassword("123456789");
        user.setStatus(StatusTypeEnum.ACTIVE);
        user.newIdUser();
        return user;
    }
}
