package org.app.quizapi.dto.quiz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.app.quizapi.dto.question.QuestionsDTO;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizRequestDto {
    private String type;
    List<QuestionsDTO> questions;
}
