package src.day4;

import src.utils.FileReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Two {
    public static void main(String[] args) throws IOException {
//        String fileName = "/day4/test.txt";
        String fileName = "/day4/input.txt";

        BufferedReader bufferedReader = FileReaderUtil.getInputFile(fileName);
        String line;

        List<Card> cardList = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null) {
            Card card = new Card(line);
            cardList.add(card);
        }
        int sum = copyCards(cardList);
        System.out.println("Final answer: " + sum);
    }

    private static int copyCards(List<Card> cardList) {
        Map<Integer, List<Card>> cardMap = new LinkedHashMap<>();

        for (var card : cardList) {
            List<Card> cards = new ArrayList<>();
            if (cardMap.containsKey(card.id)) {
                cards = cardMap.get(card.id);
            }
            cards.add(card);
            cardMap.put(card.id, cards);
        }

        for (var entry : cardMap.entrySet()) {
            Integer key = entry.getKey();
            List<Card> value = entry.getValue();
            for (var card : value) {
                long count = card.winningNums.stream().filter(num -> card.givenNums.contains(num)).count();
                int j = 1;
                while (j <= count) {
                    int idx = j + key - 1;
                    List<Card> cards1 = cardMap.get(cardList.get(idx).id);
                    cards1.add(cardList.get(idx));
                    cardMap.put(cardList.get(idx).id, cards1);
                    j++;
                }
            }
        }

        int sum = cardMap.values().stream().map(List::size).mapToInt(i -> i).sum();
        return sum;
    }
}
