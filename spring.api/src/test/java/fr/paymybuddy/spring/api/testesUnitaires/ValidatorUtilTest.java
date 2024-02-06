package fr.paymybuddy.spring.api.testesUnitaires;

import fr.paymybuddy.spring.api.models.User;
import fr.paymybuddy.spring.api.utils.ValidatorUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

public class ValidatorUtilTest {

    private User user = new User();

    @BeforeEach
    public void setUser(){
        user.setId(1L);
        user.setIdUser("ABC123ZER7");
        user.setNom("Kerestes");
        user.setPrenom("Alexandre");
        user.setEmail("alexandre@email.com");
        user.setPassword("ABCdef123.!-");
        user.setConfirmPassword("ABCdef123.!-");
    }

    @Test
    public void goodUserTest(){
        List<String> listErrors = ValidatorUtil.UserValidation(user);

        Assertions.assertTrue(listErrors.isEmpty());
    }

    @Test
    public void noLastNameErrorTest(){
        user.setNom(null);
        List<String> listErrors = ValidatorUtil.UserValidation(user);
        Assertions.assertTrue(listErrors.get(0).equals("noNom"));

        user.setNom("");
        listErrors = ValidatorUtil.UserValidation(user);
        Assertions.assertTrue(listErrors.get(0).equals("noNom"));
    }

    @Test
    public void noFirstNameTest(){
        user.setPrenom(null);
        List<String> listErrors = ValidatorUtil.UserValidation(user);
        Assertions.assertTrue(listErrors.get(0).equals("noPrenom"));

        user.setPrenom("");
        listErrors = ValidatorUtil.UserValidation(user);
        Assertions.assertTrue(listErrors.get(0).equals("noPrenom"));
    }

    @Test
    public void noEmailTest(){
        user.setEmail(null);
        List<String> listErrors = ValidatorUtil.UserValidation(user);
        Assertions.assertTrue(listErrors.get(0).equals("noEmail"));

        user.setEmail("");
        listErrors = ValidatorUtil.UserValidation(user);
        Assertions.assertTrue(listErrors.get(0).equals("noEmail"));
    }

    @Test
    public void noPasswordTest(){
        user.setPassword(null);
        List<String> listErrors = ValidatorUtil.UserValidation(user);
        Assertions.assertTrue(listErrors.get(0).equals("noPassword"));

        user.setPassword("");
        listErrors = ValidatorUtil.UserValidation(user);
        Assertions.assertTrue(listErrors.get(0).equals("noPassword"));
    }

    @Test
    public void noConfirmedPasswordTest(){
        user.setConfirmPassword(null);
        List<String> listErrors = ValidatorUtil.UserValidation(user);
        Assertions.assertTrue(listErrors.get(0).equals("noConfirmPassword"));

        user.setConfirmPassword("");
        listErrors = ValidatorUtil.UserValidation(user);
        Assertions.assertTrue(listErrors.get(0).equals("noConfirmPassword"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"ale@sdf.sdf", "ale@sdf.sdf.fr", "ale123@sdf.sdf", "123ale@sdf.sdf", "ale_@sdf.sdf", "1-ale@sdf.sdf", "a.l-e-22@sdf.sdf"})
    public void correctEmailTest(String email){
        user.setEmail(email);

        List<String> listErrors = ValidatorUtil.UserValidation(user);
        Assertions.assertTrue(listErrors.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"ale@sdf", "ale@sdf.s", "123@ale@sdf.sdf", "1-alesdf.sdf"})
    public void incorrectEmailTest(String email){
        user.setEmail(email);

        List<String> listErrors = ValidatorUtil.UserValidation(user);
        Assertions.assertTrue(listErrors.get(0).equals("noValideEmail"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"ABCdef123.!- ", " ABCdef123.!-", "abcdef123.!-", "ABCDEF123.!-", "ABCdef123!-"})
    public void noMatchPasswordTest(String confirmePassword){
        user.setConfirmPassword(confirmePassword);

        List<String> listErrors = ValidatorUtil.UserValidation(user);
        Assertions.assertTrue(listErrors.get(0).equals("mismatchPassword"));
    }
}
