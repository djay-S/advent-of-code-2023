package src.day2;

import src.utils.FileReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//Bag is loaded with: 12 red cubes, 13 green cubes, and 14 blue cubes
public class One {
    public static void main(String[] args) throws IOException {
        String fileName = "/day2/input.txt";
        BufferedReader inputFile = FileReaderUtil.getInputFile(fileName);
        String line;
        List<Game> gameList = new ArrayList<>();

        while ((line = inputFile.readLine()) != null) {
            System.out.println(line);
            gameList.addAll(getGameFromLine(line));
        }
        Game filterGame = new Game("12 red, 13 green, 14 blue", 0);
        List<Integer> invalidGameIds = gameList.stream()
                .filter(game -> game.red > filterGame.red || game.blue > filterGame.blue || game.green > filterGame.green)
                .map(game -> game.gameId)
                .distinct()
                .toList();

        List<Integer> allGameIds = gameList.stream()
                .map(game -> game.gameId)
                .distinct()
                .collect(Collectors.toList());

        allGameIds.removeAll(invalidGameIds);

        int sum = allGameIds.stream()
                .mapToInt(id -> id)
                .sum();

        System.out.println("Final Sum: " + sum);
    }

    public static List<Game> getGameFromLine(String line) {
        Map<Integer, Game> map = new LinkedHashMap<>();
        String[] split = line.split(":");
        String[] split1 = split[0].split(" ");
        int gameId = Integer.parseInt(split1[1]);

        String[] games = split[1].trim().split("; ");
        List<Game> gameList = new ArrayList<>();
        for (var gameStr : games) {
            Game game = new Game(gameStr, gameId);
            gameList.add(game);
        }
        return gameList;
    }
}


class Game {
    int gameId;
    public int green;
    public int red;
    public int blue;

    public Game(){}

    public Game(String line, int gameId) {
        this.gameId = gameId;
        String[] cubes = line.split(", ");
        for (String cube : cubes) {
            String[] cubeInfo = cube.split(" ");
            int count = Integer.parseInt(cubeInfo[0]);

            if (cube.contains("red")) {
                this.red += count;
            }
            else if (cube.contains("blue")) {
                this.blue += count;
            }
            else if (cube.contains("green")) {
                this.green += count;
            }
        }
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameId=" + gameId +
                ", green=" + green +
                ", red=" + red +
                ", blue=" + blue +
                '}';
    }
}
