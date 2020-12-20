package com.jc.jpathymeleaf.Utils;

public class RunValidation {
    public static boolean validateRun(String run) {
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

    public static boolean onlyNumber(String s) {
        for (int i = 0; i<s.length();i++) {
            if (s.charAt(i) < '0' || s.charAt(i) > '9') return false;
        }
        return true;
    }
}
