package com.maktabsharif.validation;

import java.util.regex.Pattern;

public class Validators {

    //validating national code
    public boolean validateNationalCOde(String nationalCOde) {
        if(Pattern.compile("\\d{10}").matcher(nationalCOde).matches()){
            int sum = 0;
            for (int i=10, j=0; i>1; --i)
                sum += Integer.parseInt(String.valueOf(nationalCOde.charAt(j++)))*i;
            if((sum%11<3 && Integer.parseInt(String.valueOf(nationalCOde.charAt(9))) == sum%11) ||
                    (sum%11>=3 && Integer.parseInt(String.valueOf(nationalCOde.charAt(9))) == 11-sum%11) )
                return true;
            else{
                System.out.println("Invalid national code.");
                return false;
            }
        }
        else
            System.out.println("Invalid national code. National code is a 10-digit number.");
        return false;
    }


    //validating number
    public boolean validateNumber(String meNumber){
        if(Pattern.compile("\\d{1}").matcher(meNumber).matches())
            return true;
        else
            return false;
    }

    //validating number
    public boolean validateEmail(String email){
        if(Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$").matcher(email).matches())
            return true;
        else{
            System.out.println("Invalid Email address.");
            return false;
        }

    }

    //validating password
    public boolean validatePassword(String password){
        if(Pattern.compile("^[A-Za-z0-9]{8,}").matcher(password).matches())
            return true;
        else
            return false;
    }

}
