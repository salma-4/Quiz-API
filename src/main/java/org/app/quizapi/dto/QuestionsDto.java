package org.app.quizapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionsDto {

    private String questionTitle;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private int degree;
    private String answer;
}
