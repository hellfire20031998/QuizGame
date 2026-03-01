package com.hellFire.QuizGame.entity;

import com.hellFire.QuizGame.entity.enums.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void builderAndGetters_shouldPopulateFieldsCorrectly() {
        User user = User.builder()
                .username("john_doe")
                .email("john@example.com")
                .name("John Doe")
                .password("secret")
                .provider("local")
                .role(Role.PLAYER)
                .build();

        assertEquals("john_doe", user.getUsername());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("John Doe", user.getName());
        assertEquals("secret", user.getPassword());
        assertEquals("local", user.getProvider());
        assertEquals(Role.PLAYER, user.getRole());
    }

    @Test
    void equalsAndHashCode_shouldUseIdOnly() {
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");

        User user2 = new User();
        user2.setId(1L);
        user2.setUsername("user2");

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());

        user2.setId(2L);

        assertNotEquals(user1, user2);
    }

    @Test
    void equals_shouldReturnFalseForDifferentClassOrNull() {
        User user = new User();
        user.setId(1L);

        assertNotEquals(user, null);
        assertNotEquals(user, "some string");
    }
}

