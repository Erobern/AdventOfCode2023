package adventofcode2023.solvers;

import adventofcode2023.fileloaders.FileLoaders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static adventofcode2023.solvers.Day16.Direction.*;

public class Day17 {


    public static String Puzzle1(String input) {

        List<String> lines = FileLoaders.loadInputIntoStringList(input);

        Integer[][] grid = new Integer[lines.size()][lines.getFirst().length()];

        for (int i = 0; i < 110; i++) {
            for (int j = 0; j < 110; j++) {
                grid[i][j] = Integer.parseInt(String.valueOf(lines.get(i).charAt(j)));
            }
        }

        HashMap<String, Integer> solves = new HashMap<>();



        return null;
    }

    public static String Puzzle2(String input) {
     return null;
    }


}
