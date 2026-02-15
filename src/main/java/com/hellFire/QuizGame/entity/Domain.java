package com.hellFire.QuizGame.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "domains")
public class Domain extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;
}
