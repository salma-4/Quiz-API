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
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionTitle;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private int degree;
    private String answer;

    @ManyToOne(fetch = FetchType.LAZY ,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;
}
