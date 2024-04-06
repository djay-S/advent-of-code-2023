package src.day8;

import src.utils.FileReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Two {

    private static Logger LOGGER = null;

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        LOGGER = Logger.getLogger(Two.class.getName());
    }

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

//        String fileName = "/day8/test1.txt";
//        String fileName = "/day8/test2.txt";
//        String fileName = "/day8/test3.txt";
        String fileName = "/day8/input.txt";

        BufferedReader bufferedReader = FileReaderUtil.getInputFile(fileName);
        String line;

        String lr = "";
        List<Network> networkList = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null) {
            if (line.isBlank())
                continue;

            if (lr.isBlank()) {
                lr = line;
            } else {
                String[] split = line.split("=");
                String node = split[0].trim();
                String[] dests = split[1].trim().replace("(", "").replace(")", "").split(",");
                String left = dests[0].trim();
                String right = dests[1].trim();
                networkList.add(new Network(node, left, right));
            }
        }
        long answer = calculateAnswer(networkList, lr);
        LOGGER.info("Final answer: " + answer);
        LOGGER.info("Total time taken: " + (System.currentTimeMillis() - startTime));
    }

    public static long calculateAnswer(List<Network> networkList, String lr) {
        String[] directions = lr.split("");
        LinkedHashMap<String, Network> networkMap = networkList.stream().collect(Collectors.toMap(n -> n.node, Function.identity(), (n1, n2) -> n2, LinkedHashMap::new));
        List<String> ghostList = networkList.stream().map(n -> n.node).filter(node -> node.endsWith("A")).toList();
        List<Long> countList = new ArrayList<>();

        for (var ghost : ghostList) {
            long answer = 0L;
            int idx = 0;
            String currentNode = ghost;

            while (!currentNode.endsWith("Z")) {
                String direction = directions[idx];
                Network network = networkMap.get(currentNode);

                if (direction.equals("L")) {
                    currentNode = network.left;
                } else {
                    currentNode = network.right;
                }

                idx = ++idx >= directions.length ? 0 : idx;
                answer++;
            }
            countList.add(answer);
        }
        LOGGER.info(countList.toString());
        return lcmOf(countList);
    }

    public static long lcmOf(List<Long> numbers) {
        long lcm = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            lcm = lcm(lcm, numbers.get(i));
        }
        return lcm;
    }

    public static long lcm(long a, long b) {
        return a * (b / gcd(a, b));
    }

    public static long gcd(long a, long b) {
        while (b > 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
