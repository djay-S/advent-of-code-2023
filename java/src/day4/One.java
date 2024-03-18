package src.day4;

import src.utils.FileReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class One {
    public static void main(String[] args) throws IOException {
//        String fileName = "/day4/test.txt";
        String fileName = "/day4/input.txt";

        BufferedReader bufferedReader = FileReaderUtil.getInputFile(fileName);
        String line;
        List<Card> cardList = new ArrayList<>();

        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
            Card card = new Card(line);
            cardList.add(card);
        }
//        System.out.println(cardList);
        int sum = calculateSum(cardList);
        System.out.println("Final sum: " + sum);
    }

    public static int calculateSum(List<Card> cardList) {
        int sum = 0;

        for (var card : cardList) {
            long count = card.winningNums.stream().filter(num -> card.givenNums.contains(num)).count();
            int point = (int) Math.pow(2, count - 1);
            sum += point;
        }
        return sum;
    }
}


class Card {
    int id;
    List<Integer> winningNums;
    List<Integer> givenNums;

    private Card() {}

    public Card(String line) {
        String[] split = line.split(":");
        String[] cardHeader = split[0].split(" ");
        int cardId = Integer.parseInt(Arrays.stream(cardHeader).filter(Predicate.not(String::isBlank)).toList().get(1));

        String[] nums = split[1].trim().split("\\|");
        List<Integer> winningNums = convertStrToNumList(nums[0].trim());
        List<Integer> givenNums = convertStrToNumList(nums[1].trim());

        this.id = cardId;
        this.winningNums = winningNums;
        this.givenNums = givenNums;
    }

    private Card(int cardId, List<Integer> winningNums, List<Integer> givenNums) {
        this.id = cardId;
        this.winningNums = winningNums;
        this.givenNums = givenNums;
    }

    private List<Integer> convertStrToNumList(String num) {
        if (num.isBlank())
            return new ArrayList<>();
        String[] nums = num.split(" ");
        return Arrays.stream(nums).filter(Predicate.not(String::isBlank)).map(Integer::parseInt).toList();
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", winningNums=" + winningNums +
                ", givenNums=" + givenNums +
                '}';
    }
}