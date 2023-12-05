package adventofcode2023.solvers;

import adventofcode2023.fileloaders.FileLoaders;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Day5 {

    public static String Day5_Puzzle1() {
        List<String> seedsInput = FileLoaders.loadInputIntoStringList("Day5_1_seeds.txt");

        List<BigInteger> seeds = Arrays.stream(seedsInput.get(0).split(" ")).map(BigInteger::new).toList();

        List<TranslationMap> seedSoilMaps = getTranslationMaps("Day5_1_seed-soil-map.txt");
        List<TranslationMap> soilFertilizerMaps = getTranslationMaps("Day5_1_soil-fertilizer-map.txt");
        List<TranslationMap> fertilizerWaterMaps = getTranslationMaps("Day5_1_fertilizer-water-map.txt");
        List<TranslationMap> waterLightMaps = getTranslationMaps("Day5_1_water-light-map.txt");
        List<TranslationMap> lightTemperatureMaps = getTranslationMaps("Day5_1_light-temperature-map.txt");
        List<TranslationMap> temperatureHumidityMaps = getTranslationMaps("Day5_1_temperature-humidity-map.txt");
        List<TranslationMap> humidityLocationMaps = getTranslationMaps("Day5_1_humidity-location-map.txt");

        return String.valueOf(seeds.stream()
                .map(seed -> translateToNextMap(seed, seedSoilMaps))
                .map(soil -> translateToNextMap(soil, soilFertilizerMaps))
                .map(fertilizer -> translateToNextMap(fertilizer, fertilizerWaterMaps))
                .map(water -> translateToNextMap(water, waterLightMaps))
                .map(light -> translateToNextMap(light, lightTemperatureMaps))
                .map(temperature -> translateToNextMap(temperature, temperatureHumidityMaps))
                .map(humidity -> translateToNextMap(humidity, humidityLocationMaps))
                .sorted()
                .findFirst()
                .get());

    }

    private static BigInteger translateToNextMap(BigInteger input, List<TranslationMap> translationMaps) {
        return translationMaps.stream().map(translationMap -> {
                    if (input.compareTo(translationMap.input()) >= 0 && input.compareTo(translationMap.input().add(translationMap.range())) <= 0) {
                        return translationMap.output().add(input.subtract(translationMap.input()));
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(input);
    }

    private static List<TranslationMap> getTranslationMaps(String inputFile) {
        List<String> lines = FileLoaders.loadInputIntoStringList(inputFile);

        return lines.stream().map(line -> {
            List<BigInteger> parsedLine = Arrays.stream(line.split(" ")).map(BigInteger::new).toList();
            return new TranslationMap(parsedLine.get(1), parsedLine.get(0), parsedLine.get(2));
        }).toList();
    }

    public static String Day5_Puzzle2() {
        List<String> seedsInput = FileLoaders.loadInputIntoStringList("Day5_2_seeds.txt");

        List<SeedMap> seedMaps = Arrays.stream(seedsInput.get(0).split(" ")).map(line -> {
            List<BigInteger> parsedLine = Arrays.stream(line.split(" ")).map(BigInteger::new).toList();
            return new SeedMap(parsedLine.get(0), parsedLine.get(1));
        }).toList();

        List<TranslationMap> seedSoilMaps = getTranslationMaps("Day5_1_seed-soil-map.txt");
        List<TranslationMap> soilFertilizerMaps = getTranslationMaps("Day5_1_soil-fertilizer-map.txt");
        List<TranslationMap> fertilizerWaterMaps = getTranslationMaps("Day5_1_fertilizer-water-map.txt");
        List<TranslationMap> waterLightMaps = getTranslationMaps("Day5_1_water-light-map.txt");
        List<TranslationMap> lightTemperatureMaps = getTranslationMaps("Day5_1_light-temperature-map.txt");
        List<TranslationMap> temperatureHumidityMaps = getTranslationMaps("Day5_1_temperature-humidity-map.txt");
        List<TranslationMap> humidityLocationMaps = getTranslationMaps("Day5_1_humidity-location-map.txt");

        return String.valueOf(seedMaps.stream()
                .map(seedMap -> {

                })
                .map(seed -> translateToNextMap(seed, seedSoilMaps))
                .map(soil -> translateToNextMap(soil, soilFertilizerMaps))
                .map(fertilizer -> translateToNextMap(fertilizer, fertilizerWaterMaps))
                .map(water -> translateToNextMap(water, waterLightMaps))
                .map(light -> translateToNextMap(light, lightTemperatureMaps))
                .map(temperature -> translateToNextMap(temperature, temperatureHumidityMaps))
                .map(humidity -> translateToNextMap(humidity, humidityLocationMaps))
                .sorted()
                .findFirst()
                .get());

    }

    public record TranslationMap(BigInteger input, BigInteger output, BigInteger range) {
    }

    public record SeedMap(BigInteger seed, BigInteger range) {
    }

}
