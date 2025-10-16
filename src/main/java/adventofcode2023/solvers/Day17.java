package adventofcode2023.solvers;

import adventofcode2023.fileloaders.FileLoaders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class Day17 {


    public static String Puzzle1(String input) {

        List<String> lines = FileLoaders.loadInputIntoStringList(input);

        Integer[][] grid = new Integer[lines.size()][lines.getFirst().length()];

        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.getFirst().length(); j++) {
                grid[i][j] = Integer.parseInt(String.valueOf(lines.get(i).charAt(j)));
            }
        }

        Coordinate finalCoordinate = new Coordinate(lines.size() - 1, lines.getFirst().length() - 1);

        HashMap<String, Integer> solves = new HashMap<>();

        Set<Node> nodesToEvaluate = new HashSet<>();
        Set<Node> nextNodes = new HashSet<>();

        nextNodes.add(new Node( new Coordinate(0, 1), Direction.RIGHT, 0, ""));
        nextNodes.add(new Node( new Coordinate(1, 0), Direction.DOWN, 0, ""));

        AtomicReference<Integer> bestResult = new AtomicReference<>(Integer.MAX_VALUE);
        AtomicReference<String> bestResultPath = new AtomicReference<>("");

        while (!nextNodes.isEmpty()) {

            nodesToEvaluate.addAll(nextNodes);
            nextNodes.clear();

            nodesToEvaluate.forEach(node -> {

                // if our node is out of bounds we can stop already
                try {
                    Integer test = grid[node.startCoordinate.line()][node.startCoordinate.column()];
                } catch (Exception e) {
                    return;
                }

                // check if we've been here before
                if (solves.get(node.getHashKey()) == null) {
                    solves.put(node.getHashKey(), node.heatLost());
                } else {
                    if (solves.get(node.getHashKey()) <= node.heatLost()) {
                        // we've been here already and found a faster or equal solution
                        return;
                    } else {
                        solves.put(node.getHashKey(), node.heatLost());
                    }
                }

                Integer newHeatLost = node.heatLost();

                // try to populate the next six potential nodes
                try {
                    Coordinate noStep = node.startCoordinate().step(node.startDirection(), 0);
                    newHeatLost += grid[noStep.line()][noStep.column()];

                    if (noStep.line().equals(finalCoordinate.line()) && noStep.column().equals(finalCoordinate.column())) {
                        if (newHeatLost < bestResult.get()) {
                            bestResult.set(newHeatLost);
                            bestResultPath.set(node.history());
                        }
                        return;
                    }

                        nextNodes.add(new Node(noStep.step(node.startDirection().clockwise(), 1), node.startDirection().clockwise(), newHeatLost, node.history + ">" + node.getHashKey()));
                        nextNodes.add(new Node(noStep.step(node.startDirection().counterClockwise(), 1), node.startDirection().counterClockwise(), newHeatLost, node.history + ">" + node.getHashKey()));

                } catch (Exception ignored) { return; }

                try {
                    Coordinate oneStep = node.startCoordinate().step(node.startDirection(), 1);
                    newHeatLost += grid[oneStep.line()][oneStep.column()];

                    if (oneStep.line().equals(finalCoordinate.line()) && oneStep.column().equals(finalCoordinate.column())) {
                        if (newHeatLost < bestResult.get()) {
                            bestResult.set(newHeatLost);
                            bestResultPath.set(node.history());
                        }
                        return;
                    }

                        nextNodes.add(new Node(oneStep.step(node.startDirection().clockwise(), 1), node.startDirection().clockwise(), newHeatLost, node.history + ">" + node.getHashKey()));
                        nextNodes.add(new Node(oneStep.step(node.startDirection().counterClockwise(), 1), node.startDirection().counterClockwise(), newHeatLost, node.history + ">" + node.getHashKey()));
                } catch (Exception ignored) { return; }

                try {
                    Coordinate twoStep = node.startCoordinate().step(node.startDirection(), 2);
                    newHeatLost += grid[twoStep.line()][twoStep.column()];

                    if (twoStep.line().equals(finalCoordinate.line()) && twoStep.column().equals(finalCoordinate.column())) {
                        if (newHeatLost < bestResult.get()) {
                            bestResult.set(newHeatLost);
                            bestResultPath.set(node.history());
                        }
                        return;
                    }

                        nextNodes.add(new Node(twoStep.step(node.startDirection().clockwise(), 1), node.startDirection().clockwise(), newHeatLost, node.history + ">" + node.getHashKey()));
                        nextNodes.add(new Node(twoStep.step(node.startDirection().counterClockwise(), 1), node.startDirection().counterClockwise(), newHeatLost, node.history + ">" + node.getHashKey()));
                } catch (Exception ignored) { return; }

            });

            nodesToEvaluate.clear();

        }

        return bestResult.toString();
    }

    public static String Puzzle2(String input) {
     return null;
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT;

        public Direction clockwise() {
            if (this == UP) {
                return RIGHT;
            }
            if (this == RIGHT) {
                return DOWN;
            }
            if (this == DOWN) {
                return LEFT;
            }
            if (this == LEFT) {
                return UP;
            }
            throw new IllegalStateException();
        }

        public Direction counterClockwise() {
            if (this == UP) {
                return LEFT;
            }
            if (this == RIGHT) {
                return UP;
            }
            if (this == DOWN) {
                return RIGHT;
            }
            if (this == LEFT) {
                return DOWN;
            }
            throw new IllegalStateException();
        }
    }

    public record Coordinate(Integer line, Integer column){
        public Coordinate step(Direction direction, Integer steps) {
            return new Coordinate(
                    direction.equals(Direction.LEFT) || direction.equals(Direction.RIGHT) ? line : (
                            direction.equals(Direction.UP) ? line - steps : line + steps
                            ),
                    direction.equals(Direction.UP) || direction.equals(Direction.DOWN) ? column : (
                            direction.equals(Direction.LEFT) ? column - steps : column + steps
                    )
            );
        }
    }

    public record Node(Coordinate startCoordinate, Direction startDirection, Integer heatLost, String history){
        public String getHashKey() {
            return startCoordinate().line().toString() + ":" + startCoordinate().column().toString() + ":" + startDirection().name();
        }
    }


}

