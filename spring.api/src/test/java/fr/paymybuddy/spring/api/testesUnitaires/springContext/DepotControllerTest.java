package fr.paymybuddy.spring.api.testesUnitaires.springContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.paymybuddy.spring.api.enums.StatusTypeEnum;
import fr.paymybuddy.spring.api.models.Carte;
import fr.paymybuddy.spring.api.models.DTO.CarteDTO;
import fr.paymybuddy.spring.api.models.DTO.DepotDTO;
import fr.paymybuddy.spring.api.models.DTO.PortefeuilleDTO;
import fr.paymybuddy.spring.api.models.Depot;
import fr.paymybuddy.spring.api.models.Portefeuille;
import fr.paymybuddy.spring.api.models.User;
import fr.paymybuddy.spring.api.services.CarteService;
import fr.paymybuddy.spring.api.services.DepotService;
import fr.paymybuddy.spring.api.services.PortefeuilleService;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters=false)
public class DepotControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DepotService depotService;
    @MockBean
    private CarteService carteService;
    @MockBean
    private PortefeuilleService portefeuilleService;
    @MockBean
    private JwtTokenService jwtTokenService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void getDepotsTest() throws Exception {
        Mockito.when(jwtTokenService.recoveryToken(any(HttpServletRequest.class))).thenReturn("");
        Mockito.when(jwtTokenService.getSubjectFromToken(anyString())).thenReturn("");
        Mockito.when(depotService.getDepots(anyString())).thenReturn(null);

        mockMvc.perform(get("/depot"))
                .andExpect( status().isOk() );

    }

    @Test
    public void makeDepotWithNewCarteTest() throws Exception {
        String depotString = makeNewDepotString(10, 2025, "Alexandre Rodrigues Kerestes", "1234 1234 1234 1234", 456, "100");

        Portefeuille portefeuille = makePortefeuille(makeUser("Rodrigues Kerestes", "Alexandre"));
        Carte carte = makeNewCarte(portefeuille, "1234123412341234");

        Mockito.when(jwtTokenService.recoveryToken(any(HttpServletRequest.class))).thenReturn("");
        Mockito.when(jwtTokenService.getSubjectFromToken(anyString())).thenReturn("");
        Mockito.when(portefeuilleService.getPortefeuilleByEmail(anyString())).thenReturn(Optional.of(new PortefeuilleDTO(portefeuille)));
        Mockito.when(carteService.verifyCartePortefeuille(anyString(), anyString())).thenReturn(Optional.empty());
        Mockito.when(carteService.save(any(Carte.class))).thenReturn(new CarteDTO(carte));
        Mockito.when(depotService.save(any(Depot.class))).thenReturn(new DepotDTO(makeNewDepot(carte)));

        mockMvc.perform(post("/depot/make-depot").content(depotString).contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.montantTotal", is(100)),
                        jsonPath("$.carte.numCarte", is("1234123412341234")),
                        jsonPath("$.carte.nomCarte", is("Alexandre Rodrigues Kerestes"))
                );
    }

    @Test
    public void makeDepotWithOldCarteTest() throws Exception {
        String depotString = makeNewDepotString(10, 2025, "Robert Dupont", "4567 4567 4567 4567", 456, "100");

        Portefeuille portefeuille = makePortefeuille(makeUser("Dupont", "Robert"));
        Carte carte = makeNewCarte(portefeuille, "4567456745674567");

        Mockito.when(jwtTokenService.recoveryToken(any(HttpServletRequest.class))).thenReturn("");
        Mockito.when(jwtTokenService.getSubjectFromToken(anyString())).thenReturn("");
        Mockito.when(portefeuilleService.getPortefeuilleByEmail(anyString())).thenReturn(Optional.of(new PortefeuilleDTO(portefeuille)));
        Mockito.when(carteService.verifyCartePortefeuille(anyString(), anyString())).thenReturn(Optional.of(new CarteDTO(carte)));
        Mockito.when(depotService.save(any(Depot.class))).thenReturn(new DepotDTO(makeNewDepot(carte)));

        mockMvc.perform(post("/depot/make-depot").content(depotString).contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.montantTotal", is(100)),
                        jsonPath("$.carte.numCarte", is("4567456745674567")),
                        jsonPath("$.carte.nomCarte", is("Robert Dupont"))
                );
    }

    private Carte makeNewCarte(Portefeuille portefeuille, String numCarte){
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, 10, 10);

        Carte newCarte = new Carte();
        newCarte.setPortefeuille(portefeuille);
        newCarte.setNomCarte(portefeuille.getUser().getPrenom() + " " + portefeuille.getUser().getNom());
        newCarte.setNumCarte(numCarte);
        newCarte.setMoisCarte(calendar.getTime());
        newCarte.setCryptogramme(456);
        return newCarte;
    }

    private Depot makeNewDepot(Carte carte){
        Depot newDepot = new Depot();
        newDepot.setDateDepot(new Date());
        newDepot.setCarte(carte);
        newDepot.setMontantTotal(new BigDecimal(100));
        newDepot.setPortefeuille(carte.getPortefeuille());
        newDepot.setId(1L);
        return newDepot;
    }

    private String makeNewDepotString(int mois, int annee, String nom, String numero, int cvv, String montant){
        return "{\"annee\":\"" + annee + "\", " +
                "\"mois\":\"" + mois +
                "\", \"numeroCarte\":\"" + numero + "\", " +
                "\"nomCarte\":\"" + nom + "\", " +
                "\"cvv\":\"" + cvv + "\", " +
                "\"montant\":\"" + montant + "\"}";
    }

    private Portefeuille makePortefeuille(User user){
        Portefeuille portefeuille = new Portefeuille();
        portefeuille.setUser(user);
        portefeuille.setUpdateDate(new Date());
        portefeuille.setSolde(new BigDecimal(1000));
        return portefeuille;
    }

    private User makeUser(String nom, String prenom){
        User user = new User();
        user.setStatus(StatusTypeEnum.ACTIVE);
        user.setIdUser("12341234");
        user.setId(1L);
        user.setNom(nom);
        user.setPrenom(prenom);
        user.setEmail(prenom + "@gmail.com");
        user.setPassword("123456789");
        return user;
    }
}
