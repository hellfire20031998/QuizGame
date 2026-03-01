package com.hellFire.QuizGame.controllers;

import com.hellFire.QuizGame.dto.UserDto;
import com.hellFire.QuizGame.dto.request.LoginRequest;
import com.hellFire.QuizGame.dto.request.SignUpRequest;
import com.hellFire.QuizGame.dto.response.AuthResponse;
import com.hellFire.QuizGame.entity.User;
import com.hellFire.QuizGame.entity.enums.Role;
import com.hellFire.QuizGame.services.IUserService;
import com.hellFire.QuizGame.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IUserService userService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void signup_shouldReturnCreatedUserDto() throws Exception {
        SignUpRequest request = new SignUpRequest(
                "john",
                "john@example.com",
                "John Doe",
                "password",
                Role.PLAYER,
                "local"
        );

        User user = new User();
        user.setId(1L);
        user.setEmail(request.getEmail());

        UserDto userDto = new UserDto();
        userDto.setUsername(request.getUsername());
        userDto.setEmail(request.getEmail());
        userDto.setName(request.getName());
        userDto.setRole(Role.PLAYER);

        when(userService.createUser(ArgumentMatchers.any(SignUpRequest.class))).thenReturn(user);
        when(userService.toDto(user)).thenReturn(userDto);

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("john"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void login_shouldReturnTokenOnValidCredentials() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail("john@example.com");
        request.setPassword("password");

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword("encoded-password");

        when(userService.getUserByEmail(request.getEmail())).thenReturn(user);
        when(passwordEncoder.matches(request.getPassword(), user.getPassword())).thenReturn(true);

        String token = "test-jwt-token";
        when(jwtUtil.generateToken(user)).thenReturn(token);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setEmail(user.getEmail());
        authResponse.setUsername("john");
        authResponse.setToken(token);
        authResponse.setRole(Role.PLAYER);

        when(userService.toAuthResponse(user, token)).thenReturn(authResponse);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Login successful"))
                .andExpect(jsonPath("$.data.token").value(token))
                .andExpect(jsonPath("$.data.email").value("john@example.com"))
                .andExpect(jsonPath("$.data.username").value("john"));
    }

    @Test
    void login_shouldReturnUnauthorizedOnInvalidCredentials() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail("invalid@example.com");
        request.setPassword("wrong-password");

        when(userService.getUserByEmail(request.getEmail())).thenReturn(null);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value("UNAUTHORIZED"));
    }
}

