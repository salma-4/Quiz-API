package org.app.quizapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;

}
