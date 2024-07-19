package adventofcode2023.solvers;

import adventofcode2023.fileloaders.FileLoaders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Day15 {

    public static String Puzzle1(String input) {
        List<String> lines = FileLoaders.loadInputIntoStringList(input);

        List<String> instructions = Arrays.stream(lines.getFirst().split(",")).toList();

        return instructions.stream().map(Day15::day15Hash).reduce(0, Integer::sum).toString();
    }

    private static Integer day15Hash(String instruction) {
        Integer result = 0;

        for (char c : instruction.toCharArray()) {
            result += (int) c;
            result *= 17;
            result %= 256;

        }

        return result;
    }

    public static String Puzzle2(String input) {
        List<String> lines = FileLoaders.loadInputIntoStringList(input);

        List<String> instructions = Arrays.stream(lines.getFirst().split(",")).toList();

        List<List<Lens>> lensBoxes = new ArrayList<>();

        for (int i = 0; i < 256; i++) {
            lensBoxes.add(new ArrayList<>());
        }

        instructions.forEach(instruction -> {


            if (instruction.contains("=")) {
                int whichBox = day15Hash(instruction.substring(0, instruction.indexOf("=")));
                List<Lens> listToUpdate = lensBoxes.get(whichBox);

                Lens lensToAdd = new Lens(
                        instruction.substring(0, instruction.indexOf("=")),
                        Integer.parseInt(instruction.substring(instruction.indexOf("=") + 1)));
                if (listToUpdate.stream().anyMatch(lens -> lens.label.equals(lensToAdd.label))) {
                    // replace the lens
                    listToUpdate.set(
                            listToUpdate.indexOf(
                                    listToUpdate.stream().filter(lens -> lens.label.equals(lensToAdd.label)).findFirst().get()
                            ),
                            lensToAdd);
                } else {
                    // append the lens
                    listToUpdate.add(lensToAdd);
                }
                lensBoxes.set(whichBox, listToUpdate);
            } else if (instruction.contains("-")) {
                int whichBox = day15Hash(instruction.substring(0, instruction.indexOf("-")));
                List<Lens> listToUpdate = lensBoxes.get(whichBox);

                Lens lensToRemove = new Lens(instruction.substring(0, instruction.indexOf("-")), -1);
                // remove the lens
                listToUpdate.removeIf(lens -> lens.label.equals(lensToRemove.label));
                lensBoxes.set(whichBox, listToUpdate);
            }


        });

        AtomicReference<Integer> result = new AtomicReference<>(0);

        for (int i = 0; i < 256; i++) {
            AtomicReference<Integer> whichBox = new AtomicReference<>(1);
            int finalI = i;
            lensBoxes.get(i).forEach(lens -> {
                result.updateAndGet(v -> v + (finalI + 1) * (whichBox.get()) * lens.focalLength);
                whichBox.getAndSet(whichBox.get() + 1);
            });
        }

        return result.get().toString();
    }

    public record Lens(String label, Integer focalLength) {
    }
}
