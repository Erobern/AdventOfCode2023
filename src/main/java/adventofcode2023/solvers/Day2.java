package adventofcode2023.solvers;

import adventofcode2023.fileloaders.FileLoaders;

import java.util.Arrays;
import java.util.List;

public class Day2 {

    public static String Day2_Puzzle1() {

        final int redMax = 12;
        final int greenMax = 13;
        final int blueMax = 14;

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

        int gameIdSum = 0;
        int gameUnderEvaluation = 1;

        for (List<List<RevealedCubes>> revealedCubeSets : revealedCubesSets) {
            boolean isGamePossible = true;

            for (List<RevealedCubes> revealedCubes : revealedCubeSets) {
                Integer redTotal = 0;
                Integer greenTotal = 0;
                Integer blueTotal = 0;

                for (RevealedCubes individual : revealedCubes) {
                    if (individual.type().equals("green")) {
                        greenTotal += individual.count();
                    }
                    if (individual.type().equals("red")) {
                        redTotal += individual.count();
                    }
                    if (individual.type().equals("blue")) {
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

        return Integer.toString(gameIdSum);
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

        int sumOfPowers = 0;

        for (List<List<RevealedCubes>> revealedCubeSets : revealedCubesSets) {

            int redMin = 0;
            int greenMin = 0;
            int blueMin = 0;

            for (List<RevealedCubes> revealedCubes : revealedCubeSets) {
                for (RevealedCubes individual : revealedCubes) {
                    if (individual.type().equals("green") && greenMin < individual.count()) {
                        greenMin = individual.count();
                    }
                    if (individual.type().equals("red") && redMin < individual.count()) {
                        redMin = individual.count();
                    }
                    if (individual.type().equals("blue") && blueMin < individual.count()) {
                        blueMin = individual.count();
                    }
                }
            }

            sumOfPowers += redMin * greenMin * blueMin;

        }

        return String.valueOf(sumOfPowers);
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