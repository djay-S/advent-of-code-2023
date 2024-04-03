package src.day8;

import src.utils.FileReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class One {

    private static final Logger LOGGER = Logger.getLogger(One.class.getName());

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
//        String fileName = "/day8/test1.txt";
//        String fileName = "/day8/test2.txt";
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
        int answer = calculateAnswer(networkList, lr);
        LOGGER.info("Final answer: " + answer);
        LOGGER.info("Total time taken: " + (System.currentTimeMillis() - startTime));
    }

    public static int calculateAnswer(List<Network> networkList, String lr) {
        int answer = 0;
        int idx = 0;
        String[] directions = lr.split("");
        Map<String, Network> networkMap = networkList.stream().collect(Collectors.toMap(n -> n.node, Function.identity()));
        String currentNode = "AAA";

        while (!currentNode.equals("ZZZ")) {
            Network network = networkMap.get(currentNode);
            String direction = directions[idx];
            if (direction.equals("L"))
                currentNode = network.left;
            else
                currentNode = network.right;
            idx = ++idx >= directions.length ? 0 : idx;
            answer++;
        }
        return answer;
    }
}

class Network {
    String node;
    String left;
    String right;

    public Network(String node, String left, String right) {
        this.node = node;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "Network{" +
                "node='" + node + '\'' +
                ", left='" + left + '\'' +
                ", right='" + right + '\'' +
                '}';
    }
}