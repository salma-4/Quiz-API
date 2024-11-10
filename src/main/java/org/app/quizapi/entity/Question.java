package org.app.quizapi.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String questionTitle;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private int degree;
    private String answer;

}
