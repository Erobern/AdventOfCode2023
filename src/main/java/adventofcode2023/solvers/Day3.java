package adventofcode2023.solvers;

import adventofcode2023.fileloaders.FileLoaders;
import adventofcode2023.utils.IntUtils;

import java.util.ArrayList;
import java.util.List;

public class Day3 {

    static final int MAX_LINE_LENGTH = 140;

    public static String Day3_Puzzle1() {
        List<String> lines = FileLoaders.loadInputIntoStringList("Day3_1.txt");
        List<NumberAndLocation> numbersAndLocations = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                if (IntUtils.isInt(line.substring(j, j + 1))) {

                    int numberLength = 0;

                    if (j + 3 <= MAX_LINE_LENGTH && IntUtils.isInt(line.substring(j, j + 3))) {
                        numberLength = 3;
                    } else if (j + 2 <= MAX_LINE_LENGTH && IntUtils.isInt(line.substring(j, j + 2))) {
                        numberLength = 2;
                    } else {
                        numberLength = 1;
                    }

                    numbersAndLocations.add(new NumberAndLocation(Integer.parseInt(line.substring(j, j + numberLength)), i, j));

                    j += numberLength;
                }
            }
        }

        int partSum = 0;

        for (NumberAndLocation numberAndLocation : numbersAndLocations) {
            partSum += addPartSum(lines, numberAndLocation);
        }

        return String.valueOf(partSum);
    }

    private static int addPartSum(List<String> lines, NumberAndLocation numberAndLocation) {
        boolean isPart = false;
        isPart = isPart || checkBox(numberAndLocation.i() - 1, numberAndLocation.j() - 1, lines);
        isPart = isPart || checkBox(numberAndLocation.i() - 1, numberAndLocation.j(), lines);
        isPart = isPart || checkBox(numberAndLocation.i() - 1, numberAndLocation.j() + 1, lines);
        isPart = isPart || checkBox(numberAndLocation.i() + 1, numberAndLocation.j() - 1, lines);
        isPart = isPart || checkBox(numberAndLocation.i() + 1, numberAndLocation.j(), lines);
        isPart = isPart || checkBox(numberAndLocation.i() + 1, numberAndLocation.j() + 1, lines);
        isPart = isPart || checkBox(numberAndLocation.i(), numberAndLocation.j() - 1, lines);

        if (numberAndLocation.number() < 10) {
            isPart = isPart || checkBox(numberAndLocation.i(), numberAndLocation.j() + 1, lines);
        } else if (numberAndLocation.number() < 100) {
            isPart = isPart || checkBox(numberAndLocation.i() - 1, numberAndLocation.j() + 2, lines);
            isPart = isPart || checkBox(numberAndLocation.i() + 1, numberAndLocation.j() + 2, lines);
            isPart = isPart || checkBox(numberAndLocation.i(), numberAndLocation.j() + 2, lines);
        } else {
            isPart = isPart || checkBox(numberAndLocation.i() - 1, numberAndLocation.j() + 2, lines);
            isPart = isPart || checkBox(numberAndLocation.i() + 1, numberAndLocation.j() + 2, lines);
            isPart = isPart || checkBox(numberAndLocation.i() - 1, numberAndLocation.j() + 3, lines);
            isPart = isPart || checkBox(numberAndLocation.i() + 1, numberAndLocation.j() + 3, lines);
            isPart = isPart || checkBox(numberAndLocation.i(), numberAndLocation.j() + 3, lines);
        }

        if (isPart) {
            return numberAndLocation.number();
        }
        return 0;
    }

    private static boolean checkBox(int i, int j, List<String> lines) {
        try {
            return !lines.get(i).substring(j, j + 1).matches("[0-9.]");
        } catch (Exception ignored) {
            return false;
        }
    }

    public static String Day3_Puzzle2() {
        List<String> lines = FileLoaders.loadInputIntoStringList("Day3_1.txt");
        List<NumberAndLocation> numbersAndLocations = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                if (IntUtils.isInt(line.substring(j, j + 1))) {

                    int numberLength = 0;

                    if (j + 3 <= MAX_LINE_LENGTH && IntUtils.isInt(line.substring(j, j + 3))) {
                        numberLength = 3;
                    } else if (j + 2 <= MAX_LINE_LENGTH && IntUtils.isInt(line.substring(j, j + 2))) {
                        numberLength = 2;
                    } else {
                        numberLength = 1;
                    }

                    numbersAndLocations.add(new NumberAndLocation(Integer.parseInt(line.substring(j, j + numberLength)), i, j));

                    j += numberLength;
                }
            }
        }

        int gearRatioSum = 0;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == '*') {
                    gearRatioSum += addGearRatioSumIfExactlyTwoNumberNeighbours(i, j, numbersAndLocations);
                }
            }
        }

        return String.valueOf(gearRatioSum);
    }

    private static int addGearRatioSumIfExactlyTwoNumberNeighbours(int i, int j, List<NumberAndLocation> numbersAndLocations) {
        int numberNeighbours = 0;

        int ratioSum = 1;

        for (NumberAndLocation numberAndLocation : numbersAndLocations) {
            if (numberAndLocation.number() < 10) {
                if (numberAndLocation.i() - 1 <= i && numberAndLocation.i() + 1 >= i &&
                        numberAndLocation.j() - 1 <= j && numberAndLocation.j() + 1 >= j) {
                    numberNeighbours++;
                    if (numberNeighbours > 2) {
                        return 0;
                    }
                    ratioSum *= numberAndLocation.number();
                }
            } else if (numberAndLocation.number() < 100) {
                if (numberAndLocation.i() - 1 <= i && numberAndLocation.i() + 1 >= i &&
                        numberAndLocation.j() - 1 <= j && numberAndLocation.j() + 2 >= j) {
                    numberNeighbours++;
                    if (numberNeighbours > 2) {
                        return 0;
                    }
                    ratioSum *= numberAndLocation.number();
                }
            } else {
                if (numberAndLocation.i() - 1 <= i && numberAndLocation.i() + 1 >= i &&
                        numberAndLocation.j() - 1 <= j && numberAndLocation.j() + 3 >= j) {
                    numberNeighbours++;
                    if (numberNeighbours > 2) {
                        return 0;
                    }
                    ratioSum *= numberAndLocation.number();
                }
            }
        }

        if (numberNeighbours == 2) {
            return ratioSum;
        }

        return 0;
    }

    private record NumberAndLocation(Integer number, Integer i, Integer j) {
    }
}
