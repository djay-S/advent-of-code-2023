package src.day1;

import src.utils.FileReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Two {
    public static void main(String[] args) throws IOException {
        String fileName = "/day1/input.txt";

        BufferedReader inputFile = FileReaderUtil.getInputFile(fileName);
        List<String> numStrList = List.of("one", "two", "three", "four", "five", "six", "seven", "eight", "nine");
        String line;
        int sum = 0;
        while ((line = inputFile.readLine()) != null) {
            List<Integer> nums = new ArrayList<>();
            for (int i = 0; i < line.length(); i++) {
                if (Character.isDigit(line.charAt(i))) {
                    nums.add(Character.getNumericValue(line.charAt(i)));
                }
                else {
                    for (String numStr : numStrList) {
                        if (line.startsWith(numStr, i)) {
                            nums.add(numStrList.indexOf(numStr) + 1);
                        }
                    }
                }
            }
//            System.out.println(line);
//            System.out.println(nums);
            int num = (10 * nums.get(0)) + nums.get(nums.size() - 1);
//            System.out.println(num);
            sum += num;
        }
        System.out.println("Final Sum: " + sum);
    }
}