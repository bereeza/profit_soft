package org.example.paser.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Song;
import org.example.exceptions.WrongAttributeException;
import org.example.paser.AttributeExtractor;
import org.example.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Stream;

import static org.example.comparator.Comparator.sortMapByValue;
import static org.example.entity.Song.ENTITY_ATTRIBUTES;

/**
 * Json parser.
 * 1) It's used for parsing by path (all files in the dir are parsed).
 * 2) It's used to combine the statistics of parsed json files.
 *
 * @param <T> the class that is used when the class is called.
 */
public final class JsonParser<T> {
    /**
     * ObjectMapper instance used for JSON serialization/deserialization.
     */
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    /**
     * default extension for file.
     */
    private static final String JSON_EXTENSION = ".json";

    /**
     * default number of threads.
     */
    private static final int MAX_THREADS = 6;

    /**
     * The method of generating statistics for the json file attribute.
     *
     * @param path dir path.
     * @param attribute attribute for statistics.
     * @return list with sorted map (by value) for each file in dir.
     */
    public List<Map<T, Integer>> generateStatistic(String path, String attribute) {
        List<Map<T, Integer>> allStatistics = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);
        try (Stream<File> files = Arrays.stream(FileUtils.getAllFiles(path, JSON_EXTENSION))) {
            List<Future<Map<T, Integer>>> futures = files.parallel()
                    .map(file -> executor.submit(() -> parseToMap(file, attribute)))
                    .toList();

            for (Future<Map<T, Integer>> future : futures) {
                allStatistics.add(future.get());
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            executor.shutdown();
        }
        return allStatistics;
    }

    /**
     * Merges statistic maps from a list into a single sorted map.
     *
     * @param statistics list with statistic map.
     * @param <K> type of key.
     * @return common sorted map.
     */
    public <K> Map<K, Integer> mergeFromList(List<Map<K, Integer>> statistics) {
        Map<K, Integer> mergedStatistics = new ConcurrentHashMap<>();
        for (Map<K, Integer> i : statistics) {
            i.forEach((key, value) -> mergedStatistics.merge(key, value, Integer::sum));
        }
        return sortMapByValue(mergedStatistics);
    }

    private Map<T, Integer> parseToMap(File file, String attribute) {
        try {
            Map<T, Integer> statistics = new ConcurrentHashMap<>();
            AttributeExtractor<Song, T> valueExtractor = (AttributeExtractor<Song, T>) ENTITY_ATTRIBUTES.get(attribute);
            try (Stream<Song> songs = getSongsFromFile(file)) {
                songs.map(valueExtractor::extractAttribute)
                        .filter(Objects::nonNull)
                        .flatMap(this::splitAttribute)
                        .forEach(attr -> statistics.merge(attr, 1, Integer::sum));
            } catch (IOException e) {
                throw new RuntimeException("Error reading file: " + file.getName());
            }
            return sortMapByValue(statistics);
        } catch (RuntimeException e) {
            throw new WrongAttributeException("The attribute [" + attribute + "] is invalid or does not exist in the file");
        }
    }

    private Stream<Song> getSongsFromFile(File file) throws IOException {
        return Arrays.stream(JSON_MAPPER.readValue(file, Song[].class));
    }

    private Stream<T> splitAttribute(T attr) {
        return (attr instanceof String) ?
                Arrays.stream(((String) attr).split(",")).map(String::trim).map(val -> (T) val) :
                Stream.of(attr);
    }
}