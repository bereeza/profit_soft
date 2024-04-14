package org.example;

import org.example.entity.Song;
import org.example.paser.json.JsonParser;
import org.example.paser.xml.XmlParser;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String attribute = "genre";
        JsonParser<Song> jp = new JsonParser<>();

        long startTime = System.nanoTime();
        List<Map<Song, Integer>> statisticByAllFiles = jp.generateStatistic("src/main/resources", attribute);
        long endTime = System.nanoTime();
        long result = endTime - startTime;
        double seconds = (double) result / 1_000_000_000;
        System.out.println("Total execution time: " + seconds + " seconds");

        Map<Song, Integer> xmlStatistic = jp.mergeFromList(statisticByAllFiles);
        XmlParser<Song> xp = new XmlParser<>();
        xp.writeStatistic(xmlStatistic, attribute);
    }
}
