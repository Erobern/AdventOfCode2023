package adventofcode2023.solvers;

import adventofcode2023.fileloaders.FileLoaders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Day4 {
    public static String Day4_Puzzle1() {
        List<String> lines = FileLoaders.loadInputIntoStringList("Day4_1.txt");

        AtomicInteger totalPoints = new AtomicInteger();

        List<ScratchTicket> scratchTickets = getScratchTickets(lines);

        scratchTickets.stream()
                .forEach(scratchTicket -> {
                    if (scratchTicket.totalWins() > 0) {
                        totalPoints.addAndGet((int) Math.pow(2, (scratchTicket.totalWins() - 1)));
                    }
                });

        return totalPoints.toString();
    }

    public static String Day4_Puzzle2() {
        List<String> lines = FileLoaders.loadInputIntoStringList("Day4_1.txt");

        List<ScratchTicket> scratchTickets = getScratchTickets(lines);

        for (int i = 0; i < scratchTickets.size(); i++) {
            ScratchTicket scratchTicket = scratchTickets.get(i);
            for (int j = scratchTicket.cardIndex() + 1; j <= scratchTicket.totalWins() + scratchTicket.cardIndex(); j++) {
                scratchTickets.add(scratchTickets.get(j));
            }
        }

        return String.valueOf(scratchTickets.size());
    }

    private static List<ScratchTicket> getScratchTickets(List<String> lines) {
        List<ScratchTicket> scratchTickets = new ArrayList<>();

        lines
                .stream()
                .forEach(line -> {
                    Integer cardIndex = Arrays.stream(Arrays.stream(line.split(":"))
                                    .findFirst()
                                    .get()
                                    .split(" ")).reduce((first, second) -> second)
                            .map(Integer::parseInt)
                            .get() - 1;

                    String gameArea = Arrays.stream(line.split(":"))
                            .reduce((first, second) -> second)
                            .get();

                    List<Integer> winningNumbers =
                            Arrays.stream(Arrays.stream(gameArea.split("x")).findFirst().get()
                                            .split(" "))
                                    .filter(s -> !s.isBlank() && !s.isEmpty())
                                    .map(Integer::parseInt)
                                    .toList();

                    List<Integer> numbersWeHave =
                            Arrays.stream(Arrays.stream(gameArea.split("x")).reduce((first, second) -> second).get()
                                            .split(" "))
                                    .filter(s -> !s.isBlank() && !s.isEmpty())
                                    .map(Integer::parseInt)
                                    .toList();

                    AtomicInteger totalWins = new AtomicInteger();

                    winningNumbers
                            .forEach(winningNumber -> {
                                if (numbersWeHave.contains(winningNumber)) {
                                    totalWins.getAndIncrement();
                                }
                            });

                    scratchTickets.add(new ScratchTicket(cardIndex, winningNumbers, numbersWeHave, totalWins.get()));
                });
        return scratchTickets;
    }

    private record ScratchTicket(Integer cardIndex, List<Integer> winningNumbers, List<Integer> playNumbers,
                                 Integer totalWins) {
    }

}