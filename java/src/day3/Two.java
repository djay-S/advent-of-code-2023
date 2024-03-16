package src.day3;

import src.utils.FileReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static src.day3.One.getCompleteNum;

public class Two {
    public static void main(String[] args) throws IOException {
//        String fileName = "/day3/test1.txt";
//        String fileName = "/day3/test.txt";
        String fileName = "/day3/input.txt";
        BufferedReader inputFile = FileReaderUtil.getInputFile(fileName);
        String line;
        int sum = 0;
        List<String> matrix = new ArrayList<>();
        while ((line = inputFile.readLine()) != null) {
            System.out.println(line);
            matrix.add(line);
        }
        sum = calculateNewSum(matrix);
        System.out.println("Final answer: " + sum);
    }

    public static int calculateNewSum(List<String> matrix) {
        char[][] arr = new char[matrix.size()][matrix.size()];
        int sum = 0;

        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.size(); j++) {
                arr[i][j] = matrix.get(i).charAt(j);
            }
        }

        Map<String, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if (Character.isDigit(arr[i][j])) {
                    String starCoordinates = isSurroundingSymbolStar(arr, i, j);
                    if (!starCoordinates.isBlank()) {
                        String[] num = {""};
                        int lastIdx = getCompleteNum(arr, i, j, num);
                        List<Integer> list = new ArrayList<>();
                        if (map.containsKey(starCoordinates)) {
                            list = map.get(starCoordinates);
                        }
                        list.add(Integer.parseInt(num[0]));
                        map.put(starCoordinates, list);
                        j = lastIdx;
                    }
                }
            }
        }

        if (map.isEmpty())
            return 0;
        for (var entry : map.entrySet()) {
            List<Integer> value = entry.getValue();

            if (value.size() == 2) {
                int gearRatio = value.get(0) * value.get(1);
                sum += gearRatio;
            }
        }
        return sum;
    }

    public static String isSurroundingSymbolStar(char[][] arr, int i, int j) {
        int currCol = j;
        int currRow = i;
        int nextCol = Math.min(j + 1, arr.length - 1);
        int nextRow = Math.min(i + 1, arr.length - 1);
        int prevCol = Math.max(j - 1, 0);
        int prevRow = Math.max(i - 1, 0);

        if (isCharacterSymbol(arr[currRow][nextCol], '*'))
            return currRow + "," + nextCol;
        if (isCharacterSymbol(arr[nextRow][nextCol], '*'))
            return nextRow + "," + nextCol;
        if (isCharacterSymbol(arr[nextRow][currCol], '*'))
            return nextRow + "," + currCol;
        if (isCharacterSymbol(arr[nextRow][prevCol], '*'))
            return nextRow + "," + prevCol;
        if (isCharacterSymbol(arr[currRow][prevCol], '*'))
            return currRow + "," + prevCol;
        if (isCharacterSymbol(arr[prevRow][prevCol], '*'))
            return prevRow + "," + prevCol;
        if (isCharacterSymbol(arr[prevRow][currCol], '*'))
            return prevRow + "," + currCol;
        if (isCharacterSymbol(arr[prevRow][nextCol], '*'))
            return prevRow + "," + nextCol;
        return "";
    }

    public static boolean isCharacterSymbol(char ch, char sink) {
        return ch == sink;
    }
}
