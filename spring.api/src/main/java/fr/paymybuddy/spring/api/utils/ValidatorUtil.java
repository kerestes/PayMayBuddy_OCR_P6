package fr.paymybuddy.spring.api.utils;

import fr.paymybuddy.spring.api.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtil {

    public static List<String> UserValidation(User user){
        List<String> erroList = new ArrayList<>();

        if(user.getNom() == null || user.getNom().isEmpty()){
            erroList.add("noNom");
        }
        if(user.getPrenom() == null || user.getPrenom().isEmpty()) {
            erroList.add("noPrenom");
        }
        if(user.getEmail() == null || user.getEmail().isEmpty()){
            erroList.add("noEmail");
        } else {
            Pattern regexPattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
            Matcher regexMatcher = regexPattern.matcher(user.getEmail());

            if(!regexMatcher.matches()){
                erroList.add("noValideEmail");
            }
        }
        if(user.getPassword() == null || user.getPassword().isEmpty()){
            erroList.add("noPassword");
        }
        if(user.getConfirmPassword() == null || user.getConfirmPassword().isEmpty()){
            erroList.add("noConfirmPassword");
        }
        if(!erroList.contains("noConfirmPassword") && !erroList.contains("noPassword") && !user.getPassword().equals(user.getConfirmPassword())){
            erroList.add("mismatchPassword");
        }

        return erroList;
    }
}
