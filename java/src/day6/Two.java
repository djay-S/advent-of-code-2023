package src.day6;

import src.utils.FileReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Logger;

public class Two {

    private final static Logger LOGGER = Logger.getLogger(Two.class.getName());

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
//        String fileName = "/day6/test.txt";
        String fileName = "/day6/input.txt";

        BufferedReader bufferedReader = FileReaderUtil.getInputFile(fileName);
        String line;
        long time = 0;
        long distance = 0;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.startsWith("Time: ")) {
                String[] split = line.split(":");
                time = Long.parseLong(split[1].trim().replace(" ", ""));
            }
            if (line.startsWith("Distance: ")) {
                String[] split = line.split(":");
                distance = Long.parseLong(split[1].trim().replace(" ", ""));
            }
        }
        long answer = calculateAnswer(time, distance);
        LOGGER.info("Final answer: " + answer);
        LOGGER.info("Total time elapsed: " + (System.currentTimeMillis() - startTime));
    }

    public static long calculateAnswer(long time, long distance) {
        long count = 0L;

        for (long i = 0; i < time; i++) {
            long timeTaken = i * (time - i);
            if (timeTaken > distance) {
                count++;
            }
        }
        return count;
    }
}
