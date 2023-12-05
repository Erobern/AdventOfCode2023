package adventofcode2023.solvers;

import adventofcode2023.fileloaders.FileLoaders;

import java.math.BigInteger;
import java.util.*;

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

        List<InputMap> seedMaps = seedsInput.stream().map(line -> {
            List<BigInteger> parsedLine = Arrays.stream(line.split(" ")).map(BigInteger::new).toList();
            return new InputMap(parsedLine.get(0), parsedLine.get(1));
        }).toList();

        List<TranslationMap> seedSoilMaps = getTranslationMaps("Day5_1_seed-soil-map.txt");
        List<TranslationMap> soilFertilizerMaps = getTranslationMaps("Day5_1_soil-fertilizer-map.txt");
        List<TranslationMap> fertilizerWaterMaps = getTranslationMaps("Day5_1_fertilizer-water-map.txt");
        List<TranslationMap> waterLightMaps = getTranslationMaps("Day5_1_water-light-map.txt");
        List<TranslationMap> lightTemperatureMaps = getTranslationMaps("Day5_1_light-temperature-map.txt");
        List<TranslationMap> temperatureHumidityMaps = getTranslationMaps("Day5_1_temperature-humidity-map.txt");
        List<TranslationMap> humidityLocationMaps = getTranslationMaps("Day5_1_humidity-location-map.txt");

        return String.valueOf(seedMaps.stream()
                .map(seedMap -> translateMapToNextMaps(seedMap, seedSoilMaps)).flatMap(List::stream)
                .map(soilMap -> translateMapToNextMaps(soilMap, soilFertilizerMaps)).flatMap(List::stream)
                .map(fertilizerMap -> translateMapToNextMaps(fertilizerMap, fertilizerWaterMaps)).flatMap(List::stream)
                .map(waterMap -> translateMapToNextMaps(waterMap, waterLightMaps)).flatMap(List::stream)
                .map(lightMap -> translateMapToNextMaps(lightMap, lightTemperatureMaps)).flatMap(List::stream)
                .map(temperatureMap -> translateMapToNextMaps(temperatureMap, temperatureHumidityMaps)).flatMap(List::stream)
                .map(humidityMap -> translateMapToNextMaps(humidityMap, humidityLocationMaps)).flatMap(List::stream)
                .sorted(Comparator.comparing(InputMap::input))
                .map(InputMap::input)
                .findFirst()
                .get());

    }

    public static List<InputMap> translateMapToNextMaps(InputMap inputMap, List<TranslationMap> translationMaps) {

        List<TranslationMap> validMaps = new ArrayList<>(translationMaps
                .stream()
                .filter(translationMap -> !(inputMap.input().compareTo(translationMap.input().add(translationMap.range())) > 0 ||
                        inputMap.input().add(inputMap.range()).compareTo(translationMap.input()) < 0))
                .sorted(Comparator.comparing(TranslationMap::input))
                .toList());

        if (validMaps.isEmpty()) {
            return List.of(inputMap);
        }
        BigInteger remainingInputStart = inputMap.input();
        BigInteger remainingOffset = inputMap.range();
        List<InputMap> outputMaps = new ArrayList<>();

        while (remainingOffset.compareTo(BigInteger.ZERO) > 0) {
            // check end scenarios
            if (validMaps.isEmpty() && remainingOffset.compareTo(BigInteger.ZERO) == 0) {
                return outputMaps;
            } else if (validMaps.isEmpty() && remainingOffset.compareTo(BigInteger.ZERO) != 0) {
                outputMaps.add(new InputMap(remainingInputStart, remainingOffset));
                return outputMaps;
            }

            TranslationMap currentValidMap = validMaps.get(0);
            // 1. under the current valid map
            if (remainingInputStart.add(remainingOffset).compareTo(currentValidMap.input()) < 0) {
                outputMaps.add(new InputMap(remainingInputStart, remainingOffset));
                return outputMaps;
            }
            // 2. over the current valid map
            else if (remainingInputStart.compareTo(currentValidMap.input().add(currentValidMap.range())) > 0) {
                validMaps.remove(0);
            }
            // 3. overlap the start of the current valid map
            else if (remainingInputStart.compareTo(currentValidMap.input()) < 0 &&
                    remainingInputStart.add(remainingOffset).compareTo(currentValidMap.input().add(currentValidMap.range())) < 0) {
                BigInteger margin = currentValidMap.input().subtract(remainingInputStart);
                outputMaps.add(new InputMap(remainingInputStart, margin));
                remainingInputStart = remainingInputStart.add(margin);
                remainingOffset = remainingOffset.subtract(margin);
                outputMaps.add(new InputMap(currentValidMap.output(), remainingOffset));
                return outputMaps;
            }
            // 4. overlap the whole current valid map
            else if (remainingInputStart.compareTo(currentValidMap.input()) <= 0 &&
                    remainingInputStart.add(remainingOffset).compareTo(currentValidMap.input().add(currentValidMap.range())) >= 0) {
                BigInteger margin = currentValidMap.input().subtract(remainingInputStart);
                BigInteger endMargin = currentValidMap.input().add(currentValidMap.range()).subtract(
                        remainingInputStart.add(remainingOffset));
                if (margin.compareTo(BigInteger.ZERO) == 0 && endMargin.compareTo(BigInteger.ZERO) == 0) {
                    outputMaps.add(new InputMap(currentValidMap.output(), currentValidMap.range()));
                    return outputMaps;
                } else {

                    if (margin.compareTo(BigInteger.ZERO) > 0) {
                        outputMaps.add(new InputMap(remainingInputStart, margin));
                        remainingInputStart = remainingInputStart.add(currentValidMap.range());
                        remainingOffset = remainingOffset.subtract(currentValidMap.range());
                    }

                    outputMaps.add(new InputMap(currentValidMap.output(), currentValidMap.range()));
                    remainingInputStart = remainingInputStart.add(currentValidMap.range());
                    remainingOffset = remainingOffset.subtract(currentValidMap.range());

                    validMaps.remove(0);
                }
            }
            // 5. within the current valid map
            else if (remainingInputStart.compareTo(currentValidMap.input()) >= 0 &&
                    remainingInputStart.add(remainingOffset).compareTo(currentValidMap.input().add(currentValidMap.range())) < 0) {
                BigInteger margin = remainingInputStart.subtract(currentValidMap.input());
                outputMaps.add(new InputMap(currentValidMap.output().add(margin), remainingOffset));
                return outputMaps;
            }
            // 6. overlap the end of the current valid map
            else if (remainingInputStart.compareTo(currentValidMap.input()) > 0 &&
                    remainingInputStart.add(remainingOffset).compareTo(currentValidMap.input().add(currentValidMap.range())) >= 0) {
                BigInteger margin = currentValidMap.input().add(currentValidMap.range()).subtract(remainingInputStart);
                outputMaps.add(new InputMap(currentValidMap.output().add(currentValidMap.range().subtract(margin)), margin));
                remainingInputStart = remainingInputStart.add(margin);
                remainingOffset = remainingOffset.subtract(margin);
                validMaps.remove(0);
            }
        }

        return outputMaps;
    }

    public record TranslationMap(BigInteger input, BigInteger output, BigInteger range) {
    }

    public record InputMap(BigInteger input, BigInteger range) {
    }

}
