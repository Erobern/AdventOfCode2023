package adventofcode2023.solvers;

import adventofcode2023.fileloaders.FileLoaders;
import adventofcode2023.utils.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day1 {
    public static String Day1_Puzzle1() {
        List<String> lines = FileLoaders.loadInputIntoStringList("Day1_1.txt");

        Integer sum = lines
                .stream()
                .map(line -> (Arrays.stream(line.split(""))
                        .filter(Utils::isInt).findFirst()
                        .map(Integer::parseInt)
                        .orElse(0) * 10) +
                        (Arrays.stream(line.split(""))
                                .filter(Utils::isInt).reduce((first, second) -> second)
                                .map(Integer::parseInt)
                                .orElse(0)))
                .reduce(0, Integer::sum);

        return sum.toString();
    }

    public static String Day1_Puzzle2() {
        List<String> lines = FileLoaders.loadInputIntoStringList("Day1_1.txt");

        Integer sum = lines
                .stream()
                .map(line -> (Arrays.stream(line.split(""))
                        .filter(Utils::isInt).findFirst()
                        .map(Integer::parseInt)
                        .orElse(0) * 10) +
                        (Arrays.stream(line.split(""))
                                .filter(Utils::isInt).reduce((first, second) -> second)
                                .map(Integer::parseInt)
                                .orElse(0)))
                .reduce(0, Integer::sum);

        return sum.toString();
    }
}
