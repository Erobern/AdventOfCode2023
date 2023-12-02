import adventofcode2023.solvers.Day1;
import adventofcode2023.solvers.Day2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PuzzleAnswerTests {

    @Test
    public void Day1_Puzzle1() {
        Assertions.assertEquals("54968", Day1.Day1_Puzzle1(), "Day 1, Puzzle 1");
        Assertions.assertEquals("54094", Day1.Day1_Puzzle2(), "Day 1, Puzzle 2");

        Assertions.assertEquals("2164", Day2.Day2_Puzzle1(), "Day 2, Puzzle 1");
        Assertions.assertEquals("69929", Day2.Day2_Puzzle2(), "Day 2, Puzzle 2");
    }
}
