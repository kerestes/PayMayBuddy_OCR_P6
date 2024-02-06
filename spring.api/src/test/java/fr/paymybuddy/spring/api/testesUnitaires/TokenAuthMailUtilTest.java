package fr.paymybuddy.spring.api.testesUnitaires;

import fr.paymybuddy.spring.api.utils.TokenAuthMailUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TokenAuthMailUtilTest {

    @Test
    public void tokenGenerateTest(){
        String token = TokenAuthMailUtil.tokenGenerate(10);
        Assertions.assertTrue(token.length() == 10);
    }
}
