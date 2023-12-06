import adventofcode2023.solvers.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PuzzleAnswerTests {

    @Test
    public void Day1_Puzzle1() {
        Assertions.assertEquals("54968", Day1.Day1_Puzzle1(), "Day 1, Puzzle 1");
        Assertions.assertEquals("54094", Day1.Day1_Puzzle2(), "Day 1, Puzzle 2");

        Assertions.assertEquals("2164", Day2.Day2_Puzzle1(), "Day 2, Puzzle 1");
        Assertions.assertEquals("69929", Day2.Day2_Puzzle2(), "Day 2, Puzzle 2");

        Assertions.assertEquals("507214", Day3.Day3_Puzzle1(), "Day 3, Puzzle 1");
        Assertions.assertEquals("72553319", Day3.Day3_Puzzle2(), "Day 3, Puzzle 2");

        Assertions.assertEquals("23750", Day4.Day4_Puzzle1(), "Day 4, Puzzle 1");
        Assertions.assertEquals("13261850", Day4.Day4_Puzzle2(), "Day 4, Puzzle 2");

        Assertions.assertEquals("51580674", Day5.Day5_Puzzle1(), "Day 5, Puzzle 1");
        Assertions.assertEquals("99751240", Day5.Day5_Puzzle2(), "Day 5, Puzzle 2");

        Assertions.assertEquals("211904", Day6.Day6_Puzzle1(), "Day 6, Puzzle 1");
        Assertions.assertEquals("43364472", Day6.Day6_Puzzle2(), "Day 6, Puzzle 2");
    }
}
