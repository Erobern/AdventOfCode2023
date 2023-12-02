package adventofcode2023.solvers;

import adventofcode2023.fileloaders.FileLoaders;
import adventofcode2023.utils.Utils;

import java.util.ArrayList;
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
                .map(line ->
                        (generateLineListOfNumbers(line)
                                .stream()
                                .findFirst().orElse(0)* 10) +
                                generateLineListOfNumbers(line)
                                        .stream()
                                        .reduce((first, second) -> second)
                                        .stream().findFirst()
                                        .orElse(0)
                )
                .reduce(0, Integer::sum);

        return sum.toString();
    }

    private static List<Integer> generateLineListOfNumbers(String line) {
        List<Integer> returnList = new ArrayList<>();
        for(int i = 0; i < line.length(); i++)
        {
            String stringToEvaluate = line.substring(i);
            addNumber(returnList, stringToEvaluate);
            addWord(returnList, stringToEvaluate);
        }

        return returnList.stream().filter(i -> i >= 0).toList();
    }

    private static void addWord(List<Integer> returnList, String stringToEvaluate) {
        if (stringToEvaluate.matches("one[a-z0-9]*")) {
            returnList.add(1);
        }
        if (stringToEvaluate.matches("two[a-z0-9]*")) {
            returnList.add(2);
        }
        if (stringToEvaluate.matches("three[a-z0-9]*")) {
            returnList.add(3);
        }
        if (stringToEvaluate.matches("four[a-z0-9]*")) {
            returnList.add(4);
        }
        if (stringToEvaluate.matches("five[a-z0-9]*")) {
            returnList.add(5);
        }
        if (stringToEvaluate.matches("six[a-z0-9]*")) {
            returnList.add(6);
        }
        if (stringToEvaluate.matches("seven[a-z0-9]*")) {
            returnList.add(7);
        }
        if (stringToEvaluate.matches("eight[a-z0-9]*")) {
            returnList.add(8);
        }
        if (stringToEvaluate.matches("nine[a-z0-9]*")) {
            returnList.add(9);
        }
        if (stringToEvaluate.matches("zero[a-z0-9]*")) {
            returnList.add(0);
        }
    }

    private static void addNumber(List<Integer> returnList, String stringToEvaluate) {
        try {
            returnList.add(Integer.parseInt(stringToEvaluate.substring(0, 1)));
        } catch (NumberFormatException e) {

        }
    }


}
