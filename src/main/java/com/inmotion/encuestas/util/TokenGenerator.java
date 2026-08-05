package com.inmotion.encuestas.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class TokenGenerator {
    
    private static final int TOKEN_LENGTH = 32;
    private final SecureRandom secureRandom = new SecureRandom();
    
    public String generarToken() {
        byte[] bytes = new byte[TOKEN_LENGTH];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}