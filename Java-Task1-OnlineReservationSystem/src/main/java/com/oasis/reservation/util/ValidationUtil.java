package com.oasis.reservation.util;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class ValidationUtil {
    private ValidationUtil(){}
    public static LocalDate parseFutureDate(String value){
        try {
            LocalDate d=LocalDate.parse(value.trim(),DateTimeFormatter.ISO_LOCAL_DATE);
            if(!d.isAfter(LocalDate.now())) throw new IllegalArgumentException("Journey date must be a future date.");
            return d;
        } catch(DateTimeParseException e){ throw new IllegalArgumentException("Use a valid date in YYYY-MM-DD format."); }
    }
    public static int parseTrainNumber(String value){
        try { int n=Integer.parseInt(value.trim()); if(n<=0) throw new NumberFormatException(); return n; }
        catch(NumberFormatException e){ throw new IllegalArgumentException("Train number must be numeric."); }
    }
}
