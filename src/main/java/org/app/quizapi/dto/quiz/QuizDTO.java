package org.app.quizapi.dto.quiz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.app.quizapi.dto.question.QuestionResponseDTO;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizDTO {
    private Long id;
    private String category;
    private List<QuestionResponseDTO> questionResponse;
}
