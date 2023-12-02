package adventofcode2023.solvers;

import adventofcode2023.fileloaders.FileLoaders;

import java.util.Arrays;
import java.util.List;

public class Day2 {

    public static String Day2_Puzzle1() {

        final Integer redMax = 12;
        final Integer greenMax = 13;
        final Integer blueMax = 14;

        List<String> lines = FileLoaders.loadInputIntoStringList("Day2_1.txt");

        List<List<List<RevealedCubes>>> revealedCubesSets = lines
                .stream()
                .map(line -> Arrays.stream(line.split(":")).reduce((first, second) -> second).get())
                .map(line -> Arrays.stream(line.split(";"))
                        .map(set -> Arrays.stream(set.split(",")).map(
                                        cubes -> new RevealedCubes(getCount(cubes), getType(cubes))
                                ).toList()
                        ).toList())
                .toList();

        Integer gameIdSum = 0;
        Integer gameUnderEvaluation = 1;

        for (List<List<RevealedCubes>> revealedCubeSets : revealedCubesSets) {
            boolean isGamePossible = true;

            for (List<RevealedCubes> revealedCubes : revealedCubeSets) {
                Integer redTotal = 0;
                Integer greenTotal = 0;
                Integer blueTotal = 0;

                for (RevealedCubes individual : revealedCubes) {
                    if (individual.type() == "green") {
                        greenTotal += individual.count();
                    }
                    if (individual.type() == "red") {
                        redTotal += individual.count();
                    }
                    if (individual.type() == "blue") {
                        blueTotal += individual.count();
                    }
                }

                if (redTotal > redMax || greenTotal > greenMax || blueTotal > blueMax) {
                    isGamePossible = false;
                    break;
                }

            }
            if (isGamePossible) {
                gameIdSum += gameUnderEvaluation;
            }

            gameUnderEvaluation++;
        }

        return gameIdSum.toString();
    }

    public static String Day2_Puzzle2() {
        List<String> lines = FileLoaders.loadInputIntoStringList("Day2_1.txt");

        List<List<List<RevealedCubes>>> revealedCubesSets = lines
                .stream()
                .map(line -> Arrays.stream(line.split(":")).reduce((first, second) -> second).get())
                .map(line -> Arrays.stream(line.split(";")).map(
                        set -> Arrays.stream(set.split(",")).map(
                                cubes -> new RevealedCubes(getCount(cubes), getType(cubes))
                        ).toList()
                ).toList())
                .toList();

        Integer sumOfPowers = 0;

        for (List<List<RevealedCubes>> revealedCubeSets : revealedCubesSets) {

            Integer redMin = 0;
            Integer greenMin = 0;
            Integer blueMin = 0;

            for (List<RevealedCubes> revealedCubes : revealedCubeSets) {
                for (RevealedCubes individual : revealedCubes) {
                    if (individual.type() == "green" && greenMin < individual.count()) {
                        greenMin = individual.count();
                    }
                    if (individual.type() == "red" && redMin < individual.count()) {
                        redMin = individual.count();
                    }
                    if (individual.type() == "blue" && blueMin < individual.count()) {
                        blueMin = individual.count();
                    }
                }
            }

            sumOfPowers += redMin * greenMin * blueMin;

        }

        return sumOfPowers.toString();
    }


    private static String getType(String splitLine) {
        if (splitLine.contains("green")) {
            return "green";
        }
        if (splitLine.contains("red")) {
            return "red";
        }
        return "blue";
    }

    private static Integer getCount(String splitLine) {
        return Integer.parseInt(Arrays.stream(splitLine.strip().split(" ")).findFirst().orElse("0"));
    }


    private record RevealedCubes(Integer count, String type) {
    }

}