package adventofcode2023.solvers;

import adventofcode2023.fileloaders.FileLoaders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day9 {

    public static String Puzzle1(String input) {
        List<String> lines = FileLoaders.loadInputIntoStringList(input);
        List<List<Integer>> allSets = lines.stream().map(line -> Arrays.stream(line.split(" ")).map(Integer::parseInt).toList()).toList();

        return String.valueOf(allSets.stream().map(set -> {
            List<List<Integer>> tree = new ArrayList<>();
            tree.add(set);

            boolean allZeros = false;
            while (!allZeros) {
                Integer target = tree.size() - 1;
                List<Integer> nextSet = new ArrayList<>();
                for (int i = 1; i < tree.get(tree.size() - 1).size(); i++) {
                    nextSet.add(tree.get(target).get(i) - tree.get(target).get(i - 1));
                }
                tree.add(nextSet);

                if (nextSet.stream().filter(i -> i != 0).count() == 0) {
                    allZeros = true;
                }
            }

            Collections.reverse(tree);

            Integer carryForward = 0;
            for (int i = 1; i < tree.size(); i++) {
                carryForward = tree.get(i).get(tree.get(i).size() - 1) + carryForward;
            }

            return carryForward;
        }).reduce(0, (a, b) -> a + b));
    }

    public static String Puzzle2(String input) {
        List<String> lines = FileLoaders.loadInputIntoStringList(input);
        List<List<Integer>> allSets = lines.stream().map(line -> Arrays.stream(line.split(" ")).map(Integer::parseInt).toList()).toList();

        return String.valueOf(allSets.stream().map(set -> {
            List<List<Integer>> tree = new ArrayList<>();
            tree.add(set);

            boolean allZeros = false;
            while (!allZeros) {
                Integer target = tree.size() - 1;
                List<Integer> nextSet = new ArrayList<>();
                for (int i = 1; i < tree.get(tree.size() - 1).size(); i++) {
                    nextSet.add(tree.get(target).get(i) - tree.get(target).get(i - 1));
                }
                tree.add(nextSet);

                if (nextSet.stream().filter(i -> i != 0).count() == 0) {
                    allZeros = true;
                }
            }

            Collections.reverse(tree);

            Integer carryForward = 0;
            for (int i = 1; i < tree.size(); i++) {
                carryForward = tree.get(i).get(0) - carryForward;
            }

            return carryForward;
        }).reduce(0, (a, b) -> a + b));
    }

}
