package com.jc.jpathymeleaf.Validations;


import com.jc.jpathymeleaf.model.Customer;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

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
            errors.rejectValue("run", null, "El run no tiene un formato correcto, Ej: 19.414.979-4");
        }
    }

    private boolean validateRun(String run) {
        return true;
//        return validarNumerosYSimbolos(run) &&  validarFormatoRut(run);
    }

    public boolean validarNumerosYSimbolos(String rut) {
        if (rut.length()>12 || rut.length()<10 ) {
            return false;
        }
        int cantidadPuntos = 0;
        int cantidadGuion = 0;
        char arreglo[] = rut.toCharArray();
        int ascii;
        for (int i = 0;i<rut.length(); i++) {
            ascii = (int)arreglo[i];
            if ( esNumeroASCII(ascii) || ascii == 107 || ascii == 46 || ascii == 45) {
                // Si el char es un numero, un '.', un '-' o una 'k', entonces es correcto
                // 48 = '0', 57 = '9', 107 = 'k', 46 = '.', 45 = '-'
                if ( ascii == 46 ) {
                    cantidadPuntos++;
                }
                if ( ascii == 45 ) {
                    cantidadGuion++;
                }
            } else {
                return false;
            }
        }
        if ( cantidadPuntos != 2 || cantidadGuion != 1) {
            return false;
        }
        return true;
    }

    public boolean validarFormatoRut(String rut) {
        //En este punto el rut ya debe tener dos '.' y un '-' y tener solo numeros y una k en el digito verificador
        String[] arrRut = rut.split("\\.");
        char[] numero = arrRut[0].toCharArray();
        if ( numero.length > 2 || numero.length < 1) {
            return false;
        } else {
            if (!soloNumero(numero)) return false;
        }
        numero = arrRut[1].toCharArray();
        if ( numero.length != 3) {
            return false;
        } else {
            if (!soloNumero(numero)) return false;
        }
        String[] numYDigito = arrRut[2].split("-");
        if (numYDigito.length != 2 ) {
            return false;
        } else {
            numero = numYDigito[0].toCharArray();
            if ( numero.length != 3) {
                return false;
            } else {
                if (!soloNumero(numero)) return false;
            }
            numero = numYDigito[1].toCharArray();
            if ( numero.length > 1 ) {
                return false;
            } else {
                if (soloNumero(numero) || numero[0] == 'k') {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean esNumeroASCII(int ascii) {
        if ( (ascii >= 48 && ascii<=57) ) {
            return true;
        } else {
            return false;
        }
    }

    public boolean soloNumero(char numero[]) {
        int ascii;
        for (int i = 0; i<numero.length; i++ ) {
            ascii = (int) numero[i];
            if ( !esNumeroASCII(ascii) ) {
                return false;
            }
        }
        return true;
    }
}
