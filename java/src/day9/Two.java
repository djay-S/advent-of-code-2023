package src.day9;

import src.utils.FileReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static src.day9.One.getNextNumber;

public class Two {
    private static final Logger LOGGER;

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        LOGGER = Logger.getLogger(src.day8.Two.class.getName());
    }

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
//        String fileName = "/day9/test.txt";
        String fileName = "/day9/input.txt";

        BufferedReader bufferedReader = FileReaderUtil.getInputFile(fileName);
        String line;

        int answer = 0;
        while ((line = bufferedReader.readLine()) != null) {
            line = getReverseStr(line);
            int num = getNextNumber(line);
            LOGGER.info("Last num: " + num);
            answer += num;
        }
        LOGGER.info("Final answer :" + answer);
        LOGGER.info("Total time taken: " + (System.currentTimeMillis() - startTime));
    }

    public static String getReverseStr(String line) {
        String[] splitLine = line.split(" ");
        List<String> list = Arrays.stream(splitLine).collect(Collectors.toList());
        Collections.reverse(list);
        return String.join(" ", list);
    }
}
