package adventofcode2023.solvers;

import adventofcode2023.fileloaders.FileLoaders;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;

public class Day12 {

    // Damaged, Unknown, Working -> D U W

    public static String Puzzle1(String input) {
        List<String> lines = FileLoaders.loadInputIntoStringList(input);

        AtomicLong arrangementsSum = new AtomicLong();

        lines.forEach(line -> {
            List<String> splitLines = Arrays.stream(line.split(" ")).toList();
            List<Integer> ints = Arrays.stream(splitLines.get(1).split(",")).map(Integer::parseInt).toList();
            String regex = "^[UW]*";
            for (int i = 0; i < ints.size(); i++) {
                regex += "[UD]{" + ints.get(i) + "}";
                regex += "[UW]+";
            }
            regex = regex.substring(0, regex.length() - 1) + "*$";
            Pattern pattern = Pattern.compile(regex);

            long matchingRegex = matchRegex(splitLines.get(0), pattern);

            System.out.println(splitLines.get(1) + ": " + matchingRegex);

            arrangementsSum.addAndGet(matchingRegex);

        });

        return String.valueOf(arrangementsSum.get());
    }

    private static long matchRegex(String stringToTest, Pattern pattern) {

        boolean isMatch = pattern.matcher(stringToTest).find();
        if (!stringToTest.contains("U")) {
            if (isMatch) {
                return 1;
            }
            return 0;
        }

        if (!isMatch) {
            return 0;
        }

        long matches = 0;
        matches += matchRegex(stringToTest.replaceFirst("U", "W"), pattern);
        matches += matchRegex(stringToTest.replaceFirst("U", "D"), pattern);
        return matches;
    }

    // brute force way - not feasible for part 2
//    public static String Puzzle1(String input) {
//        List<String> lines = FileLoaders.loadInputIntoStringList(input);
//
//        AtomicInteger arrangementsSum = new AtomicInteger();
//
//        lines.forEach(line -> {
//            List<String> splitLines = Arrays.stream(line.split(" ")).toList();
//            List<Integer> ints = Arrays.stream(splitLines.get(1).split(",")).map(Integer::parseInt).toList();
//            String regex = "^W*";
//            for (int i = 0; i < ints.size(); i++) {
//                regex += "D{" + ints.get(i) + "}";
//                regex += "W+";
//            }
//            regex = regex.substring(0, regex.length() - 1) + "*$";
//            Pattern pattern = Pattern.compile(regex);
//
//            Integer unknowns = Math.toIntExact(Arrays.stream(splitLines.get(0).split("")).filter(s -> s.equals("U")).count());
//
//            int matchingRegex = 0;
//
//            for (int i = 0; i < Math.pow(2, unknowns); i++) {
//                String stringToTest = splitLines.get(0);
//                for (int j = 0; j < unknowns; j++) {
//                    // treat i as a bitflag, perform bitwise operations to iterate through every possible combination of unknowns
//                    stringToTest = stringToTest.replaceFirst("[U]", ((i >>> j) & 1) != 0 ? "W" : "D");
//                }
//                if (pattern.matcher(stringToTest).find()) {
//                    matchingRegex++;
//                }
//            }
//
//            System.out.println(splitLines.get(1) + ": " + matchingRegex);
//
//            arrangementsSum.addAndGet(matchingRegex);
//
//        });
//
//        return String.valueOf(arrangementsSum.get());
//    }

    public static String Puzzle2(String input) {
        List<String> lines = FileLoaders.loadInputIntoStringList(input);

        AtomicLong arrangementsSum = new AtomicLong();

        lines.parallelStream().forEach(line -> {
            List<String> splitLines = Arrays.stream(line.split(" ")).toList();

            System.out.println(splitLines.get(1) + ": starting...");

            List<Integer> ints = Arrays.stream((splitLines.get(1) + "," + splitLines.get(1) + "," +
                    splitLines.get(1) + "," + splitLines.get(1) + "," + splitLines.get(1)).split(",")).map(Integer::parseInt).toList();
            String regex = "^[UW]*";
            for (int i = 0; i < ints.size(); i++) {
                regex += "[UD]{" + ints.get(i) + "}";
                regex += "[UW]+";
            }
            regex = regex.substring(0, regex.length() - 1) + "*$";
            Pattern pattern = Pattern.compile(regex);

            long matchingRegex = matchRegex(splitLines.get(0) + "U" + splitLines.get(0) + "U" +
                    splitLines.get(0) + "U" + splitLines.get(0) + "U" + splitLines.get(0), pattern);

            System.out.println(splitLines.get(1) + ": " + matchingRegex);

            arrangementsSum.addAndGet(matchingRegex);

        });

        return String.valueOf(arrangementsSum.get());
    }

}
