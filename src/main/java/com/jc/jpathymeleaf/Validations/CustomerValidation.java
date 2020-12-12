package com.jc.jpathymeleaf.Validations;


import com.jc.jpathymeleaf.model.Customer;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class CustomerValidation implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Customer.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Customer customer = (Customer) o;

        ValidationUtils.rejectIfEmpty(errors, "run", null, "Debe ingresar su run");

        if(!validateRun(customer.getRun())){
            errors.rejectValue("run", null, "El run no es valido");
        }
    }

    private boolean validateRun(String run) {
        if (run.contains(".")){
            return false;
        } else {
            if (!run.contains("-")){
                return false;
            }
        }
        String[] numberDigit = run.split("-");
        if (numberDigit.length == 2){
            if (numberDigit[0].length() >= 7 &&
                    numberDigit[0].length() <= 8 &&
                    numberDigit[1].length() == 1) {
                if (!onlyNumber(numberDigit[0])) return false;
                int suma = 0;
                int mult = 2;
                int result;
                for (int i = numberDigit[0].length() - 1; i>=0; i--) {
                    result = Integer.parseInt(String.valueOf(numberDigit[0].charAt(i))) * mult;
                    suma = suma + result;
                    mult = mult + 1;
                    if (mult == 8) mult = 2;
                }
                int moduloOnce = 11 - (suma % 11);
                char dv;
                if (moduloOnce == 10) {
                    dv = 'k';
                } else {
                    if (moduloOnce == 11) {
                        dv = '0';
                    } else {
                        dv = Character.forDigit(moduloOnce, 10);
                    }
                }
                char dvInput = Character.toLowerCase(numberDigit[1].charAt(0));
                if (dv == dvInput) return true;
            } else {
                return false;
            }
        }
        return false;
    }

    private boolean onlyNumber(String s) {
        for (int i = 0; i<s.length();i++) {
            if (s.charAt(i) < '0' || s.charAt(i) > '9') return false;
        }
        return true;
    }


}
