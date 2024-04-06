package src.day9;

import src.day8.Two;
import src.utils.FileReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class One {
    private static final Logger LOGGER;

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        LOGGER = Logger.getLogger(Two.class.getName());
    }

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
//        String fileName = "/day9/test.txt";
        String fileName = "/day9/input.txt";

        BufferedReader bufferedReader = FileReaderUtil.getInputFile(fileName);
        String line;

        int answer = 0;
        while ((line = bufferedReader.readLine()) != null) {
            int num = getNextNumber(line);
            LOGGER.info("Last num: " + num);
            answer += num;
        }
        LOGGER.info("Final answer: " + answer);
        LOGGER.info("Total time taken: " + (System.currentTimeMillis() - startTime));
    }

    public static int getNextNumber(String line) {
        int counter = 0;
        List<Integer> numbers = Arrays.stream(line.split(" ")).map(Integer::parseInt).toList();
        List<List<Integer>> seriesList = new ArrayList<>();
        seriesList.add(numbers);

        Set<Integer> differenceSet;

        do {
            differenceSet = new HashSet<>();
            List<Integer> integerList = seriesList.get(counter);
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < integerList.size() - 1; i++) {
                int difference = integerList.get(i + 1) - integerList.get(i);
                list.add(difference);
                differenceSet.add(difference);
            }
            seriesList.add(list);
            counter++;
        } while (!differenceSet.isEmpty() && differenceSet.size() > 1);

        List<Integer> prevList = new ArrayList<>();
        for (int i = seriesList.size() - 1; i >= 0; i--) {
            List<Integer> list = new ArrayList<>(seriesList.get(i));
            if (new HashSet<>(list).size() == 1) {
                list.add(list.get(0));
            } else {
                int i1 = list.get(list.size() - 1) + prevList.get(prevList.size() - 1);
                list.add(i1);
            }
            prevList = list;
            seriesList.set(i, list);
        }
        return seriesList.get(0).get(seriesList.get(0).size() - 1);
    }
}
