import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс для тестирования.
 */
class SolveTest {

    /**
     * Тест для выражения, для которого есть решение.
     * @param expected ожидаемый ответ.
     * @param input исходное выражение.
     */
    void solveTest(String expected, String input) {

        try {
            assertEquals(expected, (new Solve(input)).solve());
        } catch (Exception exp) {
            fail("Exception: " + exp.getMessage());
        }
    }

    /**
     * Тетст для выражения, для которого нет решения.
     * @param input исходной выражение.
     */
    void notSolveTest(String input) {
        try {
            assertEquals(null, (new Solve(input)).solve());
        } catch (Exception exp) {
            fail("Exception: " + exp.getMessage());
        }
    }

    /**
     * Тест для выражения, для которого возникает исключение.
     * @param input исходной выражение.
     */
    void tryTest(String input) {
        try {
            new Solve(input).solve();
            fail("Must be try.");
        } catch (Exception exp) {}
    }

    @Test
    void solve() {
        solveTest("56+4-(4*4-((5*3)*(4+2))-2)*(4-2+1+0)=288", "56 4(4 4((5 3) (4 2)) 2) (4 2 1 0)=288");
        solveTest("5+6-4+(4*4*((5*3)+(4-2))+2)+(4+2+1+0)=288", "5 6 4(4 4((5 3) (4 2)) 2) (4 2 1 0)=288");
        solveTest("4+3-3*(1*1+((((50)+2-5))))+3+100+42=2+2+1+3", "4  3  3  (1  1 ((((50)2 5))))  3 100 42 = 2  2  1  3");
        solveTest("4453+(45*2-3+4*(1-2-3)*54)=36*100+76", "4453  (45  2 3  4  ( 1  2 3 )  54 ) = 36 100 76");
        solveTest("45-3*4-3=2*2*2*2*2-2","45  3  4  3 = 2 2 2 2 2 2 ");
        solveTest("3-2+(4+1+8*4)=34+2+2","3  2  (4  1  8  4 ) = 34 2 2");

        notSolveTest("4 (10 3) 4 (5 3) 2 = 100000000");
        notSolveTest("(5 6 (4 3) (5 3 223)) (4 5 23) = 3443543543 2 10");
        notSolveTest("5 342 23 43 (67 (56 3 4 (7))) = (64531314532 3)");
        notSolveTest("4 3 4 3 2 3 1 31 1 = 1243264352243");
        notSolveTest("(1 1 1 1 1) (1 1 1 1 1) = 4543432423");
        notSolveTest("675 657 65 76 57 645 43 5 = 32343242123");
        notSolveTest("(54 = 3");

        tryTest("435 43 5 43 5 43 534  + 45 =4 ");
        tryTest("324 324 32 = 234 32 432 - 23432");
        tryTest("2 + 2 = 4");
        tryTest("4 ) ^ = 4");
        tryTest("34 23 43 2 3 2 3  4 ? #");
    }
}