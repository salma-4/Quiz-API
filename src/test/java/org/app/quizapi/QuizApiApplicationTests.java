package org.app.quizapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "security.enabled=false")
class QuizApiApplicationTests {

    @Test
    void contextLoads() {
    }

}
