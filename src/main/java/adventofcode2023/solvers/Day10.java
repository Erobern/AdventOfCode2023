package adventofcode2023.solvers;

import adventofcode2023.fileloaders.FileLoaders;

import java.util.*;
import java.util.stream.IntStream;

public class Day10 {

    private static final Map<Pipe, List<Direction>> pipeDirectionMap = new HashMap<>();

    static {
        pipeDirectionMap.put(Pipe.BAR, List.of(Direction.NORTH, Direction.SOUTH));
        pipeDirectionMap.put(Pipe.DASH, List.of(Direction.EAST, Direction.WEST));
        pipeDirectionMap.put(Pipe.L, List.of(Direction.NORTH, Direction.WEST));
        pipeDirectionMap.put(Pipe.J, List.of(Direction.NORTH, Direction.WEST));
        pipeDirectionMap.put(Pipe.SEVEN, List.of(Direction.SOUTH, Direction.WEST));
        pipeDirectionMap.put(Pipe.F, List.of(Direction.SOUTH, Direction.EAST));
    }

    public static String Puzzle1(String input) {
        List<String> lines = FileLoaders.loadInputIntoStringList(input);
        Node[][] nodes = new Node[140][140];

        IntStream.range(0, 140)
                .forEach(idxX -> {
                    List<String> row = Arrays.stream(lines.get(idxX).split("")).toList();
                    IntStream.range(0, 140)
                            .forEach(idxY -> {
                                nodes[idxX][idxY] = new Node(Pipe.get(row.get(idxY)), false, 0);
                            });
                });

        int sX = 0;
        int sY = 0;

        for (int i = 0; i < 140; i++) {
            Node[] jNodes = nodes[i];
            for (int j = 0; j < 140; j++) {
                if (jNodes[j].pipe().equals(Pipe.S)) {
                    sX = i;
                    sY = j;
                    break;
                }
            }
        }

        traverseNode(nodes, sX, sY, 0);
        Node sNode = nodes[sX][sY];

        List<CurrentNode> currentNodes = List.of(new CurrentNode(sNode, sX, sY));

        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            List<CurrentNode> newCurrentNodes = new ArrayList<>();
            int finalI = i;
            currentNodes.stream().forEach(currentNode -> {
                if (currentNode.x - 1 >= 0 && !nodes[currentNode.x - 1][currentNode.y].traversed() &&
                        doNodesConnect(currentNode.node, nodes[currentNode.x - 1][currentNode.y], Direction.NORTH)) {
                    traverseNode(nodes, currentNode.x - 1, currentNode.y, finalI);
                    newCurrentNodes.add(new CurrentNode(nodes[currentNode.x - 1][currentNode.y], currentNode.x - 1, currentNode.y));
                }

                if (currentNode.x + 1 < 140 && !nodes[currentNode.x + 1][currentNode.y].traversed() &&
                        doNodesConnect(currentNode.node, nodes[currentNode.x + 1][currentNode.y], Direction.SOUTH)) {
                    traverseNode(nodes, currentNode.x + 1, currentNode.y, finalI);
                    newCurrentNodes.add(new CurrentNode(nodes[currentNode.x + 1][currentNode.y], currentNode.x + 1, currentNode.y));
                }

                if (currentNode.y - 1 >= 0 && !nodes[currentNode.x][currentNode.y - 1].traversed() &&
                        doNodesConnect(currentNode.node, nodes[currentNode.x][currentNode.y - 1], Direction.WEST)) {
                    traverseNode(nodes, currentNode.x, currentNode.y - 1, finalI);
                    newCurrentNodes.add(new CurrentNode(nodes[currentNode.x][currentNode.y - 1], currentNode.x, currentNode.y - 1));
                }

                if (currentNode.y + 1 < 140 && !nodes[currentNode.x][currentNode.y + 1].traversed() &&
                        doNodesConnect(currentNode.node, nodes[currentNode.x][currentNode.y + 1], Direction.EAST)) {
                    traverseNode(nodes, currentNode.x, currentNode.y + 1, finalI);
                    newCurrentNodes.add(new CurrentNode(nodes[currentNode.x][currentNode.y + 1], currentNode.x, currentNode.y + 1));
                }
            });

            if (newCurrentNodes.isEmpty()) {
                break;
            }
            currentNodes = newCurrentNodes;
        }

        int maxDistance = 0;
        for (int i = 0; i < 140; i++) {
            for (int j = 0; j < 140; j++) {
                if (nodes[i][j].distanceFromS() > maxDistance) {
                    maxDistance = nodes[i][j].distanceFromS();
                }
            }
        }


        return String.valueOf(maxDistance);
    }

    private static void traverseNode(Node[][] nodes, int x, int y, int i) {
        nodes[x][y] = new Node(nodes[x][y].pipe(), true, i);
    }

    public static String Puzzle2(String input) {
        List<String> lines = FileLoaders.loadInputIntoStringList(input);
        Node[][] nodes = new Node[140][140];

        IntStream.range(0, 140)
                .forEach(idxX -> {
                    List<String> row = Arrays.stream(lines.get(idxX).split("")).toList();
                    IntStream.range(0, 140)
                            .forEach(idxY -> {
                                nodes[idxX][idxY] = new Node(Pipe.get(row.get(idxY)), false, 0);
                            });
                });

        int sX = 0;
        int sY = 0;

        for (int i = 0; i < 140; i++) {
            Node[] jNodes = nodes[i];
            for (int j = 0; j < 140; j++) {
                if (jNodes[j].pipe().equals(Pipe.S)) {
                    sX = i;
                    sY = j;
                    break;
                }
            }
        }

        traverseNode(nodes, sX, sY, 0);
        Node sNode = nodes[sX][sY];

        List<CurrentNode> currentNodes = List.of(new CurrentNode(sNode, sX, sY));

        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            List<CurrentNode> newCurrentNodes = new ArrayList<>();
            int finalI = i;
            currentNodes.stream().forEach(currentNode -> {
                if (currentNode.x - 1 >= 0 && !nodes[currentNode.x - 1][currentNode.y].traversed() &&
                        doNodesConnect(currentNode.node, nodes[currentNode.x - 1][currentNode.y], Direction.NORTH)) {
                    traverseNode(nodes, currentNode.x - 1, currentNode.y, finalI);
                    newCurrentNodes.add(new CurrentNode(nodes[currentNode.x - 1][currentNode.y], currentNode.x - 1, currentNode.y));
                }

                if (currentNode.x + 1 < 140 && !nodes[currentNode.x + 1][currentNode.y].traversed() &&
                        doNodesConnect(currentNode.node, nodes[currentNode.x + 1][currentNode.y], Direction.SOUTH)) {
                    traverseNode(nodes, currentNode.x + 1, currentNode.y, finalI);
                    newCurrentNodes.add(new CurrentNode(nodes[currentNode.x + 1][currentNode.y], currentNode.x + 1, currentNode.y));
                }

                if (currentNode.y - 1 >= 0 && !nodes[currentNode.x][currentNode.y - 1].traversed() &&
                        doNodesConnect(currentNode.node, nodes[currentNode.x][currentNode.y - 1], Direction.WEST)) {
                    traverseNode(nodes, currentNode.x, currentNode.y - 1, finalI);
                    newCurrentNodes.add(new CurrentNode(nodes[currentNode.x][currentNode.y - 1], currentNode.x, currentNode.y - 1));
                }

                if (currentNode.y + 1 < 140 && !nodes[currentNode.x][currentNode.y + 1].traversed() &&
                        doNodesConnect(currentNode.node, nodes[currentNode.x][currentNode.y + 1], Direction.EAST)) {
                    traverseNode(nodes, currentNode.x, currentNode.y + 1, finalI);
                    newCurrentNodes.add(new CurrentNode(nodes[currentNode.x][currentNode.y + 1], currentNode.x, currentNode.y + 1));
                }
            });

            if (i == 1) {
                currentNodes = Collections.singletonList(newCurrentNodes.stream().findFirst().get());
            } else {
                if (newCurrentNodes.isEmpty()) {
                    break;
                }
                currentNodes = newCurrentNodes;
            }
        }

        // expand the node list (put a gap between each)
        Node[][] expandedNodes = new Node[279][279];

        for (int i = 0; i < 279; i++) {
            for (int j = 0; j < 279; j++) {
                if (i % 2 == 0 && j % 2 == 0) {
                    expandedNodes[i][j] = nodes[i / 2][j / 2];
                } else {
                    expandedNodes[i][j] = new Node(Pipe.DOT, false, 0);
                }
            }
        }

        // carry connections forward
        for (int i = 0; i < 279; i++) {
            for (int j = 0; j < 279; j++) {
                if (i % 2 == 1 && i - 1 >= 0 && i + 1 < 279 && northSouthConnected(expandedNodes, i, j)) {
                    expandedNodes[i][j] = new Node(Pipe.BAR, true, Integer.MAX_VALUE);
                } else if (j % 2 == 1 && j - 1 >= 0 && j + 1 < 279 && eastWestConnected(expandedNodes, i, j)) {
                    expandedNodes[i][j] = new Node(Pipe.DASH, true, Integer.MAX_VALUE);
                }
            }
        }

        // add all nontraversed outer edge nodes
        currentNodes = new ArrayList<>();
        for (int i = 0; i < 279; i++) {
            for (int j = 0; j < 279; j++) {
                if (!expandedNodes[i][j].traversed() && (i == 0 || i == 278)) {
                    expandedNodes[i][j] = new Node(expandedNodes[i][j].pipe(), true, Integer.MAX_VALUE - 1);
                    currentNodes.add(new CurrentNode(new Node(expandedNodes[i][j].pipe(), true, Integer.MAX_VALUE - 1), i, j));
                } else if (!expandedNodes[i][j].traversed() && (j == 0 || j == 278)) {
                    expandedNodes[i][j] = new Node(expandedNodes[i][j].pipe(), true, Integer.MAX_VALUE - 1);
                    currentNodes.add(new CurrentNode(new Node(expandedNodes[i][j].pipe(), true, Integer.MAX_VALUE - 1), i, j));
                }
            }
        }

        //traverse the whole grid from edge nodes in
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            List<CurrentNode> newCurrentNodes = new ArrayList<>();
            currentNodes.stream().forEach(currentNode -> {
                if (currentNode.x - 1 >= 0 && !expandedNodes[currentNode.x - 1][currentNode.y].traversed()) {
                    traverseNodeP2(expandedNodes, currentNode.x - 1, currentNode.y);
                    newCurrentNodes.add(new CurrentNode(expandedNodes[currentNode.x - 1][currentNode.y], currentNode.x - 1, currentNode.y));
                }

                if (currentNode.x + 1 < 279 && !expandedNodes[currentNode.x + 1][currentNode.y].traversed()) {
                    traverseNodeP2(expandedNodes, currentNode.x + 1, currentNode.y);
                    newCurrentNodes.add(new CurrentNode(expandedNodes[currentNode.x + 1][currentNode.y], currentNode.x + 1, currentNode.y));
                }

                if (currentNode.y - 1 >= 0 && !expandedNodes[currentNode.x][currentNode.y - 1].traversed()) {
                    traverseNodeP2(expandedNodes, currentNode.x, currentNode.y - 1);
                    newCurrentNodes.add(new CurrentNode(expandedNodes[currentNode.x][currentNode.y - 1], currentNode.x, currentNode.y - 1));
                }

                if (currentNode.y + 1 < 279 && !expandedNodes[currentNode.x][currentNode.y + 1].traversed()) {
                    traverseNodeP2(expandedNodes, currentNode.x, currentNode.y + 1);
                    newCurrentNodes.add(new CurrentNode(expandedNodes[currentNode.x][currentNode.y + 1], currentNode.x, currentNode.y + 1));
                }
            });

            if (newCurrentNodes.isEmpty()) {
                break;
            }
            currentNodes = newCurrentNodes;

        }

        Node[][] collapsedNodes = new Node[140][140];

        // collapse the expansion back down to a 140x140 grid
        for (int i = 0; i < 279; i++) {
            for (int j = 0; j < 279; j++) {
                if (i % 2 == 0 && j % 2 == 0) {
                    collapsedNodes[i / 2][j / 2] = expandedNodes[i][j];
                }
            }
        }

        int enclosedNodes = 0;

        for (int i = 0; i < 140; i++) {
            for (int j = 0; j < 140; j++) {
                if (!collapsedNodes[i][j].traversed()) {
                    enclosedNodes++;
                }
            }
        }

        return String.valueOf(enclosedNodes);
    }

    private static void traverseNodeP2(Node[][] nodes, int x, int y) {
        nodes[x][y] = new Node(nodes[x][y].pipe(), true, Integer.MAX_VALUE - 1);
    }

    private static boolean northSouthConnected(Node[][] expandedNodes, int i, int j) {
        return expandedNodes[i - 1][j].traversed() && expandedNodes[i + 1][j].traversed() &&
                List.of(Pipe.S, Pipe.BAR, Pipe.F, Pipe.SEVEN).contains(expandedNodes[i - 1][j].pipe()) &&
                List.of(Pipe.S, Pipe.BAR, Pipe.J, Pipe.L).contains(expandedNodes[i + 1][j].pipe());
    }

    private static boolean eastWestConnected(Node[][] expandedNodes, int i, int j) {
        return expandedNodes[i][j - 1].traversed() && expandedNodes[i][j + 1].traversed() &&
                List.of(Pipe.S, Pipe.DASH, Pipe.F, Pipe.L).contains(expandedNodes[i][j - 1].pipe()) &&
                List.of(Pipe.S, Pipe.DASH, Pipe.SEVEN, Pipe.J).contains(expandedNodes[i][j + 1].pipe());
    }

    private static boolean doNodesConnect(Node node1, Node node2, Direction node2relativeDirection) {
        if (node2.pipe().equals(Pipe.DOT)) {
            return false;
        }

        switch (node2relativeDirection) {
            case NORTH:
                if (!List.of(Pipe.BAR, Pipe.L, Pipe.J, Pipe.S).contains(node1.pipe())) {
                    return false;
                }
                return List.of(Pipe.BAR, Pipe.SEVEN, Pipe.F).contains(node2.pipe());
            case SOUTH:
                if (!List.of(Pipe.BAR, Pipe.F, Pipe.SEVEN, Pipe.S).contains(node1.pipe())) {
                    return false;
                }
                return List.of(Pipe.BAR, Pipe.L, Pipe.J).contains(node2.pipe());
            case EAST:
                if (!List.of(Pipe.DASH, Pipe.F, Pipe.L, Pipe.S).contains(node1.pipe())) {
                    return false;
                }
                return List.of(Pipe.DASH, Pipe.SEVEN, Pipe.J).contains(node2.pipe());
            case WEST:
                if (!List.of(Pipe.DASH, Pipe.J, Pipe.SEVEN, Pipe.S).contains(node1.pipe())) {
                    return false;
                }
                return List.of(Pipe.DASH, Pipe.L, Pipe.F).contains(node2.pipe());
            default:
                return false;
        }

    }

    private enum Direction {
        NORTH,
        SOUTH,
        WEST,
        EAST
    }

    private enum Pipe {
        BAR("|"),
        DASH("-"),
        L("L"),
        J("J"),
        SEVEN("7"),
        F("F"),
        DOT("."),
        S("S");

        private final String symbol;

        Pipe(String s) {
            this.symbol = s;
        }

        public static Pipe get(String s) {
            return Arrays.stream(Pipe.values())
                    .filter(e -> e.symbol.equals(s))
                    .findFirst()
                    .get();
        }
    }

    public record Node(Pipe pipe, boolean traversed, int distanceFromS) {
    }

    public record CurrentNode(Node node, int x, int y) {
    }

}
