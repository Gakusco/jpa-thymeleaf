package com.jc.jpathymeleaf.Validations;

import com.jc.jpathymeleaf.Utils.RunValidation;
import com.jc.jpathymeleaf.model.Customer;
import com.jc.jpathymeleaf.model.Staff;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
public class StaffValidation implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Staff.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Staff staff = (Staff) o;

        ValidationUtils.rejectIfEmpty(errors, "run", null, "Debe ingresar su run");

        if(!RunValidation.validateRun(staff.getRun())){
            errors.rejectValue("run", null, "El run no es valido");
        }
    }
}
