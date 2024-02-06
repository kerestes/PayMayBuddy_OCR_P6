package fr.paymybuddy.spring.api.testesUnitaires.springContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.paymybuddy.spring.api.enums.StatusTypeEnum;
import fr.paymybuddy.spring.api.models.DTO.IbanDTO;
import fr.paymybuddy.spring.api.models.DTO.PortefeuilleDTO;
import fr.paymybuddy.spring.api.models.DTO.RetraitDTO;
import fr.paymybuddy.spring.api.models.Iban;
import fr.paymybuddy.spring.api.models.Portefeuille;
import fr.paymybuddy.spring.api.models.Retrait;
import fr.paymybuddy.spring.api.models.User;
import fr.paymybuddy.spring.api.services.IbanService;
import fr.paymybuddy.spring.api.services.PortefeuilleService;
import fr.paymybuddy.spring.api.services.RetraitService;
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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters=false)
public class RetraitControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RetraitService retraitService;
    @MockBean
    private PortefeuilleService portefeuilleService;
    @MockBean
    private IbanService ibanService;
    @MockBean
    private JwtTokenService jwtTokenService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void getRetraitsTest() throws Exception {
        Mockito.when(jwtTokenService.recoveryToken(any(HttpServletRequest.class))).thenReturn("");
        Mockito.when(jwtTokenService.getSubjectFromToken(anyString())).thenReturn("");
        Mockito.when(retraitService.getRetraits(anyString())).thenReturn(Arrays.asList());

        mockMvc.perform(get("/retrait")).andExpect(
                status().isOk()
        );
    }

    @Test
    public void makeRetraitNotAcceptableErrorTest() throws Exception {


        Mockito.when(jwtTokenService.recoveryToken(any(HttpServletRequest.class))).thenReturn("");
        Mockito.when(jwtTokenService.getSubjectFromToken(anyString())).thenReturn("");
        Mockito.when(portefeuilleService.getPortefeuilleByEmail(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(post("/retrait/make-retrait").content(makeRetraitString("FR761234123412341234", "FRPPGGXX", "100")).contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                status().isNotAcceptable()
        );
    }

    @Test
    public void makeRetraitNotAcceptableErrorByMontantTest() throws Exception {

        PortefeuilleDTO portefeuilleDTO = new PortefeuilleDTO(makePortefeuille(makeUser()));

        Mockito.when(jwtTokenService.recoveryToken(any(HttpServletRequest.class))).thenReturn("");
        Mockito.when(jwtTokenService.getSubjectFromToken(anyString())).thenReturn("");
        Mockito.when(portefeuilleService.getPortefeuilleByEmail(anyString())).thenReturn(Optional.of(portefeuilleDTO));

        mockMvc.perform(post("/retrait/make-retrait").content(makeRetraitString("FR761234123412341234", "FRPPGGXX", "2000")).contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        status().isNotAcceptable()
                );
    }

    @Test
    public void makeRetraitTest() throws Exception {

        PortefeuilleDTO portefeuilleDTO = new PortefeuilleDTO(makePortefeuille(makeUser()));
        IbanDTO ibanDTO = new IbanDTO(makeIban(new Portefeuille(portefeuilleDTO)));
        RetraitDTO retraitDTO = new RetraitDTO(makeRetrait(new Portefeuille(portefeuilleDTO), new Iban(ibanDTO)));

        Mockito.when(jwtTokenService.recoveryToken(any(HttpServletRequest.class))).thenReturn("");
        Mockito.when(jwtTokenService.getSubjectFromToken(anyString())).thenReturn("");
        Mockito.when(portefeuilleService.getPortefeuilleByEmail(anyString())).thenReturn(Optional.of(portefeuilleDTO));
        Mockito.when(ibanService.findByCompteAdresse(anyString(), anyString(), anyString(), anyString(), anyString(), anyLong())).thenReturn(Optional.of(ibanDTO));
        Mockito.when(retraitService.save(any(Retrait.class))).thenReturn(retraitDTO);

        mockMvc.perform(post("/retrait/make-retrait").content(makeRetraitString("FR761234123412341234", "FRPPGGXX", "500")).contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.montantTotal", is(500))
                );
    }

    private String makeRetraitString(String iban, String bic,  String montant){
        return "{\"iban\":\"" + iban + "\"," +
                "\"bic\":\"" + bic + "\"," +
                "\"montant\":" + montant + "}";
    }

    private Retrait makeRetrait(Portefeuille portefeuille, Iban iban){
        Retrait retrait = new Retrait();
        retrait.setDateRetrait(new Date());
        retrait.setMontantTotal(new BigDecimal(500));
        retrait.setPortefeuille(portefeuille);
        retrait.setIban(iban);
        return retrait;
    }

    private Portefeuille makePortefeuille(User user){
        Portefeuille portefeuille = new Portefeuille();
        portefeuille.setId(1L);
        portefeuille.setUser(user);
        portefeuille.setSolde(new BigDecimal(1000));
        portefeuille.setUpdateDate(new Date());
        return portefeuille;
    }

    private User makeUser(){
        User user = new User();
        user.setId(1L);
        user.setEmail("robert@gmail.com");
        user.setNom("Dupont");
        user.setPrenom("Robert");
        user.setPassword("123456789");
        user.setStatus(StatusTypeEnum.ACTIVE);
        user.newIdUser();
        return user;
    }

    private Iban makeIban(Portefeuille portefeuille){
        Iban iban = new Iban();
        iban.setId(1L);
        iban.setPortefeuille(portefeuille);
        iban.setCodeBanque("3535");
        iban.setCodeGuichet("12345");
        iban.setNumeroCompte("4568793213");
        iban.setCleRib("56");
        iban.setIban("FR76");
        iban.setBic("FRPPGGXX");
        return iban;
    }
}
