package com.oasis.reservation.service;
import java.security.SecureRandom;

public class PnrGenerator {
    private final SecureRandom random = new SecureRandom();
    public String generate() {
        long n = 1_000_000_000L + (long)(random.nextDouble()*9_000_000_000L);
        return Long.toString(n);
    }
}
