import adventofcode2023.solvers.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class PuzzleAnswerTests {

    @Test
    public void AllPuzzleSolutions() {
        Assertions.assertEquals("54968", Day1.Puzzle1("Day1_1.txt"), "Day 1, Puzzle 1");
        Assertions.assertEquals("54094", Day1.Puzzle2("Day1_1.txt"), "Day 1, Puzzle 2");

        Assertions.assertEquals("2164", Day2.Puzzle1("Day2_1.txt"), "Day 2, Puzzle 1");
        Assertions.assertEquals("69929", Day2.Puzzle2("Day2_1.txt"), "Day 2, Puzzle 2");

        Assertions.assertEquals("507214", Day3.Day3_Puzzle1("Day3_1.txt"), "Day 3, Puzzle 1");
        Assertions.assertEquals("72553319", Day3.Day3_Puzzle2("Day3_1.txt"), "Day 3, Puzzle 2");

        Assertions.assertEquals("23750", Day4.Puzzle1("Day4_1.txt"), "Day 4, Puzzle 1");
        Assertions.assertEquals("13261850", Day4.Puzzle2("Day4_1.txt"), "Day 4, Puzzle 2");

        Assertions.assertEquals("51580674", Day5.Puzzle1("Day5_1_seeds.txt",
                "Day5_1_seed-soil-map.txt",
                "Day5_1_soil-fertilizer-map.txt",
                "Day5_1_fertilizer-water-map.txt",
                "Day5_1_water-light-map.txt",
                "Day5_1_light-temperature-map.txt",
                "Day5_1_temperature-humidity-map.txt",
                "Day5_1_humidity-location-map.txt"), "Day 5, Puzzle 1");
        Assertions.assertEquals("99751240", Day5.Puzzle2("Day5_2_seeds.txt",
                "Day5_1_seed-soil-map.txt",
                "Day5_1_soil-fertilizer-map.txt",
                "Day5_1_fertilizer-water-map.txt",
                "Day5_1_water-light-map.txt",
                "Day5_1_light-temperature-map.txt",
                "Day5_1_temperature-humidity-map.txt",
                "Day5_1_humidity-location-map.txt"), "Day 5, Puzzle 2");

        Assertions.assertEquals("211904", Day6.Puzzle1(), "Day 6, Puzzle 1");
        Assertions.assertEquals("43364472", Day6.Puzzle2(), "Day 6, Puzzle 2");

        Assertions.assertEquals("250347426", Day7.Puzzle1("Day7_1.txt"), "Day 7, Puzzle 1");
        Assertions.assertEquals("251224870", Day7.Puzzle2("Day7_1.txt"), "Day 7, Puzzle 2");

        Assertions.assertEquals("22199", Day8.Puzzle1("Day8_1-steps.txt", "Day8_1-nodes.txt"), "Day 8, Puzzle 1");
        Assertions.assertEquals("13334102464297", Day8.Puzzle2("Day8_1-steps.txt", "Day8_1-nodes.txt"), "Day 8, Puzzle 2");

        Assertions.assertEquals("1696140818", Day9.Puzzle1("Day9_1.txt"), "Day 9, Puzzle 1");
        Assertions.assertEquals("1152", Day9.Puzzle2("Day9_1.txt"), "Day 9, Puzzle 2");

        Assertions.assertEquals("6968", Day10.Puzzle1("Day10_1.txt"), "Day 10, Puzzle 1");
        Assertions.assertEquals("413", Day10.Puzzle2("Day10_1.txt"), "Day 10, Puzzle 2");

        Assertions.assertEquals("9805264", Day11.Puzzle1("Day11_1.txt"), "Day 11, Puzzle 1");
        Assertions.assertEquals("779032247216", Day11.Puzzle2("Day11_1.txt"), "Day 11, Puzzle 2");

        Assertions.assertEquals("7718", Day12.Puzzle1_Rewrite("Day12_1.txt"), "Day 12, Puzzle 1 Rewrite");
        Assertions.assertEquals("128741994134728", Day12.Puzzle2_Rewrite("Day12_1.txt"), "Day 12, Puzzle 2");

        Assertions.assertEquals("28651", Day13.Puzzle1("Day13_1.txt"), "Day 13, Puzzle 1");
        Assertions.assertEquals("25450", Day13.Puzzle2("Day13_1.txt"), "Day 13, Puzzle 2");

        Assertions.assertEquals("113525", Day14.Puzzle1("Day14_1.txt"), "Day 14, Puzzle 1");
        Assertions.assertEquals("101292", Day14.Puzzle2("Day14_1.txt"), "Day 14, Puzzle 2");

        Assertions.assertEquals("495972", Day15.Puzzle1("Day15_1.txt"), "Day 15, Puzzle 1");
        Assertions.assertEquals("245223", Day15.Puzzle2("Day15_1.txt"), "Day 15, Puzzle 2");

        Assertions.assertEquals("7392", Day16.Puzzle1("Day16_1.txt", Optional.empty()), "Day 16, Puzzle 1");
        Assertions.assertEquals("7665", Day16.Puzzle2("Day16_1.txt"), "Day 16, Puzzle 2");
    }
}
