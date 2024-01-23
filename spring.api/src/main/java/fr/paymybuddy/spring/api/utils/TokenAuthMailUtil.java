package fr.paymybuddy.spring.api.utils;

import java.util.Random;

public class TokenAuthMailUtil {

    private final static String[] dictionaire = {"1", "3", "4", "5", "8", "7", "8", "9", "0",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public static String tokenGenerate(int taille){
        Random sortedNumber = new Random();
        String token = "";
        for(int i=0; i<taille; i++){
            token += dictionaire[sortedNumber.nextInt(dictionaire.length)];
        }
        return token;
    }
}
