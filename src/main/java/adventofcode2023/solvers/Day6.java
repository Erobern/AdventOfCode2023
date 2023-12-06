package adventofcode2023.solvers;

import java.math.BigInteger;
import java.util.List;

public class Day6 {

//    Time:        56     71     79     99
//    Distance:   334   1135   1350   2430

    public static String Day6_Puzzle1() {
        List<Race> races = List.of(
                new Race(56, 334), new Race(71, 1135),
                new Race(79, 1350), new Race(99, 2430)
        );

        return String.valueOf(races.stream()
                .map(race -> {
                    Integer winningScenarios = 0;
                    for (int i = 0; i < race.time(); i++) {
                        if ((race.time() - i) * i > race.recordDistance()) {
                            winningScenarios++;
                        }
                    }
                    return winningScenarios;
                })
                .reduce(1, (a, b) -> a * b));

    }

    public static String Day6_Puzzle2() {
        Part2Race race =
                new Part2Race(new BigInteger("56717999"), new BigInteger("334113513502430"));

        BigInteger firstWinningGame = BigInteger.ZERO;
        for (BigInteger i = BigInteger.ZERO; i.compareTo(race.time()) < 0; i = i.add(BigInteger.ONE)) {
            if ((race.time().subtract(i)).multiply(i).compareTo(race.recordDistance()) > 0) {
                firstWinningGame = i;
                break;
            }
        }

        BigInteger lastWinningGame = BigInteger.ZERO;
        for (BigInteger i = race.time().subtract(BigInteger.ONE); i.compareTo(BigInteger.ZERO) > 0; i = i.subtract(BigInteger.ONE)) {
            if (i.multiply(race.time().subtract(i)).compareTo(race.recordDistance()) > 0) {
                lastWinningGame = i;
                break;
            }
        }

        return lastWinningGame.subtract(firstWinningGame).add(BigInteger.ONE).toString();

    }

    public record Race(Integer time, Integer recordDistance) {
    }

    public record Part2Race(BigInteger time, BigInteger recordDistance) {
    }


}
