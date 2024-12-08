package org.app.quizapi.service;

import org.app.quizapi.dto.question.QuestionsDTO;
import org.app.quizapi.dto.quiz.QuizDTO;
import org.app.quizapi.dto.quiz.QuizRequestDto;
import org.app.quizapi.dto.quiz.QuizResponseDto;
import org.app.quizapi.entity.Question;
import org.app.quizapi.entity.Quiz;
import org.app.quizapi.exception.RecordNotFoundException;
import org.app.quizapi.mapper.question.QuestionMapper;
import org.app.quizapi.mapper.quiz.QuizMapper;
import org.app.quizapi.repository.QuestionRepo;
import org.app.quizapi.repository.QuizRepo;
import org.app.quizapi.service.impl.QuizServiceImp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class QuizServiceTest {
    @Mock
    private QuizRepo quizRepo;
    @Mock
    private QuizMapper quizMapper;
    @Mock
    private QuestionMapper questionMapper;
    @Mock
    private QuestionRepo questionRepo;
    @InjectMocks
    private QuizServiceImp quizService;

    private QuizRequestDto quizRequestDto;
    private List<QuestionsDTO> questionList;
    @BeforeEach
    void setUp() {
        questionList = new ArrayList<>();
        questionList.add(QuestionsDTO.builder()
                .questionTitle("What is the speed of light?")
                .optionA("3x10^8 m/s")
                .optionB("1x10^6 m/s")
                .optionC("2x10^6 m/s")
                .optionC("5x10^9 m/s")
                .answer("A")
                .degree(2)
                .build());
        questionList.add(QuestionsDTO.builder()
                .questionTitle("Who developed the theory of relativity?")
                .optionA("Newton")
                .optionB("Einstein")
                .optionC("Galileo")
                .optionD("NONE OF ABOVE")
                .answer("B")
                .degree(2)
                .build());


        quizRequestDto = QuizRequestDto.builder()
                .type("Science")
                .questions(questionList)
                .build();
    }
    @Test
    void createQuiz_notExistingCategory_returnsNewQuizCreatedMessage() {
        // Arrange

        // :( Mocking the behavior of the quizMapper and questionMapper to return valid entities
        Quiz mockQuiz = new Quiz();
        Mockito.when(quizMapper.toEntity(quizRequestDto)).thenReturn(mockQuiz);

        Question mockQuestion1 = new Question();
        Question mockQuestion2 = new Question();
        Mockito.when(questionMapper.toEntity(questionList.get(0))).thenReturn(mockQuestion1);
        Mockito.when(questionMapper.toEntity(questionList.get(1))).thenReturn(mockQuestion2);

        List<Question> questions = quizRequestDto.getQuestions().stream()
                .map(questionMapper::toEntity)
                .peek(question -> question.setQuiz(mockQuiz))
                .collect(Collectors.toList());
        mockQuiz.setQuestions(questions);

        Mockito.when(quizRepo.findQuizByType("Science")).thenReturn(Optional.empty());

        // Act
        String resultMessage = quizService.createQuiz(quizRequestDto);

        // Assert
        Mockito.verify(quizRepo).save(mockQuiz);
        assertThat(resultMessage).isEqualTo("New quiz created successfully");
    }
    @Test
    void createQuiz_existingCategory_returnsUpdatedQuizMessage() {
        // Arrange
        Quiz existingQuiz = new Quiz();
        Mockito.when(quizRepo.findQuizByType("Science")).thenReturn(Optional.of(existingQuiz));

        Question mockQuestion1 = new Question();
        Question mockQuestion2 = new Question();
        Mockito.when(questionMapper.toEntity(Mockito.any())).thenReturn(mockQuestion1, mockQuestion2);

        // Act
        String msg = quizService.createQuiz(quizRequestDto);

        // Assert
        Mockito.verify(quizRepo).findQuizByType("Science");
        Mockito.verify(questionRepo).saveAll(Mockito.anyList());
        assertThat(msg).isEqualTo("Quiz updated with new questions successfully");

    }
    @Test
    void getAllQuizCategoriesTest_existingQuizzes_returnAll() {
        //Arrange

        List<Quiz> quizzes = List.of(new Quiz(),new Quiz());
        Mockito.when(quizRepo.findAll()).thenReturn(quizzes);
        QuizResponseDto mockQuizResponse = new QuizResponseDto();
        Mockito.when(quizMapper.toResponseDTO(Mockito.any())).thenReturn(mockQuizResponse);

        //Act
        List<QuizResponseDto> quizResponseDtos= quizService.getAllQuizCategories();

        //Assert
        Mockito.verify(quizRepo).findAll(); // Verify that findAll() was called
        assertThat(quizResponseDtos.isEmpty()).isFalse();
        assertThat(quizResponseDtos.getFirst()).isEqualTo(mockQuizResponse);

    }
    @Test
    void deleteQuizTest_existingQuiz_returnSuccessMessage() {
       //Arrange
        Long quizId = 1L;
        Quiz quiz = new Quiz();
        Mockito.when(quizRepo.findById(quizId)).thenReturn(Optional.of(quiz));

        //Act
       String msg = quizService.deletQuiz(quizId);

       //Assert
        assertThat(msg).isEqualTo("Quiz deleted successfully");
        Mockito.verify(quizRepo).delete(quiz);
    }
    @Test
    void deleteQuizTest_notExistingQuiz_throwException(){
        //Arrange
        Mockito.when(quizRepo.findById(1L)).thenReturn(Optional.empty());

        //Act & Assert
        assertThatThrownBy(() -> quizService.deletQuiz(1L))
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessageContaining("Quiz not found with ID: 1");
    }
    @Test
    void getQuizByTypeTest_existingType_returnQuizRequestDTO() {
        //Arrange
        String category ="Science";
        Quiz existingQuiz = new Quiz();
        Mockito.when(quizRepo.findQuizByType(category)).thenReturn(Optional.of(existingQuiz));
        QuizRequestDto quizRequest = new QuizRequestDto();
        Mockito.when(quizMapper.toDTO(existingQuiz)).thenReturn(quizRequest);

        //Act
        QuizRequestDto result = quizService.getQuizByType(category);

        //Assert
        assertThat(result).isNotNull();
        Mockito.verify(quizRepo).findQuizByType(category);
    }
    @Test
    void getQuizByTypeTest_notExistingType_throwException(){
        //Arrange
        String category = "Science";
        Mockito.when(quizRepo.findQuizByType(category)).thenReturn(Optional.empty());

        //Act & Assert
        assertThatThrownBy(()->quizService.getQuizByType(category))
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessageContaining("this type not exist");

    }
    @Test
    void getQuizByIdTest_existingQuiz_returnQuizDTO() {
        //Arrange
        Long quizId = 1L;
        Quiz existingQuiz = new Quiz();
        Mockito.when(quizRepo.findById(quizId)).thenReturn(Optional.of(existingQuiz));
        QuizDTO quizDTO = new QuizDTO();
        Mockito.when(quizMapper.toResponse(existingQuiz)).thenReturn(quizDTO);

        //Act
        QuizDTO result = quizService.getQuizById(quizId);

        //Assert
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(quizDTO);
        Mockito.verify(quizRepo).findById(quizId);

    }
    @Test
    void getQuizByIdTest_notExistingQuiz_throwException(){
        //Arrange
        Long quizId= 1L;
        Mockito.when(quizRepo.findById(quizId)).thenReturn(Optional.empty());

        //Act &Assert
        assertThatThrownBy(()->quizService.getQuizById(quizId))
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessageContaining("No quiz with id 1");
    }

    //TODO test update
    @Test
    @Disabled
    void updateQuiz() {
    }

    @AfterEach
    void tearDown() {
    }

}