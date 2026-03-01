package com.hellFire.QuizGame.utils;

import com.hellFire.QuizGame.entity.User;
import io.jsonwebtoken.io.Encoders;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JwtUtilTest {

    private JwtUtil createJwtUtil(long expirationMillis) {
        JwtUtil jwtUtil = new JwtUtil();

        byte[] keyBytes = new byte[32];
        new SecureRandom().nextBytes(keyBytes);
        String secret = Encoders.BASE64.encode(keyBytes);

        ReflectionTestUtils.setField(jwtUtil, "secret", secret);
        ReflectionTestUtils.setField(jwtUtil, "expiration", expirationMillis);

        jwtUtil.init();
        return jwtUtil;
    }

    @Test
    void generateTokenAndExtractEmail_shouldWorkWithValidUser() {
        JwtUtil jwtUtil = createJwtUtil(3_600_000L);

        User user = User.builder()
                .email("test@example.com")
                .build();

        String token = jwtUtil.generateToken(user);

        assertNotNull(token);

        String extractedEmail = jwtUtil.extractEmail(token);

        assertEquals("test@example.com", extractedEmail);
    }
}

