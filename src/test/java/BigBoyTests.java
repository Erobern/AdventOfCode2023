import adventofcode2023.solvers.Day5;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BigBoyTests {

    @Test
    public void BigBoyD5P1() {
        Assertions.assertEquals("11056616", Day5.Puzzle1("bigboyinputs/bigboy_seeds_p1.txt",
                "bigboyinputs/bigboy_seed-soil-map.txt",
                "bigboyinputs/bigboy_soil-fertilizer-map.txt",
                "bigboyinputs/bigboy_fertilizer-water-map.txt",
                "bigboyinputs/bigboy_water-light-map.txt",
                "bigboyinputs/bigboy_light-temperature-map.txt",
                "bigboyinputs/bigboy_temperature-humidity-map.txt",
                "bigboyinputs/bigboy_humidity-location-map.txt"), "Day 5, Puzzle 1");
    }

    @Test
    public void BigBoyD5P2() {
        Assertions.assertEquals("100062714", Day5.Puzzle2("bigboyinputs/bigboy_seeds_p2.txt",
                "bigboyinputs/bigboy_seed-soil-map.txt",
                "bigboyinputs/bigboy_soil-fertilizer-map.txt",
                "bigboyinputs/bigboy_fertilizer-water-map.txt",
                "bigboyinputs/bigboy_water-light-map.txt",
                "bigboyinputs/bigboy_light-temperature-map.txt",
                "bigboyinputs/bigboy_temperature-humidity-map.txt",
                "bigboyinputs/bigboy_humidity-location-map.txt"), "Day 5, Puzzle 2");
    }
}
