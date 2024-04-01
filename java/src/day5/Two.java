package src.day5;

import src.utils.FileReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static src.day5.One.populateRange;

public class Two {

    private static final Logger LOGGER = Logger.getLogger(Two.class.getName());

    static List<Long> seeds = new ArrayList<>();
    static List<Range> seed2SoilRange = new ArrayList<>();
    static List<Range> soil2FertilizerRange = new ArrayList<>();
    static List<Range> fertilizer2WaterRange = new ArrayList<>();
    static List<Range> water2LightRange = new ArrayList<>();
    static List<Range> light2TemperatureRange = new ArrayList<>();
    static List<Range> temperature2HumidityRange = new ArrayList<>();
    static List<Range> humidity2LocationRange = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
//        String fileName = "/day5/test.txt";
        String fileName = "/day5/input.txt";

        BufferedReader bufferedReader = FileReaderUtil.getInputFile(fileName);
        String line;
        List<String> lines = new ArrayList<>();

        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }

        processLines(lines);
        LOGGER.info("Total time taken: " + (System.currentTimeMillis() - startTime) + "ms");
    }

    public static void processLines(List<String> lines) {
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

    public static long lowestLocation(List<Long> seeds) {
        long minLocation = 0L;

        for (long location = 0; location < Long.MAX_VALUE; location++) {
            long humidity = getSourceFromDestinationNRange(location, humidity2LocationRange);
            if (humidity < 0) continue;
            long temperature = getSourceFromDestinationNRange(humidity, temperature2HumidityRange);
            if (temperature < 0) continue;
            long light = getSourceFromDestinationNRange(temperature, light2TemperatureRange);
            if (light < 0) continue;
            long water = getSourceFromDestinationNRange(light, water2LightRange);
            if (water < 0) continue;
            long fertilizer = getSourceFromDestinationNRange(water, fertilizer2WaterRange);
            if (fertilizer < 0) continue;
            long soil = getSourceFromDestinationNRange(fertilizer, soil2FertilizerRange);
            if (soil < 0) continue;
            long seed = getSourceFromDestinationNRange(soil, seed2SoilRange);
            if (seed < 0) continue;
//            String logStr = "location: " + location + ", humidity: " + humidity + ", temperature: " + temperature + ", light: " + light + ", water: " + water + ", fertilizer: " + fertilizer + ", soil: " + soil + ", seed: " + seed;
//            LOGGER.info(logStr);
            if (isValidSeed(seed)) {
                return location;
            }
        }
        return minLocation;
    }

    public static boolean isValidSeed(long seed) {

        if (seed < 0) {
            return false;
        }

        for (int i = 0; i < seeds.size(); i += 2) {
            long lowerLimit = seeds.get(i);
            long length = seeds.get(i + 1);
            long upperLimit = lowerLimit + length;
            if (seed <= upperLimit && seed >= lowerLimit) {
                return true;
            }
        }
        return false;
    }

    public static long getSourceFromDestinationNRange(long destination, List<Range> rangeList) {
        long lowest = Long.MAX_VALUE;
        long highest = Long.MIN_VALUE;

        for (var range : rangeList) {
            lowest = Math.min(lowest, range.lower.source);
            highest = Math.max(highest, range.upper.source);
        }

        for (var range : rangeList) {
            if (destination < lowest || destination > highest) {
                return destination;
            }
            if (destination >= range.lower.destination && destination <= range.upper.destination) {
                return destination + (range.lower.source - range.lower.destination);
            }
        }
        return destination;
    }
}
