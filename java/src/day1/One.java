package src.day1;

import src.utils.FileReaderUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class One {
    public static void main (String[] args) throws IOException {
        String fileName = "/day1/input.txt";

        BufferedReader inputFile = FileReaderUtil.getInputFile(fileName);

        String str;

        int sum = 0;
        while ((str = inputFile.readLine()) != null) {
//            System.out.println(str);
            List<Integer> nums = new ArrayList<>();
            String[] chars = str.split("");
            for (String letter : chars) {
                int numeric = isNumeric(letter);
                if (numeric != -1) {
                    nums.add(numeric);
                }
            }
            int num = (10 * nums.get(0)) + nums.get(nums.size() - 1);
            sum += num;
        }
        System.out.println("Final sum of the file is: " + sum);
    }

    public static int isNumeric(String letter) {
        try {
            return Integer.parseInt(letter);
        }
        catch (NumberFormatException e) {
            return -1;
        }
    }
}
