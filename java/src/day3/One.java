package src.day3;

import src.utils.FileReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class One {

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
        sum = calculateSum(matrix);
        System.out.println("Final answer:" + sum);
    }

    public static int calculateSum(List<String> matrix) {
        char[][] arr = new char[matrix.size()][matrix.size()];

        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.size(); j++) {
                arr[i][j] = matrix.get(i).charAt(j);
            }
        }

        List<Integer> partNumbers = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if (Character.isDigit(arr[i][j])) {
                    if (isSurroundingSymbol(arr, i, j)) {
                        String[] num = {""};
                        int lastIdx = getCompleteNum(arr, i, j, num);
//                        System.out.printf("i:%s, j:%s, num:%s, %s\n", i, j, num[0], lastIdx);
                        partNumbers.add(Integer.parseInt(num[0]));
                        j = lastIdx;
                    }
                }
            }
        }
//        System.out.println(partNumbers);
        return partNumbers.stream().mapToInt(i -> i).sum();
    }

    public static int getCompleteNum(char[][] arr, int i, int j, String[] number) {
        number[0] = "";
        int num = 0;
        while (j >= 0) {
            if (!Character.isDigit(arr[i][j])) {
                break;
            }
            j--;
        }
        j++;
        while (j < arr.length) {
            if (Character.isDigit(arr[i][j])) {
                num = (10 * num) + Character.getNumericValue(arr[i][j]);
                number[0] += Character.toString(arr[i][j]);
            } else
                break;
            j++;
        }
        return j;
    }

    public static boolean isSurroundingSymbol(char[][] arr, int i, int j) {
        int currCol = j;
        int currRow = i;
        int nextCol = Math.min(j + 1, arr.length - 1);
        int nextRow = Math.min(i + 1, arr.length - 1);
        int prevCol = Math.max(j - 1, 0);
        int prevRow = Math.max(i - 1, 0);

        if (isCharacterSymbol(arr[currRow][nextCol]))
            return true;
        if (isCharacterSymbol(arr[nextRow][nextCol]))
            return true;
        if (isCharacterSymbol(arr[nextRow][currCol]))
            return true;
        if (isCharacterSymbol(arr[nextRow][prevCol]))
            return true;
        if (isCharacterSymbol(arr[currRow][prevCol]))
            return true;
        if (isCharacterSymbol(arr[prevRow][prevCol]))
            return true;
        if (isCharacterSymbol(arr[prevRow][currCol]))
            return true;
        if (isCharacterSymbol(arr[prevRow][nextCol]))
            return true;
        return false;
    }

    public static boolean isCharacterSymbol(char ch) {
        return !Character.isLetterOrDigit(ch) && ch != '.';
    }
}
