package com.hellFire.QuizGame.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "answer_evaluations")
public class AnswerEvaluation extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_submission_id", nullable = false)
    private AnswerSubmission answer;

    @Column(nullable = false)
    private boolean correct;
}