package src.day7;

import src.utils.FileReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class One {

    public static final List<Character> cardOrder = List.of('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J'); //New Order for two
//    public static final List<Character> cardOrder = List.of('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2');
    private static final Logger LOGGER = Logger.getLogger(One.class.getName());

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

    public static long calculateAnswer(List<Stack> stackList) {
        int[] maxRank = new int[]{1};
        long[] answerArr = new long[]{0};

        Map<String, List<Stack>> typeStackListMap = stackList.stream().collect(Collectors.groupingBy(s -> s.type));

        if (typeStackListMap.containsKey("High card")) {
            calculateRankOfStacks(typeStackListMap.get("High card"), maxRank, answerArr);
        }
        if (typeStackListMap.containsKey("One pair")) {
            calculateRankOfStacks(typeStackListMap.get("One pair"), maxRank, answerArr);
        }
        if (typeStackListMap.containsKey("Two pair")) {
            calculateRankOfStacks(typeStackListMap.get("Two pair"), maxRank, answerArr);
        }
        if (typeStackListMap.containsKey("Three of a kind")) {
            calculateRankOfStacks(typeStackListMap.get("Three of a kind"), maxRank, answerArr);
        }
        if (typeStackListMap.containsKey("Full House")) {
            calculateRankOfStacks(typeStackListMap.get("Full House"), maxRank, answerArr);
        }
        if (typeStackListMap.containsKey("Four of a kind")) {
            calculateRankOfStacks(typeStackListMap.get("Four of a kind"), maxRank, answerArr);
        }
        if (typeStackListMap.containsKey("Five of a kind")) {
            calculateRankOfStacks(typeStackListMap.get("Five of a kind"), maxRank, answerArr);
        }

        return answerArr[0];
    }

    public static void calculateRankOfStacks(List<Stack> stackList, int[] maxRank, long[] answerArr) {
        Collections.sort(stackList);
        int rank = maxRank[0];
        long answer = answerArr[0];
        for (var stack : stackList) {
            stack.rank = rank++;
            answer += ((long) stack.rank * stack.bid);
        }
        answerArr[0] = answer;
        maxRank[0] = rank;
    }

    public static String getHandTypeFromHand(String hand) {
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

class Stack implements Comparable<Stack> {
    String hand;
    int bid;
    String type;
    int rank;

    public Stack(String hand, int bid, String type, int rank) {
        this.hand = hand;
        this.bid = bid;
        this.type = type;
        this.rank = rank;
    }

    public Stack(String hand, int bid) {
        this.hand = hand;
        this.bid = bid;
    }

    public Stack(String hand, int bid, String type) {
        this.hand = hand;
        this.bid = bid;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Stack{" +
                "hand='" + hand + '\'' +
                ", bid=" + bid +
                ", type='" + type + '\'' +
                ", rank=" + rank +
                '}';
    }

    @Override
    public int compareTo(Stack stack) {
        int i = 0;
        while (this.hand.charAt(i) == stack.hand.charAt(i)) {
            i++;
        }
        return One.cardOrder.indexOf(stack.hand.charAt(i)) - One.cardOrder.indexOf(this.hand.charAt(i));
    }
}