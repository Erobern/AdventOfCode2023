package adventofcode2023.solvers;

import adventofcode2023.fileloaders.FileLoaders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static adventofcode2023.solvers.Day16.Direction.*;

public class Day16 {
    public enum Direction {NORTH, EAST, SOUTH, WEST}

    public record Ray(int x, int y, Direction direction) {
    }

    public static String Puzzle1(String input, Optional<Ray> startingRay) {
        List<String> lines = FileLoaders.loadInputIntoStringList(input);

        char[][] laserGrid = new char[110][110];
        char[][] illuminatedGrid = new char[110][110];
        List<String> mirrorSplittersActivated = new ArrayList<>();
        List<Ray> raysLeftToProcess = new ArrayList<>();

        for (int i = 0; i < 110; i++) {
            for (int j = 0; j < 110; j++) {
                laserGrid[i][j] = lines.get(i).charAt(j);
            }
        }

        if (startingRay.isPresent()) {
            raysLeftToProcess.add(startingRay.get());
        } else {
            raysLeftToProcess.add(new Ray(0, 0, EAST));
        }

        // illuminate the grid

        while (!raysLeftToProcess.isEmpty()) {
            Ray currentRay = raysLeftToProcess.removeFirst();

            List<String> rayHistory = new ArrayList<>();

            int x = currentRay.x;
            int y = currentRay.y;
            Direction currentDirection = currentRay.direction;

            if (x < 0 || x >= 110 || y < 0 || y >= 110) {
                continue;
            }

            boolean isRayAlive = true;

            while (isRayAlive) {
                if (x < 0 || x >= 110 || y < 0 || y >= 110) {
                    // ray is out of bounds
                    isRayAlive = false;
                    continue;
                }

                if (rayHistory.contains(x + "-" + y + "-" + currentDirection)) {
                    // we are trapped in a loop
                    isRayAlive = false;
                    continue;
                }

                rayHistory.add(x + "-" + y + "-" + currentDirection);

                // illuminate current square
                illuminatedGrid[x][y] = '#';

                switch (currentDirection) {
                    case EAST:
                        if (laserGrid[x][y] == '.' || laserGrid[x][y] == '-') {
                            y++;
                            continue;
                        }
                        if (laserGrid[x][y] == '/') {
                            currentDirection = NORTH;
                            x--;
                            continue;
                        }
                        if (laserGrid[x][y] == '\\') {
                            currentDirection = SOUTH;
                            x++;
                            continue;
                        }
                        if (laserGrid[x][y] == '|' && !mirrorSplittersActivated.contains(x + "-" + y)) {
                            isRayAlive = false;
                            mirrorSplittersActivated.add(x + "-" + y);
                            raysLeftToProcess.add(new Ray(x - 1, y, NORTH));
                            raysLeftToProcess.add(new Ray(x + 1, y, SOUTH));
                            continue;
                        }
                        break;
                    case WEST:
                        if (laserGrid[x][y] == '.' || laserGrid[x][y] == '-') {
                            y--;
                            continue;
                        }
                        if (laserGrid[x][y] == '/') {
                            currentDirection = SOUTH;
                            x++;
                            continue;
                        }
                        if (laserGrid[x][y] == '\\') {
                            currentDirection = NORTH;
                            x--;
                            continue;
                        }
                        if (laserGrid[x][y] == '|' && !mirrorSplittersActivated.contains(x + "-" + y)) {
                            isRayAlive = false;
                            mirrorSplittersActivated.add(x + "-" + y);
                            raysLeftToProcess.add(new Ray(x - 1, y, NORTH));
                            raysLeftToProcess.add(new Ray(x + 1, y, SOUTH));
                            continue;
                        }
                        break;
                    case NORTH:
                        if (laserGrid[x][y] == '.' || laserGrid[x][y] == '|') {
                            x--;
                            continue;
                        }
                        if (laserGrid[x][y] == '/') {
                            currentDirection = EAST;
                            y++;
                            continue;
                        }
                        if (laserGrid[x][y] == '\\') {
                            currentDirection = WEST;
                            y--;
                            continue;
                        }
                        if (laserGrid[x][y] == '-' && !mirrorSplittersActivated.contains(x + "-" + y)) {
                            isRayAlive = false;
                            mirrorSplittersActivated.add(x + "-" + y);
                            raysLeftToProcess.add(new Ray(x, y - 1, WEST));
                            raysLeftToProcess.add(new Ray(x, y + 1, EAST));
                            continue;
                        }
                        break;
                    case SOUTH:
                        if (laserGrid[x][y] == '.' || laserGrid[x][y] == '|') {
                            x++;
                            continue;
                        }
                        if (laserGrid[x][y] == '/') {
                            currentDirection = WEST;
                            y--;
                            continue;
                        }
                        if (laserGrid[x][y] == '\\') {
                            currentDirection = EAST;
                            y++;
                            continue;
                        }
                        if (laserGrid[x][y] == '-' && !mirrorSplittersActivated.contains(x + "-" + y)) {
                            isRayAlive = false;
                            mirrorSplittersActivated.add(x + "-" + y);
                            raysLeftToProcess.add(new Ray(x, y - 1, WEST));
                            raysLeftToProcess.add(new Ray(x, y + 1, EAST));
                            continue;
                        }
                        break;
                }
            }
        }


        Integer result = 0;

        for (int i = 0; i < 110; i++) {
            for (int j = 0; j < 110; j++) {
                result += illuminatedGrid[i][j] == '#' ? 1 : 0;
            }
        }

        return result.toString();
    }

    public static String Puzzle2(String input) {
        List<String> lines = FileLoaders.loadInputIntoStringList(input);

        Integer maxResult = 0;
        Integer tempResult = 0;

        for (int x = 0; x < 110; x++) {
            tempResult = Integer.parseInt(Puzzle1(input, Optional.of(new Ray(x, 0, EAST))));

            if (tempResult > maxResult) {
                maxResult = tempResult;
            }

            tempResult = Integer.parseInt(Puzzle1(input, Optional.of(new Ray(x, 109, WEST))));

            if (tempResult > maxResult) {
                maxResult = tempResult;
            }
        }

        for (int y = 0; y < 110; y++) {
            tempResult = Integer.parseInt(Puzzle1(input, Optional.of(new Ray(0, y, SOUTH))));

            if (tempResult > maxResult) {
                maxResult = tempResult;
            }

            tempResult = Integer.parseInt(Puzzle1(input, Optional.of(new Ray(109, y, NORTH))));

            if (tempResult > maxResult) {
                maxResult = tempResult;
            }
        }

        return maxResult.toString();
    }
}
