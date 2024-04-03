package src.day7;

import src.utils.FileReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

import static src.day7.One.calculateAnswer;

public class Two {

    private static final Logger LOGGER = Logger.getLogger(Two.class.getName());

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
//        String fileName = "/day7/test.txt";
        String fileName = "/day7/input.txt";

        BufferedReader bufferedReader = FileReaderUtil.getInputFile(fileName);
        String line;
        List<Stack> stackList = new ArrayList<>();

        while ((line = bufferedReader.readLine()) != null) {
            String[] split = line.split(" ");
            String hand = split[0].trim();
            int bid = Integer.parseInt(split[1].trim());
            String type = getHandTypeFromHand(hand);
            stackList.add(new Stack(hand, bid, type));
        }
        long answer = calculateAnswer(stackList);
        LOGGER.info("Final answer: " + answer);
        LOGGER.info("Total time taken: " + (System.currentTimeMillis() - startTime));
    }

    public static String getHandTypeFromHand(String hand) {
        if (hand.equals("JJJJJ"))
            return "Five of a kind";
        String[] cardsArr = hand.split("");
        Map<String, Integer> cardsMap = new HashMap<>();
        for (var card : cardsArr) {
            int count = 0;
            if (cardsMap.containsKey(card)) {
                count = cardsMap.get(card);
            }
            count++;
            cardsMap.put(card, count);
        }
        if (hand.contains("J")) {
            int jOccurrence = cardsMap.get("J");
            cardsMap.remove("J");
            Integer maxOccurrence = cardsMap.values().stream().max(Comparator.naturalOrder()).get();
            for (var entry : cardsMap.entrySet()) {
                Integer value = entry.getValue();
                if (value.equals(maxOccurrence)) {
                    value += jOccurrence;
                    cardsMap.put(entry.getKey(), value);
                    break;
                }
            }
        }
        if (cardsMap.size() == 1)
            return "Five of a kind";
        if (cardsMap.containsValue(4))
            return "Four of a kind";
        if (cardsMap.containsValue(3) && cardsMap.containsValue(1))
            return "Three of a kind";
        if (cardsMap.containsValue(3) && cardsMap.containsValue(2))
            return "Full House";
        if (cardsMap.size() == 3)
            return "Two pair";
        if (cardsMap.size() == 4)
            return "One pair";
        return "High card";
    }
}
