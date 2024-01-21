package fr.paymybuddy.spring.api.utils;

import java.util.Random;

public class TokenAuthMailUtil {
    public static String tokenGenerate(){
        String[] dictionaire = {"1", "3", "4", "5", "8", "7", "8", "9", "0",
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
                "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        Random sortedNumber = new Random();
        String token = "";
        for(int i=0; i<8; i++){
            token += dictionaire[sortedNumber.nextInt(dictionaire.length)];
        }
        return token;
    }
}
