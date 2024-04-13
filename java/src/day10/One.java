package src.day10;

import src.utils.FileReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

import static src.day10.One.DIRECTIONS.*;

public class One {
    private static final Logger LOGGER;

    public enum DIRECTIONS {UP, DOWN, RIGHT, LEFT};

    public static Map<String, List<String>> validTileDirectionMap = Map.of(
            UP.name(), List.of("|", "7", "F", "S"),
            DOWN.name(), List.of("|", "L", "J", "S"),
            RIGHT.name(), List.of("-", "J", "7", "S"),
            LEFT.name(), List.of("-", "L", "F", "S")
    );

    public static Map<String, List<String>> tileToDirectionMap = Map.of(
            "|", List.of(UP.name(), DOWN.name()),
            "-", List.of(LEFT.name(), RIGHT.name()),
            "L", List.of(UP.name(), RIGHT.name()),
            "J", List.of(UP.name(), LEFT.name()),
            "7", List.of(DOWN.name(), LEFT.name()),
            "F", List.of(DOWN.name(), RIGHT.name()),
            "S", List.of(UP.name(), DOWN.name(), LEFT.name(), RIGHT.name())
    );

    public static Map<String, String> oppositeDirectionMap = Map.of(
            UP.name(), DOWN.name(),
            DOWN.name(), UP.name(),
            RIGHT.name(), LEFT.name(),
            LEFT.name(), RIGHT.name(),
            "", ""
    );

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        LOGGER = Logger.getLogger(One.class.getName());
    }

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
//        String fileName = "/day10/test1.txt";
//        String fileName = "/day10/test2.txt";
        String fileName = "/day10/input.txt";

        BufferedReader bufferedReader = FileReaderUtil.getInputFile(fileName);
        String line;

        List<String> tileList = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null) {
            tileList.add(line);
        }
        int answer = calculateAnswer(tileList);
        LOGGER.info("Final answer: " + answer);
        LOGGER.info("Total time taken: " + (System.currentTimeMillis() - startTime));
    }

    public static int calculateAnswer(List<String> tileList) {
        String[][] tileArr = new String[tileList.size()][tileList.size()];
        int startI = 0;
        int startJ = 0;

        for (int i = 0; i < tileList.size(); i++) {
            String[] tiles = tileList.get(i).split("");
            for (int j = 0; j < tiles.length; j++) {
                tileArr[i][j] = tiles[j];
                if (tileArr[i][j].equals("S")) {
                    startI = i;
                    startJ = j;
                }
            }
        }
        Integer[] startCoordinates = {startI, startJ};
        Stack<Integer[]> visitedTiles = new Stack<>();
        visitedTiles.push(startCoordinates);
        boolean[] isLoopCompleted = new boolean[]{false};
            traverseTiles2(tileArr, startCoordinates, visitedTiles, "", isLoopCompleted);
        return (visitedTiles.size() - 1) / 2;
    }

    public static void traverseTiles2(String[][] tileArr, Integer[] startCoordinates, Stack<Integer[]> visitedTiles, String prevDirection, boolean[] count) {

        if (startCoordinates[0] < 0 || startCoordinates[1] < 0) {
            visitedTiles.pop();
            return;
        }

        if (startCoordinates[0] >= tileArr.length || startCoordinates[1] >= tileArr.length) {
            visitedTiles.pop();
            return;
        }

        String currentTile = tileArr[startCoordinates[0]][startCoordinates[1]];
//        System.out.println("Current tile: " + currentTile + " at position: " + Arrays.asList(startCoordinates));
//        LOGGER.info("Current tile: " + currentTile + " at position: " + Arrays.asList(startCoordinates));

        if ("S".equals(currentTile) && visitedTiles.size() > 1) {
            count[0] = true;
            return;
        }

        if (currentTile.equals(".")) {
            visitedTiles.pop();
            return;
        }

        List<String> tileDirections = new ArrayList<>(validTileDirectionMap.getOrDefault(prevDirection, new ArrayList<>()));
        if (!prevDirection.isBlank() && !tileDirections.contains(currentTile)) {
            visitedTiles.pop();
            return;
        }

//        List<String> directions = Arrays.stream(values()).map(Enum::name).filter(d -> !oppositeDirectionMap.get(prevDirection).equals(d)).collect(Collectors.toList());
        List<String> directions = new ArrayList<>();
        for (var value: values()) {
            String name = value.name();
            if (!oppositeDirectionMap.get(prevDirection).equals(name)) {
                directions.add(name);
            }
        }
        List<String> directionByTile = new ArrayList<>(tileToDirectionMap.getOrDefault(currentTile, new ArrayList<>()));
        directions.retainAll(directionByTile);

        for (String direction : directions) {
            if (count[0])
                break;
            Integer[] nextPosition = getNextPosition(direction, startCoordinates);
            visitedTiles.push(nextPosition);
            traverseTiles2(tileArr, nextPosition, visitedTiles, direction, count);
        }
    }

    public static Integer[] getNextPosition(String direction, Integer[] currentPosition) {
        Integer[] nextPosition = new Integer[]{-1, -1};

        if (UP.name().equals(direction)) {
            return new Integer[]{currentPosition[0] - 1, currentPosition[1]};
        }
        if (DOWN.name().equals(direction)) {
            return new Integer[]{currentPosition[0] + 1, currentPosition[1]};
        }
        if (LEFT.name().equals(direction)) {
            return new Integer[]{currentPosition[0], currentPosition[1] - 1};
        }
        if (RIGHT.name().equals(direction)) {
            return new Integer[]{currentPosition[0], currentPosition[1] + 1};
        }
        return nextPosition;
    }
}
