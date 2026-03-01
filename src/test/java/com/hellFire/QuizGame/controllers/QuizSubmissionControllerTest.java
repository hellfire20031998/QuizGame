package com.hellFire.QuizGame.controllers;

import com.hellFire.QuizGame.dto.QuizSubmissionDto;
import com.hellFire.QuizGame.dto.request.SubmitQuizRequest;
import com.hellFire.QuizGame.entity.User;
import com.hellFire.QuizGame.services.impl.QuizSubmissionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuizSubmissionController.class)
@AutoConfigureMockMvc(addFilters = false)
class QuizSubmissionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private QuizSubmissionService submissionService;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void submitQuiz_shouldReturnSubmissionDtoForAuthenticatedUser() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setEmail("candidate@example.com");

        SubmitQuizRequest request = new SubmitQuizRequest();
        request.setQuizId(42L);
        request.setTotalTimeInSeconds(120L);
        request.setAnswers(Collections.emptyList());

        QuizSubmissionDto dto = new QuizSubmissionDto();
        dto.setId(100L);
        dto.setScore(5);
        dto.setCompleted(true);

        when(submissionService.submitQuiz(any(SubmitQuizRequest.class), eq(user))).thenReturn(dto);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user, null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(post("/submission")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.score").value(5))
                .andExpect(jsonPath("$.data.completed").value(true));
    }
}

