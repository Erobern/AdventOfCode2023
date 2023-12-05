import adventofcode2023.solvers.Day5;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;

public class Day5_2Tests {

    @Test
    public void testUnderScenario() {
        List<Day5.InputMap> results = Day5.translateMapToNextMaps(
                new Day5.InputMap(new BigInteger("5"), new BigInteger("5")),
                List.of(new Day5.TranslationMap(new BigInteger("15"), new BigInteger("0"), new BigInteger("15")))
        );

        assertInputMap(results, List.of(new Day5.InputMap(new BigInteger("5"), new BigInteger("5"))));
    }

    @Test
    public void testOverScenario() {
        List<Day5.InputMap> results = Day5.translateMapToNextMaps(
                new Day5.InputMap(new BigInteger("5"), new BigInteger("5")),
                List.of(new Day5.TranslationMap(new BigInteger("0"), new BigInteger("0"), new BigInteger("2")),
                        new Day5.TranslationMap(new BigInteger("15"), new BigInteger("0"), new BigInteger("2")))
        );

        assertInputMap(results, List.of(new Day5.InputMap(new BigInteger("5"), new BigInteger("5"))));
    }

    @Test
    public void testWithinScenario() {
        List<Day5.InputMap> results = Day5.translateMapToNextMaps(
                new Day5.InputMap(new BigInteger("3"), new BigInteger("10")),
                List.of(new Day5.TranslationMap(new BigInteger("2"), new BigInteger("0"), new BigInteger("15")),
                        new Day5.TranslationMap(new BigInteger("30"), new BigInteger("0"), new BigInteger("15")))
        );

        assertInputMap(results, List.of(new Day5.InputMap(new BigInteger("1"), new BigInteger("10"))));
    }

    @Test
    public void testWithoutScenario() {
        List<Day5.InputMap> results = Day5.translateMapToNextMaps(
                new Day5.InputMap(new BigInteger("3"), new BigInteger("10")),
                List.of(new Day5.TranslationMap(new BigInteger("7"), new BigInteger("0"), new BigInteger("2")),
                        new Day5.TranslationMap(new BigInteger("15"), new BigInteger("0"), new BigInteger("2")))
        );

        assertInputMap(results, List.of(new Day5.InputMap(new BigInteger("3"), new BigInteger("4")),
                new Day5.InputMap(new BigInteger("0"), new BigInteger("2")),
                new Day5.InputMap(new BigInteger("7"), new BigInteger("6"))));
    }

    @Test
    public void testFrontOverlapScenario() {
        List<Day5.InputMap> results = Day5.translateMapToNextMaps(
                new Day5.InputMap(new BigInteger("10"), new BigInteger("10")),
                List.of(new Day5.TranslationMap(new BigInteger("13"), new BigInteger("0"), new BigInteger("10")),
                        new Day5.TranslationMap(new BigInteger("50"), new BigInteger("0"), new BigInteger("5")))
        );

        assertInputMap(results, List.of(new Day5.InputMap(new BigInteger("10"), new BigInteger("3")),
                new Day5.InputMap(new BigInteger("0"), new BigInteger("7"))));
    }

    @Test
    public void testFrontOverlapExactScenario() {
        List<Day5.InputMap> results = Day5.translateMapToNextMaps(
                new Day5.InputMap(new BigInteger("10"), new BigInteger("10")),
                List.of(new Day5.TranslationMap(new BigInteger("13"), new BigInteger("0"), new BigInteger("7")),
                        new Day5.TranslationMap(new BigInteger("50"), new BigInteger("0"), new BigInteger("5")))
        );

        assertInputMap(results, List.of(new Day5.InputMap(new BigInteger("10"), new BigInteger("3")),
                new Day5.InputMap(new BigInteger("0"), new BigInteger("7"))));
    }

    @Test
    public void testEndOverlapScenario() {
        List<Day5.InputMap> results = Day5.translateMapToNextMaps(
                new Day5.InputMap(new BigInteger("10"), new BigInteger("10")),
                List.of(new Day5.TranslationMap(new BigInteger("3"), new BigInteger("0"), new BigInteger("10")),
                        new Day5.TranslationMap(new BigInteger("50"), new BigInteger("0"), new BigInteger("5")))
        );

        assertInputMap(results, List.of(new Day5.InputMap(new BigInteger("7"), new BigInteger("3")),
                new Day5.InputMap(new BigInteger("13"), new BigInteger("7"))));
    }

    @Test
    public void testEndOverlapExactScenario() {
        List<Day5.InputMap> results = Day5.translateMapToNextMaps(
                new Day5.InputMap(new BigInteger("3"), new BigInteger("15")),
                List.of(new Day5.TranslationMap(new BigInteger("3"), new BigInteger("0"), new BigInteger("10")),
                        new Day5.TranslationMap(new BigInteger("50"), new BigInteger("0"), new BigInteger("5")))
        );

        assertInputMap(results, List.of(new Day5.InputMap(new BigInteger("0"), new BigInteger("10"))));
    }

    @Test
    public void testWithinPlusEndOverlapScenario() {
        List<Day5.InputMap> results = Day5.translateMapToNextMaps(
                new Day5.InputMap(new BigInteger("11"), new BigInteger("16")),
                List.of(new Day5.TranslationMap(new BigInteger("11"), new BigInteger("0"), new BigInteger("11")),
                        new Day5.TranslationMap(new BigInteger("50"), new BigInteger("0"), new BigInteger("5")))
        );

        assertInputMap(results, List.of(new Day5.InputMap(new BigInteger("0"), new BigInteger("11"))));
    }

    @Test
    public void testWithinPlusFrontOverlapScenario() {
        List<Day5.InputMap> results = Day5.translateMapToNextMaps(
                new Day5.InputMap(new BigInteger("6"), new BigInteger("21")),
                List.of(new Day5.TranslationMap(new BigInteger("11"), new BigInteger("0"), new BigInteger("16")),
                        new Day5.TranslationMap(new BigInteger("50"), new BigInteger("0"), new BigInteger("5")))
        );

        assertInputMap(results, List.of(new Day5.InputMap(new BigInteger("6"), new BigInteger("5")),
                new Day5.InputMap(new BigInteger("0"), new BigInteger("16"))));
    }

    @Test
    public void testWithinExactOverlapScenario() {
        List<Day5.InputMap> results = Day5.translateMapToNextMaps(
                new Day5.InputMap(new BigInteger("11"), new BigInteger("16")),
                List.of(new Day5.TranslationMap(new BigInteger("11"), new BigInteger("0"), new BigInteger("16")),
                        new Day5.TranslationMap(new BigInteger("50"), new BigInteger("0"), new BigInteger("5")))
        );

        assertInputMap(results, List.of(new Day5.InputMap(new BigInteger("0"), new BigInteger("16"))));
    }

    private void assertInputMap(List<Day5.InputMap> results, List<Day5.InputMap> inputMaps) {
        Assertions.assertEquals(inputMaps.size(), results.size());

        for (int i = 0; i < inputMaps.size(); i++) {
            Assertions.assertEquals(inputMaps.get(i).input().compareTo(results.get(i).input()), 0);
            Assertions.assertEquals(inputMaps.get(i).range().compareTo(results.get(i).range()), 0);

        }
    }
}
