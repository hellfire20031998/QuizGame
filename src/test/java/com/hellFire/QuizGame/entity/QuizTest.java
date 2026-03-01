package com.hellFire.QuizGame.entity;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuizTest {

    @Test
    void defaultConstructor_shouldInitializePublishedAsFalse() {
        Quiz quiz = new Quiz();

        assertFalse(quiz.isPublished());
    }

    @Test
    void builderAndGetters_shouldPopulateFieldsCorrectly() {
        User creator = new User();
        creator.setId(10L);

        Question question = new Question();
        question.setText("Sample question");

        Quiz quiz = Quiz.builder()
                .title("Sample Quiz")
                .description("A simple quiz for testing")
                .published(true)
                .createdBy(creator)
                .totalTimeInSeconds(300L)
                .questions(List.of(question))
                .build();

        assertEquals("Sample Quiz", quiz.getTitle());
        assertEquals("A simple quiz for testing", quiz.getDescription());
        assertTrue(quiz.isPublished());
        assertEquals(creator, quiz.getCreatedBy());
        assertEquals(300L, quiz.getTotalTimeInSeconds());
        assertNotNull(quiz.getQuestions());
        assertEquals(1, quiz.getQuestions().size());
        assertEquals("Sample question", quiz.getQuestions().get(0).getText());
    }
}

