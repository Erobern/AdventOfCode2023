package adventofcode2023.solvers;

import adventofcode2023.fileloaders.FileLoaders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Day11 {

    public static String Puzzle1(String input) {
        List<String> lines = FileLoaders.loadInputIntoStringList(input);
        Character[][] nodes = new Character[140][140];

        IntStream.range(0, 140)
                .forEach(idxX -> {
                    List<String> row = Arrays.stream(lines.get(idxX).split("")).toList();
                    IntStream.range(0, 140)
                            .forEach(idxY -> {
                                nodes[idxX][idxY] = row.get(idxY).charAt(0);
                            });
                });

        List<Integer> emptyRows = new ArrayList<>();
        List<Integer> emptyColumns = new ArrayList<>();

        for (int i = 0; i < 140; i++) {
            boolean galaxyFound = false;
            for (int j = 0; j < 140; j++) {
                if (nodes[i][j] == '#') {
                    galaxyFound = true;
                    break;
                }
            }
            if (!galaxyFound) {
                emptyRows.add(i);
            }
        }

        for (int j = 0; j < 140; j++) {
            boolean galaxyFound = false;
            for (int i = 0; i < 140; i++) {
                if (nodes[i][j] == '#') {
                    galaxyFound = true;
                    break;
                }
            }
            if (!galaxyFound) {
                emptyColumns.add(j);
            }
        }

        int expandedXSize = 140 + emptyRows.size();
        int expandedYSize = 140 + emptyColumns.size();

        Character[][] expandedNodes = new Character[expandedXSize][expandedYSize];

        int xOffset = 0;
        int yOffset = 0;

        for (int i = 0; i < expandedXSize; i++) {
            yOffset = 0;

            if (emptyRows.contains(i - xOffset)) {
                for (int j = 0; j < expandedYSize; j++) {
                    expandedNodes[i][j] = '.';
                }
                i++;
                for (int j = 0; j < expandedYSize; j++) {
                    expandedNodes[i][j] = '.';
                }
                xOffset++;
            } else {
                for (int j = 0; j < expandedYSize; j++) {
                    if (emptyColumns.contains(j - yOffset)) {
                        expandedNodes[i][j] = '.';
                        j++;
                        yOffset++;
                    }
                    expandedNodes[i][j] = nodes[i - xOffset][j - yOffset];
                }
            }
        }

        List<Galaxy> galaxies = new ArrayList<>();

        for (int i = 0; i < expandedXSize; i++) {
            for (int j = 0; j < expandedYSize; j++) {
                if (expandedNodes[i][j] == '#') {
                    galaxies.add(new Galaxy(i, j));
                }
            }
        }

        int sumOfShortestPaths = 0;
        int galaxyUnderEvaluation = 0;

        for (Galaxy galaxy : galaxies) {
            List<Galaxy> galaxiesToMatch = galaxies.subList(galaxyUnderEvaluation + 1, galaxies.size());

            for (Galaxy galaxyToMatch : galaxiesToMatch) {
                sumOfShortestPaths += Math.abs(galaxy.y() - galaxyToMatch.y()) +
                        Math.abs(galaxy.x() - galaxyToMatch.x());
            }

            galaxyUnderEvaluation++;
        }

        return String.valueOf(sumOfShortestPaths);
    }

    public static String Puzzle2(String input) {
        List<String> lines = FileLoaders.loadInputIntoStringList(input);
        Character[][] nodes = new Character[140][140];

        IntStream.range(0, 140)
                .forEach(idxX -> {
                    List<String> row = Arrays.stream(lines.get(idxX).split("")).toList();
                    IntStream.range(0, 140)
                            .forEach(idxY -> {
                                nodes[idxX][idxY] = row.get(idxY).charAt(0);
                            });
                });

        List<Integer> emptyRows = new ArrayList<>();
        List<Integer> emptyColumns = new ArrayList<>();

        for (int i = 0; i < 140; i++) {
            boolean galaxyFound = false;
            for (int j = 0; j < 140; j++) {
                if (nodes[i][j] == '#') {
                    galaxyFound = true;
                    break;
                }
            }
            if (!galaxyFound) {
                emptyRows.add(i);
            }
        }

        for (int j = 0; j < 140; j++) {
            boolean galaxyFound = false;
            for (int i = 0; i < 140; i++) {
                if (nodes[i][j] == '#') {
                    galaxyFound = true;
                    break;
                }
            }
            if (!galaxyFound) {
                emptyColumns.add(j);
            }
        }

        List<Galaxy> galaxies = new ArrayList<>();

        for (int i = 0; i < 140; i++) {
            for (int j = 0; j < 140; j++) {
                if (nodes[i][j] == '#') {
                    galaxies.add(new Galaxy(i, j));
                }
            }
        }

        long sumOfShortestPaths = 0;
        int galaxyUnderEvaluation = 0;

        for (Galaxy galaxy : galaxies) {
            List<Galaxy> galaxiesToMatch = galaxies.subList(galaxyUnderEvaluation + 1, galaxies.size());

            for (Galaxy galaxyToMatch : galaxiesToMatch) {

                int emptyRowsBetween;
                int emptyColumnsBetween;

                int biggerX = Math.max(galaxy.x(), galaxyToMatch.x());
                int smallerX = Math.min(galaxy.x(), galaxyToMatch.x());

                int biggerY = Math.max(galaxy.y(), galaxyToMatch.y());
                int smallerY = Math.min(galaxy.y(), galaxyToMatch.y());

                if (biggerX == smallerX) {
                    emptyRowsBetween = 0;
                } else {
                    emptyRowsBetween = (int) emptyRows.stream().filter(row -> row < biggerX && row > smallerX).count();
                }

                if (biggerY == smallerY) {
                    emptyColumnsBetween = 0;
                } else {
                    emptyColumnsBetween = (int) emptyColumns.stream().filter(column -> column < biggerY && column > smallerY).count();
                }

                sumOfShortestPaths += Math.abs(galaxy.y() - galaxyToMatch.y()) +
                        Math.abs(galaxy.x() - galaxyToMatch.x()) +
                        ((long) emptyRowsBetween * 999999) + ((long) emptyColumnsBetween * 999999);
            }

            galaxyUnderEvaluation++;
        }

        return String.valueOf(sumOfShortestPaths);
    }

    public record Galaxy(int x, int y) {
    }
}
