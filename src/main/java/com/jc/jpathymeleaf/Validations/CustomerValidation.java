package com.jc.jpathymeleaf.Validations;


import com.jc.jpathymeleaf.Utils.RunValidation;
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

        if(!RunValidation.validateRun(customer.getRun())){
            errors.rejectValue("run", null, "El run no es valido");
        }
    }


}
