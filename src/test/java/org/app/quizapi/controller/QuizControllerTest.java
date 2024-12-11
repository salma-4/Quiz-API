package org.app.quizapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.app.quizapi.dto.question.QuestionResponseDTO;
import org.app.quizapi.dto.question.QuestionsDTO;
import org.app.quizapi.dto.quiz.QuizDTO;
import org.app.quizapi.dto.quiz.QuizRequestDto;
import org.app.quizapi.dto.quiz.QuizResponseDto;
import org.app.quizapi.exception.RecordNotFoundException;
import org.app.quizapi.repository.UserTokenRepo;
import org.app.quizapi.security.JWTService;
import org.app.quizapi.service.QuizService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {QuizController.class})
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class QuizControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private QuizService quizService;
    @MockBean
    private UserTokenRepo userTokenRepo;
    @MockBean
    private JWTService jwtService;
    private QuizRequestDto mockQuizRequestDto;
    private QuizDTO mockQuizDto;
    private final List<QuestionsDTO> mockQuestions = new ArrayList<>();
    private final List<QuestionResponseDTO> mockQuestionResponseDto = new ArrayList<>();

    private long quizId;

    @BeforeEach
    void setUp() {
        quizId = 1L;

        mockQuestions.add(QuestionsDTO.builder().questionTitle("What is 1+1?").optionA("2").optionB("3").optionC("4").optionD("5").answer("A").degree(2).build());
        mockQuestions.add(QuestionsDTO.builder().questionTitle("What is 2+2?").optionA("3").optionB("4").optionC("5").optionD("6").answer("B").degree(3).build());
        mockQuestionResponseDto.add(QuestionResponseDTO.builder().questionTitle("What is 2+2?").optionA("3").optionB("4").optionC("5").optionD("6").degree(3).build());
        mockQuestionResponseDto.add(QuestionResponseDTO.builder().questionTitle("What is 2+3?").optionA("3").optionB("4").optionC("5").optionD("6").degree(3).build());

        mockQuizRequestDto = QuizRequestDto.builder().type("Math").questions(mockQuestions).build();
        mockQuizDto = QuizDTO.builder().id(1L).category("Math").questionResponse(mockQuestionResponseDto).build();
    }

    @Test
    void getQuizByCategoryTest_existingCategory_returnsQuizRequestDto() throws Exception {
        //Arrange
        String category = "Math";
        Mockito.when(quizService.getQuizByType(category)).thenReturn(mockQuizRequestDto);

        //Act & Assert
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/quizApp/v1/quiz/category/{category}", category)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value(mockQuizRequestDto.getType()))
                .andExpect(jsonPath("$.questions").isArray())
                .andExpect(jsonPath("$.questions[0].questionTitle").value(mockQuestions.getFirst().getQuestionTitle()));

    }

    @Test
    void getQuizByCategoryTest_nonExistingCategory_throwsException() throws Exception {
        //Arrange
        String category = "Math";
        Mockito.when(quizService.getQuizByType(category)).thenThrow(new RecordNotFoundException("No quiz with category " + category));

        //Act & Assert
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/quizApp/v1/quiz/category/{category}", category))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No quiz with category " + category));

    }

    @Test
    void createQuizTest_returnsSuccessMsg() throws Exception {
        //Arrange
        String expectedMessage = "Quiz created successfully!";
        Mockito.when(quizService.createQuiz(Mockito.any(QuizRequestDto.class))).thenReturn(expectedMessage);

        //Act & Assert
        mockMvc.perform(post("/quizApp/v1/quiz")
                        .contentType(MediaType.APPLICATION_JSON) // Set Content-Type
                        .content(new ObjectMapper().writeValueAsString(mockQuizRequestDto))) // Set body
                .andExpect(status().isCreated())
                .andExpect(content().string(expectedMessage)); // Verify 201 Created
    }

    @Test
    void deleteQuizTest_validId_returnsSuccessMsg() throws Exception {
        //Arrange
        String expectedMsg = "Quiz deleted successfully";
        Mockito.when(quizService.deletQuiz(quizId)).thenReturn(expectedMsg);

        //Act & Assert
        mockMvc.perform(
                        delete("/quizApp/v1/quiz/{quizId}", quizId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedMsg));
    }

    @Test
    void deleteQuiz_invalidId_throwsException() throws Exception {
        //Arrange
        String expectedMsg = "Quiz not found with ID: " + quizId;
        Mockito.when(quizService.getQuizById(quizId)).thenThrow(new RecordNotFoundException(expectedMsg));
        Mockito.doThrow(new RecordNotFoundException(expectedMsg)).when(quizService).deletQuiz(quizId);

        //Act & Assert
        mockMvc.perform(
                        delete("/quizApp/v1/quiz/{quizId}", quizId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(expectedMsg));
    }

    @Test
    void getAllQuizzesCategoriesTest_returnsListOfQuizResponseDto() throws Exception {
        // Arrange
        List<QuizResponseDto> mockResponse = new ArrayList<>();
        QuizResponseDto quizCategory1 = new QuizResponseDto();
        quizCategory1.setCategory("Category 1");
        QuizResponseDto quizCategory2 = new QuizResponseDto();
        quizCategory2.setCategory("Category 2");
        mockResponse.add(quizCategory1);
        mockResponse.add(quizCategory2);

        Mockito.when(quizService.getAllQuizCategories()).thenReturn(mockResponse);

        //Act & Assert
        mockMvc.perform(
                        get("/quizApp/v1/quiz/allCategories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].category").value("Category 1"));

    }

    @Test
    void getQuizByIdTest_validId_returnsQuizDto() throws Exception {
        //Arrange
        Mockito.when(quizService.getQuizById(quizId)).thenReturn(mockQuizDto);

        //Act & Assert
        mockMvc.perform(
                        get("/quizApp/v1/quiz//{quizId}", quizId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category").value(mockQuizDto.getCategory()));
    }

    @Test
    void getQuizByIdTest_invalidId_throwsException() throws Exception {
        //Arrange
        String expectedMsg = "No quiz with id " + quizId;
        Mockito.doThrow(new RecordNotFoundException(expectedMsg)).when(quizService).getQuizById(quizId);

        //Act & Assert
        mockMvc.perform(
                        get("/quizApp/v1/quiz//{quizId}", quizId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(expectedMsg));
    }
}