package src.day5;

import src.utils.FileReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class One {

    static List<Range> seed2SoilRange = new ArrayList<>();
    static List<Range> soil2FertilizerRange = new ArrayList<>();
    static List<Range> fertilizer2WaterRange = new ArrayList<>();
    static List<Range> water2LightRange = new ArrayList<>();
    static List<Range> light2TemperatureRange = new ArrayList<>();
    static List<Range> temperature2HumidityRange = new ArrayList<>();
    static List<Range> humidity2LocationRange = new ArrayList<>();

    public static void main(String[] args) throws IOException {
//        String fileName = "/day5/test.txt";
        String fileName = "/day5/input.txt";

        BufferedReader bufferedReader = FileReaderUtil.getInputFile(fileName);
        String line;
        List<String> lines = new ArrayList<>();

        List<Long> seeds = new ArrayList<>();

        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }

        processLines(lines, seeds);
    }

    private static void processLines(List<String> lines, List<Long> seeds) {
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.startsWith("seeds: ")) {
                String[] split = line.split(":");
                String[] seedsStr = split[1].trim().split(" ");
                seeds = Arrays.stream(seedsStr).map(Long::parseLong).toList();
            }

            if (line.startsWith("seed-to-soil map:")) {
                populateRange(i, lines, seed2SoilRange);
            }

            if (line.startsWith("soil-to-fertilizer map:")) {
                populateRange(i, lines, soil2FertilizerRange);
            }

            if (line.startsWith("fertilizer-to-water map:")) {
                populateRange(i, lines, fertilizer2WaterRange);
            }

            if (line.startsWith("water-to-light map:")) {
                populateRange(i, lines, water2LightRange);
            }

            if (line.startsWith("light-to-temperature map:")) {
                populateRange(i, lines, light2TemperatureRange);
            }

            if (line.startsWith("temperature-to-humidity map:")) {
                populateRange(i, lines, temperature2HumidityRange);
            }

            if (line.startsWith("humidity-to-location map:")) {
                populateRange(i, lines, humidity2LocationRange);
            }
        }
        long answer = lowestLocation(seeds);
        System.out.println("Final answer: " + answer);
    }

    private static long lowestLocation(List<Long> seeds) {
        long minLocation = Long.MAX_VALUE;

        for (long seed : seeds) {
            System.out.println("seed: " + seed);
            long soil = getValueFromRange(seed, seed2SoilRange);
            System.out.println("soil: " + soil);
            long fertilizer = getValueFromRange(soil, soil2FertilizerRange);
            System.out.println("fertilizer: " + fertilizer);
            long water = getValueFromRange(fertilizer, fertilizer2WaterRange);
            System.out.println("water: " + water);
            long light = getValueFromRange(water, water2LightRange);
            System.out.println("light: " + light);
            long temperature = getValueFromRange(light, light2TemperatureRange);
            System.out.println("temperature: " + temperature);
            long humidity = getValueFromRange(temperature, temperature2HumidityRange);
            System.out.println("humidity: " + humidity);
            long location = getValueFromRange(humidity, humidity2LocationRange);
            System.out.println("location: " + location);
            minLocation = Math.min(minLocation, location);
        }
        return minLocation;
    }

    private static long getValueFromRange(long value, List<Range> rangeList) {
        long lowest = Long.MAX_VALUE;
        long highest = Long.MIN_VALUE;

        for (var range : rangeList) {
            lowest = Math.min(lowest, range.lower.source);
            highest = Math.max(highest, range.upper.source);
        }

        for (Range range : rangeList) {
            if (value < lowest || value > highest)
                return value;
            if (value >= range.lower.source && value <= range.upper.source) {
                return value - (range.lower.source - range.lower.destination);
            }
        }
        return value;
    }

    private static void populateRange(int idx, List<String> lines, List<Range> rangeList) {
        System.out.println(lines.get(idx));
        idx++;

        while (idx < lines.size() && lines.get(idx) != null && !lines.get(idx).isBlank()) {
            String line = lines.get(idx);
            if (line == null || line.isBlank()) {
                return;
            }
            Range range = new Range(line);
            rangeList.add(range);
            idx++;
        }
    }
}

class Bound {
    long source;
    long destination;

    public Bound(long source, long destination) {
        this.source = source;
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "Bound{" +
                "source=" + source +
                ", destination=" + destination +
                '}';
    }
}

class Range {
    Bound lower;
    Bound upper;

    public Range(String line) {
        line = line.trim();
        String[] nums = line.split(" ");
        long destination = Long.parseLong(nums[0]);
        long source = Long.parseLong(nums[1]);
        long length = Long.parseLong(nums[2]);

        this.lower = new Bound(source, destination);
        this.upper = new Bound(source + length - 1, destination + length - 1);
    }

    @Override
    public String toString() {
        return "Range{" +
                "lower=" + lower +
                ", upper=" + upper +
                '}';
    }
}