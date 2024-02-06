package fr.paymybuddy.spring.api.testesUnitaires.springContext;

import fr.paymybuddy.spring.api.enums.StatusTypeEnum;
import fr.paymybuddy.spring.api.models.DTO.PortefeuilleDTO;
import fr.paymybuddy.spring.api.models.Portefeuille;
import fr.paymybuddy.spring.api.models.User;
import fr.paymybuddy.spring.api.services.PortefeuilleService;
import fr.paymybuddy.spring.api.services.authServices.JwtTokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters=false)
public class PortefeuilleControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PortefeuilleService portefeuilleService;
    @MockBean
    private JwtTokenService jwtTokenService;

    @Test
    public void getPortefeuilleTest() throws Exception {
        Mockito.when(jwtTokenService.recoveryToken(any(HttpServletRequest.class))).thenReturn("");
        Mockito.when(jwtTokenService.getSubjectFromToken(anyString())).thenReturn("");
        Mockito.when(portefeuilleService.getPortefeuilleByEmail(anyString())).thenReturn(Optional.of(new PortefeuilleDTO(makeNewPortefeuille())));

        mockMvc.perform(get("/portefeuille")).andExpectAll(
                status().isOk(),
                jsonPath("$.solde", is(1000)),
                jsonPath("$.user.nom", is("Dupont"))
        );
    }

    @Test
    public void getPortefeuilleNotFoundErrorTest() throws Exception {
        Mockito.when(jwtTokenService.recoveryToken(any(HttpServletRequest.class))).thenReturn("");
        Mockito.when(jwtTokenService.getSubjectFromToken(anyString())).thenReturn("");
        Mockito.when(portefeuilleService.getPortefeuilleByEmail(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(get("/portefeuille")).andExpectAll(
                status().isNotFound()
        );
    }

    private Portefeuille makeNewPortefeuille(){
        User user = new User();
        user.setId(1L);
        user.setIdUser("11112222");
        user.setNom("Dupont");
        user.setPrenom("Robert");
        user.setPassword("123456789");
        user.setStatus(StatusTypeEnum.ACTIVE);
        user.setEmail("robert@gmail.com");

        Portefeuille newPortefeuille = new Portefeuille();
        newPortefeuille.setUser(user);
        newPortefeuille.setUpdateDate(new Date());
        newPortefeuille.setSolde(new BigDecimal(1000));
        newPortefeuille.setId(1L);

        return newPortefeuille;
    }
}
