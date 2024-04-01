package src.day6;

import src.utils.FileReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

public class One {

    private final static Logger LOGGER = Logger.getLogger(One.class.getName());

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
//        String fileName = "/day6/test.txt";
        String fileName = "/day6/input.txt";

        BufferedReader bufferedReader = FileReaderUtil.getInputFile(fileName);
        String line;
        List<Integer> timeList = new ArrayList<>();
        List<Integer> distanceList = new ArrayList<>();

        while ((line = bufferedReader.readLine()) != null) {
            if (line.startsWith("Time: ")) {
                String[] split = line.split(":");
                String[] timeArr = split[1].trim().split(" ");
                timeList = Arrays.stream(timeArr).filter(Predicate.not(String::isBlank)).map(Integer::parseInt).toList();
            }
            if (line.startsWith("Distance: ")) {
                String[] split = line.split(":");
                String[] distanceArr = split[1].trim().split(" ");
                distanceList = Arrays.stream(distanceArr).filter(Predicate.not(String::isBlank)).map(Integer::parseInt).toList();
            }
        }
        long answer = calculateAnswer(timeList, distanceList);
        LOGGER.info("Final answer: " + answer);
        LOGGER.info("Total time elapsed: " + (System.currentTimeMillis() - startTime));
    }

    public static long calculateAnswer(List<Integer> timeList, List<Integer> distanceList) {
        long answer = 1L;

        for (int i = 0; i < timeList.size(); i++) {
            int time = timeList.get(i);
            int count = 0;
            for (int j = 0; j <= time; j++) {
                int timeTaken = j * (time - j);
                if (timeTaken > distanceList.get(i)) {
                    count++;
                }
            }
            LOGGER.info("Count: " + count);
            answer *= count;
        }
        return answer;
    }
}
