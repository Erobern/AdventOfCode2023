package adventofcode2023.solvers;

import adventofcode2023.fileloaders.FileLoaders;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Day14 {


    private static final HashMap<String, String> northAndWestHashMap = new HashMap<>();
    private static final HashMap<String, String> southAndEastHashMap = new HashMap<>();
    private static final HashMap<String, Integer> boulderGridHashSetSolves = new HashMap<>();
    private static final HashMap<Integer, char[][]> boulderGridHashSetBoulderLayouts = new HashMap<>();

    public static String Puzzle1(String input) {
        List<String> lines = FileLoaders.loadInputIntoStringList(input);

        char[][] boulderGrid = new char[100][100];

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                boulderGrid[i][j] = lines.get(i).charAt(j);
            }
        }

        // roll north
        for (int j = 0; j < 100; j++) {
            for (int i = 1; i < 100; i++) {
                if (boulderGrid[i][j] == 'O') {
                    // find the northernmost open spot
                    int newIPosition = i;
                    boolean blocked = false;
                    while (!blocked && newIPosition >= 0) {
                        if (newIPosition > 0 && boulderGrid[newIPosition - 1][j] == '.') {
                            newIPosition--;
                        } else {
                            blocked = true;
                            boulderGrid[i][j] = '.';
                            boulderGrid[newIPosition][j] = 'O';
                        }
                    }
                }
            }
        }

        Integer totalLoad = 0;

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                if (boulderGrid[i][j] == 'O') {
                    totalLoad += 100 - i;
                }
            }
        }

        return totalLoad.toString();

    }

    public static String Puzzle2(String input) {
        List<String> lines = FileLoaders.loadInputIntoStringList(input);

        char[][] boulderGrid = new char[100][100];

        StringBuilder tempRow;


        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                boulderGrid[i][j] = lines.get(i).charAt(j);
            }
        }


        // repeat a billion times.... because why not
        for (int bigCount = 0; bigCount < 1_000_000_000; bigCount++) {
            if (bigCount % 2500 == 0) {
                System.out.println(bigCount);
            }

            // roll north
            for (int j = 0; j < 100; j++) {

                tempRow = new StringBuilder();

                for (int z = 0; z < 100; z++) {
                    tempRow.append(boulderGrid[z][j]);
                }

                if (northAndWestHashMap.containsKey(tempRow.toString())) {
                    tempRow = new StringBuilder(northAndWestHashMap.get(tempRow.toString()));

                    for (int z = 0; z < 100; z++) {
                        boulderGrid[z][j] = tempRow.charAt(z);
                    }

                } else {
                    String key = tempRow.toString();

                    for (int i = 1; i < 100; i++) {
                        if (boulderGrid[i][j] == 'O') {
                            // find the northernmost open spot
                            int newIPosition = i;
                            boolean blocked = false;
                            while (!blocked && newIPosition >= 0) {
                                if (newIPosition > 0 && boulderGrid[newIPosition - 1][j] == '.') {
                                    newIPosition--;
                                } else {
                                    blocked = true;
                                    boulderGrid[i][j] = '.';
                                    boulderGrid[newIPosition][j] = 'O';
                                }
                            }
                        }
                    }

                    tempRow = new StringBuilder();

                    for (int z = 0; z < 100; z++) {
                        tempRow.append(boulderGrid[z][j]);
                    }

                    northAndWestHashMap.put(key, tempRow.toString());
                }
            }

            // roll west

            for (int i = 0; i < 100; i++) {
                tempRow = new StringBuilder();

                for (int z = 0; z < 100; z++) {
                    tempRow.append(boulderGrid[i][z]);
                }

                if (northAndWestHashMap.containsKey(tempRow.toString())) {
                    tempRow = new StringBuilder(northAndWestHashMap.get(tempRow.toString()));

                    for (int z = 0; z < 100; z++) {
                        boulderGrid[i][z] = tempRow.charAt(z);
                    }
                } else {
                    String key = tempRow.toString();
                    for (int j = 1; j < 100; j++) {
                        if (boulderGrid[i][j] == 'O') {
                            // find the westernmost open spot
                            int newJPosition = j;
                            boolean blocked = false;
                            while (!blocked && newJPosition >= 0) {
                                if (newJPosition > 0 && boulderGrid[i][newJPosition - 1] == '.') {
                                    newJPosition--;
                                } else {
                                    blocked = true;
                                    boulderGrid[i][j] = '.';
                                    boulderGrid[i][newJPosition] = 'O';
                                }
                            }
                        }
                    }

                    tempRow = new StringBuilder();

                    for (int z = 0; z < 100; z++) {
                        tempRow.append(boulderGrid[i][z]);
                    }


                    northAndWestHashMap.put(key, tempRow.toString());
                }
            }

            // roll south
            for (int j = 0; j < 100; j++) {

                tempRow = new StringBuilder();

                for (int z = 0; z < 100; z++) {
                    tempRow.append(boulderGrid[z][j]);
                }

                if (southAndEastHashMap.containsKey(tempRow.toString())) {
                    tempRow = new StringBuilder(southAndEastHashMap.get(tempRow.toString()));

                    for (int z = 0; z < 100; z++) {
                        boulderGrid[z][j] = tempRow.charAt(z);
                    }

                } else {
                    String key = tempRow.toString();

                    for (int i = 98; i >= 0; i--) {
                        if (boulderGrid[i][j] == 'O') {
                            // find the southernmost open spot
                            int newIPosition = i;
                            boolean blocked = false;
                            while (!blocked && newIPosition <= 99) {
                                if (newIPosition < 99 && boulderGrid[newIPosition + 1][j] == '.') {
                                    newIPosition++;
                                } else {
                                    blocked = true;
                                    boulderGrid[i][j] = '.';
                                    boulderGrid[newIPosition][j] = 'O';
                                }
                            }
                        }
                    }
                    tempRow = new StringBuilder();

                    for (int z = 0; z < 100; z++) {
                        tempRow.append(boulderGrid[z][j]);
                    }

                    southAndEastHashMap.put(key, tempRow.toString());
                }
            }

            // roll east
            for (int i = 0; i < 100; i++) {
                tempRow = new StringBuilder();

                for (int z = 0; z < 100; z++) {
                    tempRow.append(boulderGrid[i][z]);
                }

                if (southAndEastHashMap.containsKey(tempRow.toString())) {
                    tempRow = new StringBuilder(southAndEastHashMap.get(tempRow.toString()));

                    for (int z = 0; z < 100; z++) {
                        boulderGrid[i][z] = tempRow.charAt(z);
                    }
                } else {
                    String key = tempRow.toString();
                    for (int j = 98; j >= 0; j--) {
                        if (boulderGrid[i][j] == 'O') {
                            // find the easternmost open spot
                            int newJPosition = j;
                            boolean blocked = false;
                            while (!blocked && newJPosition <= 99) {
                                if (newJPosition < 99 && boulderGrid[i][newJPosition + 1] == '.') {
                                    newJPosition++;
                                } else {
                                    blocked = true;
                                    boulderGrid[i][j] = '.';
                                    boulderGrid[i][newJPosition] = 'O';
                                }
                            }
                        }
                    }

                    tempRow = new StringBuilder();

                    for (int z = 0; z < 100; z++) {
                        tempRow.append(boulderGrid[i][z]);
                    }


                    southAndEastHashMap.put(key, tempRow.toString());
                }
            }

            if (boulderGridHashSetSolves.containsKey(Arrays.deepToString(boulderGrid))) {
                int match = boulderGridHashSetSolves.get(Arrays.deepToString(boulderGrid));
                int period = bigCount - match;

                while (bigCount + period < 1_000_000_000) {
                    bigCount += period;
                }
            } else {
                boulderGridHashSetSolves.put(Arrays.deepToString(boulderGrid), bigCount);
                boulderGridHashSetBoulderLayouts.put(bigCount, boulderGrid);
            }
        }

        Integer totalLoad = 0;

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                if (boulderGrid[i][j] == 'O') {
                    totalLoad += 100 - i;
                }
            }
        }

        return totalLoad.toString();

    }
}