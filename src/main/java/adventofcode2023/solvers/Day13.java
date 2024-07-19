package adventofcode2023.solvers;

import adventofcode2023.fileloaders.FileLoaders;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Day13 {
    public static String Puzzle1(String input) {
        List<String> lines = FileLoaders.loadInputIntoStringList(input);

        List<String> tempLines = new ArrayList<>();

        List<List<String>> grids = new ArrayList<>();

        lines.forEach(line -> {
            if (line.isEmpty()) {
                grids.add(new ArrayList<>(tempLines));
                tempLines.clear();
            } else {
                tempLines.add(line);
            }
        });
        grids.add(new ArrayList<>(tempLines));

        AtomicInteger columnsToLeft = new AtomicInteger();
        AtomicInteger rowsAbove = new AtomicInteger();

        grids.forEach(grid -> {
            int rows = grid.size();
            int columns = grid.getFirst().length();
            char[][] charGrid = new char[rows][columns];

            for (int i = 0; i < rows; i++) {
                String row = grid.get(i);
                for (int j = 0; j < columns; j++) {
                    charGrid[i][j] = row.charAt(j);
                }
            }

            // test vertical mirrors
            for (int i = 1; i < columns; i++) {
                boolean candidate = true;
                for (int j = 0; j < rows; j++) {
                    if (charGrid[j][i - 1] != charGrid[j][i]) {
                        candidate = false;
                        break;
                    }
                }

                if (!candidate) {
                    continue;
                } else {
                    // keep testing until we hit an out of bounds exception
                    int offset = 1;
                    while (candidate) {
                        try {
                            for (int j = 0; j < rows; j++) {
                                if (charGrid[j][i - 1 - offset] != charGrid[j][i + offset]) {
                                    candidate = false;
                                    break;
                                }
                            }
                        } catch (IndexOutOfBoundsException e) {
                            columnsToLeft.addAndGet(i);
                            candidate = false;
                        }
                        offset++;
                    }
                }
            }

            // test horizontal mirrors
            for (int i = 1; i < rows; i++) {
                boolean candidate = true;
                for (int j = 0; j < columns; j++) {
                    if (charGrid[i - 1][j] != charGrid[i][j]) {
                        candidate = false;
                        break;
                    }
                }

                if (!candidate) {
                    continue;
                } else {
                    // keep testing until we hit an out of bounds exception
                    int offset = 1;
                    while (candidate) {
                        try {
                            for (int j = 0; j < columns; j++) {
                                if (charGrid[i - 1 - offset][j] != charGrid[i + offset][j]) {
                                    candidate = false;
                                    break;
                                }
                            }
                        } catch (IndexOutOfBoundsException e) {
                            rowsAbove.addAndGet(i);
                            candidate = false;
                        }
                        offset++;
                    }
                }

            }

        });

        return ((Integer) (columnsToLeft.get() + (rowsAbove.get() * 100))).toString();
    }

    public static String Puzzle2(String input) {
        List<String> lines = FileLoaders.loadInputIntoStringList(input);

        List<String> tempLines = new ArrayList<>();

        List<List<String>> grids = new ArrayList<>();

        lines.forEach(line -> {
            if (line.isEmpty()) {
                grids.add(new ArrayList<>(tempLines));
                tempLines.clear();
            } else {
                tempLines.add(line);
            }
        });
        grids.add(new ArrayList<>(tempLines));

        AtomicInteger columnsToLeft = new AtomicInteger();
        AtomicInteger rowsAbove = new AtomicInteger();

        grids.forEach(grid -> {
            int rows = grid.size();
            int columns = grid.getFirst().length();
            char[][] charGrid = new char[rows][columns];

            for (int i = 0; i < rows; i++) {
                String row = grid.get(i);
                for (int j = 0; j < columns; j++) {
                    charGrid[i][j] = row.charAt(j);
                }
            }

            // test vertical mirrors
            for (int i = 1; i < columns; i++) {
                boolean candidate = true;
                int smudgeCount = 0;
                for (int j = 0; j < rows; j++) {
                    if (charGrid[j][i - 1] != charGrid[j][i]) {
                        smudgeCount++;
                        if (smudgeCount > 1) {
                            candidate = false;
                            break;
                        }
                    }
                }

                if (!candidate) {
                    continue;
                } else {
                    // keep testing until we hit an out of bounds exception

                    int offset = 1;
                    while (candidate) {
                        try {
                            for (int j = 0; j < rows; j++) {
                                if (charGrid[j][i - 1 - offset] != charGrid[j][i + offset]) {
                                    smudgeCount++;

                                    if (smudgeCount > 1) {
                                        candidate = false;
                                        break;
                                    }
                                }
                            }
                        } catch (IndexOutOfBoundsException e) {
                            if (smudgeCount == 1) {
                                columnsToLeft.addAndGet(i);
                                return;
                            }
                            candidate = false;
                        }
                        offset++;
                    }
                }
            }

            // test horizontal mirrors
            for (int i = 1; i < rows; i++) {
                boolean candidate = true;
                int smudgeCount = 0;
                for (int j = 0; j < columns; j++) {
                    if (charGrid[i - 1][j] != charGrid[i][j]) {
                        smudgeCount++;

                        if (smudgeCount > 1) {
                            candidate = false;
                            break;
                        }
                    }
                }

                if (!candidate) {
                    continue;
                } else {
                    // keep testing until we hit an out of bounds exception

                    int offset = 1;
                    while (candidate) {
                        try {
                            for (int j = 0; j < columns; j++) {
                                if (charGrid[i - 1 - offset][j] != charGrid[i + offset][j]) {
                                    smudgeCount++;

                                    if (smudgeCount > 1) {
                                        candidate = false;
                                        break;
                                    }
                                }
                            }
                        } catch (IndexOutOfBoundsException e) {
                            if (smudgeCount == 1) {
                                rowsAbove.addAndGet(i);
                                return;
                            }
                            candidate = false;
                        }
                        offset++;
                    }
                }

            }

        });

        return ((Integer) (columnsToLeft.get() + (rowsAbove.get() * 100))).toString();
    }
}
