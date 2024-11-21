package org.app.quizapi.dto.question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponseDTO {
    private Long id;
    private String questionTitle;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private int degree;
}
