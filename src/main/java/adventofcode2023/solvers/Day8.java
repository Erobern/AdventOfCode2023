package adventofcode2023.solvers;

import adventofcode2023.fileloaders.FileLoaders;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day8 {

    public static String Puzzle1(String inputSteps, String inputNodes) {
        List<String> steps = Arrays.stream(FileLoaders.loadInputIntoStringList(inputSteps).get(0).split("")).toList();
        List<Node> nodes = FileLoaders.loadInputIntoStringList(inputNodes)
                .stream().map(line -> {
                    List<String> splitLine = Arrays.stream(line.split("=")).toList();
                    return new Node(splitLine.get(0).trim(),
                            Arrays.stream(splitLine.get(1)
                                    .replace("(", "").replace(")", "")
                                    .split(",")).findFirst().get().trim(),
                            Arrays.stream(splitLine.get(1)
                                    .replace("(", "").replace(")", "")
                                    .split(",")).reduce((first, second) -> second).get().trim(),
                            false);
                }).toList();

        boolean zzzReached = false;
        int stepPointer = 0;
        int stepCounter = 0;
        Node currentNode = nodes.stream().filter(node -> node.key().equals("AAA")).findFirst().get();

        while (!zzzReached) {

            String step = steps.get(stepPointer);
            if (step.equals("L")) {
                Node finalCurrentNode = currentNode;
                currentNode = nodes.stream().filter(node -> node.key().equals(finalCurrentNode.left())).findFirst().get();
            } else {
                Node finalCurrentNode1 = currentNode;
                currentNode = nodes.stream().filter(node -> node.key().equals(finalCurrentNode1.right())).findFirst().get();
            }

            stepCounter++;

            if (currentNode.key().equals("ZZZ")) {
                zzzReached = true;
            } else {
                stepPointer++;
                if (stepPointer == steps.size()) {
                    stepPointer = 0;
                }
            }
        }

        return String.valueOf(stepCounter);
    }

    public static String Puzzle2(String inputSteps, String inputNodes) {
        List<String> steps = Arrays.stream(FileLoaders.loadInputIntoStringList(inputSteps).get(0).split("")).toList();
        List<Node> nodes = FileLoaders.loadInputIntoStringList(inputNodes)
                .stream().map(line -> {
                    List<String> splitLine = Arrays.stream(line.split("=")).toList();
                    return new Node(splitLine.get(0).trim(),
                            Arrays.stream(splitLine.get(1)
                                    .replace("(", "").replace(")", "")
                                    .split(",")).findFirst().get().trim(),
                            Arrays.stream(splitLine.get(1)
                                    .replace("(", "").replace(")", "")
                                    .split(",")).reduce((first, second) -> second).get().trim(),
                            splitLine.get(0).trim().matches("[A-Z]*Z"));
                }).toList();

        Map<String, Node> leftMapNodes = new HashMap<>();
        Map<String, Node> rightMapNodes = new HashMap<>();

        nodes.forEach(node -> {
            leftMapNodes.put(node.left(), nodes.stream().filter(fNode -> fNode.key.equals(node.left())).findFirst().get());
            rightMapNodes.put(node.right(), nodes.stream().filter(fNode -> fNode.key.equals(node.right())).findFirst().get());
        });

        List<Node> currentNodes = nodes.stream().filter(node -> node.key().matches("[A-Z]*A")).toList();
        String solution = "";

        List<BigInteger> countedSteps = currentNodes.stream().map(currentNode -> {
            boolean zzzReached = false;
            int stepPointer = 0;
            int stepCounter = 0;


            while (!zzzReached) {

                String step = steps.get(stepPointer);
                if (step.equals("L")) {
                    currentNode = leftMapNodes.get(currentNode.left());
                } else {
                    currentNode = rightMapNodes.get(currentNode.right());
                }

                stepCounter++;

                if (currentNode.isTrailingZ()) {
                    zzzReached = true;
                } else {
                    stepPointer++;
                    if (stepPointer == steps.size()) {
                        stepPointer = 0;
                    }
                }
            }

            return stepCounter;

        }).map(i -> new BigInteger(i.toString())).toList();

        BigInteger lcm = countedSteps.get(0);
        for (int i = 1; i < countedSteps.size(); i++) {
            lcm = calculateLcm(lcm, countedSteps.get(i));
        }

        // LCM?
        return lcm.toString();
    }

    private static BigInteger calculateLcm(BigInteger first, BigInteger second) {
        BigInteger gcd = first.gcd(second);
        BigInteger absProduct = first.multiply(second).abs();
        return absProduct.divide(gcd);
    }

    // old brute force try - saving for posterity
//    public static String Puzzle2(String inputSteps, String inputNodes) {
//        List<String> steps = Arrays.stream(FileLoaders.loadInputIntoStringList(inputSteps).get(0).split("")).toList();
//        List<Node> nodes = FileLoaders.loadInputIntoStringList(inputNodes)
//                .stream().map(line -> {
//                    List<String> splitLine = Arrays.stream(line.split("=")).toList();
//                    return new Node(splitLine.get(0).trim(),
//                            Arrays.stream(splitLine.get(1)
//                                    .replace("(", "").replace(")", "")
//                                    .split(",")).findFirst().get().trim(),
//                            Arrays.stream(splitLine.get(1)
//                                    .replace("(", "").replace(")", "")
//                                    .split(",")).reduce((first, second) -> second).get().trim(),
//                            splitLine.get(0).trim().matches("[A-Z]*Z"));
//                }).toList();
//
//        boolean zzzReached = false;
//        int stepPointer = 0;
//        int stepCounter = 0;
//        List<Node> currentNodes = nodes.stream().filter(node -> node.key().matches("[A-Z]*A")).toList();
//
//        Map<String, Node> leftMapNodes = new HashMap<>();
//        Map<String, Node> rightMapNodes = new HashMap<>();
//
//        nodes.forEach(node -> {
//            leftMapNodes.put(node.left(), nodes.stream().filter(fNode -> fNode.key.equals(node.left())).findFirst().get());
//            rightMapNodes.put(node.right(), nodes.stream().filter(fNode -> fNode.key.equals(node.right())).findFirst().get());
//        });
//
//        while (!zzzReached) {
//
//            List<Node> newCurrentNodes = new ArrayList<>();
//            String step = steps.get(stepPointer);
//
//            currentNodes.forEach(currentNode -> {
//                if (step.equals("L")) {
//                    newCurrentNodes.add(leftMapNodes.get(currentNode.left()));
//                } else {
//                    newCurrentNodes.add(rightMapNodes.get(currentNode.right()));
//                }
//            });
//
//            stepCounter++;
//
//            if (stepCounter % 100000 == 0) {
//                System.out.println(stepCounter);
//            }
//
//            if (newCurrentNodes.stream().filter(Node::isTrailingZ).count() == currentNodes.size()) {
//                zzzReached = true;
//            } else {
//                stepPointer++;
//                if (stepPointer == steps.size()) {
//                    stepPointer = 0;
//                }
//                currentNodes = newCurrentNodes;
//            }
//        }
//
//        return String.valueOf(stepCounter);
//    }

    public record Node(String key, String left, String right, boolean isTrailingZ) {
    }

}
