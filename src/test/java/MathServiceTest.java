import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MathServiceTest {
    private final MathService mathService = new MathService();

    // Позитивный тест: на два различных корня
    @Test
    public void testSolveQuadraticEquationPositiveDiscriminant() throws NotFoundAnswerException {
        // given
        int a = 1, b = -3, c = 2;
        // when
        Pair result = mathService.getAnswer(a, b, c);
        // then
        assertEquals(2.0, result.first);
        assertEquals(1.0, result.second);
    }

    // Негативный тест: нет действительных корней (отрицательный дискриминант)
    @Test
    public void testSolveQuadraticEquationNegativeDiscriminant() {
        // given
        int a = 1, b = 2, c = 5;
        // when
        // then
        assertThrows(NotFoundAnswerException.class, () -> {
            mathService.getAnswer(a, b, c);
        });
    }

    // Параметризованный тест для различных значений
    @ParameterizedTest
    @CsvSource({
            "1, -3, 2, 2.0, 1.0",
            "1, 2, 1, -1.0, -1.0",
            "1, 0, -4, 2.0, -2.0",
            "1, 0, 4, NotFoundAnswerException, NotFoundAnswerException"
    })
    public void testSolveQuadraticEquationParameterized(int a, int b, int c, String expectedFirst, String expectedSecond) {
        if ("NotFoundAnswerException".equals(expectedFirst)) {
            // given
            // when
            // then
            assertThrows(NotFoundAnswerException.class, () -> {
                mathService.getAnswer(a, b, c);
            });
        } else {
            try {
                // given
                // when
                Pair result = mathService.getAnswer(a, b, c);
                // then
                assertEquals(Double.parseDouble(expectedFirst), result.first);
                assertEquals(Double.parseDouble(expectedSecond), result.second);
            } catch (NotFoundAnswerException e) {
                fail("Не ожидалось исключение");
            }
        }
    }
}
