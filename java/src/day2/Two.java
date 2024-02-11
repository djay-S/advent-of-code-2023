package src.day2;

import src.utils.FileReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static src.day2.One.getGameFromLine;

public class Two {
    public static void main(String[] args) throws IOException {
        String fileName = "/day2/input.txt";
        BufferedReader inputFile = FileReaderUtil.getInputFile(fileName);
        String line;
        int result = 0;
        List<Game> gameList = new ArrayList<>();

        while ((line = inputFile.readLine()) != null) {
            gameList.addAll(getGameFromLine(line));
        }
        Map<Integer, List<Game>> gameMap = gameList.stream().collect(Collectors.groupingBy(g -> g.gameId));
        result = calculateSumOfPowers(gameMap);

        System.out.println(result);
    }

    public static int calculateSumOfPowers(Map<Integer, List<Game>> gameMap) {
        int res = 0;

        for (var entry : gameMap.entrySet()) {
            Game minGame = new Game(entry.getValue().get(0));
            minGame.gameId = entry.getKey();
            for (var game : entry.getValue()) {
                minGame.blue = Math.max(minGame.blue, game.blue);
                minGame.red = Math.max(minGame.red, game.red);
                minGame.green = Math.max(minGame.green, game.green);
            }
            int power = minGame.red * minGame.green * minGame.blue;
            res += power;
        }

        return res;
    }
}
