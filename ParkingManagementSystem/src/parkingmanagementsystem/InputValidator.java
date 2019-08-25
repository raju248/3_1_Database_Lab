/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingmanagementsystem;

import com.jfoenix.controls.JFXTextField;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Tech Land
 */
public class InputValidator {

    public static boolean checkContact(String mobileNo) {

        boolean valid = false;

            String PATTERN = "[0-9]{11}$";    //Writing pattern and array size//
            Pattern patt = Pattern.compile(PATTERN);
            Matcher match = patt.matcher(mobileNo);
            
            if (!match.matches()) {
                valid = false;
            } else {
                valid = true;
            }
        
        return valid;
    }

}
