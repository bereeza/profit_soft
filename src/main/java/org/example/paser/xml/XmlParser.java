package org.example.paser.xml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.example.entity.xml.Item;
import org.example.entity.xml.Statistics;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Xml parser.
 * Writes the received statistics to a xml file.
 *
 * @param <T> the class that is used when the class is called.
 */
public class XmlParser<T> {
    /**
     * ObjectMapper instance used for Xml serialization/deserialization.
     */
    private static final ObjectMapper xmlMapper = new XmlMapper();

    /**
     * default extension for file.
     */
    private static final String XML_EXTENSION = ".xml";

    /**
     * default filename
     */
    private static final String FILE_NAME_PREFIX = "statistics_by_";

    /**
     * default dir for file.
     */
    private static final String XML_DIR = "src/main/resources/xml";

    static {
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    /**
     * @param xmlStatistic map of prepared statistics.
     * @param attribute    attribute name for naming the file.
     */
    public void writeStatistic(Map<T, Integer> xmlStatistic, String attribute) {
        String fileName = FILE_NAME_PREFIX + attribute + XML_EXTENSION;
        String path = XML_DIR + File.separator + fileName;

        try {
            ArrayList<Item<T>> items = createItems(xmlStatistic);
            Statistics<T> statistics = createStatistics(items);

            File xmlFile = new File(path);
            xmlMapper.writeValue(xmlFile, statistics);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ArrayList<Item<T>> createItems(Map<T, Integer> xmlStatistic) {
        ArrayList<Item<T>> items = new ArrayList<>();
        xmlStatistic.forEach((key, value) -> {
            Item<T> item = new Item<>();
            item.setValue(key);
            item.setCount(value);
            items.add(item);
        });
        return items;
    }

    private Statistics<T> createStatistics(ArrayList<Item<T>> items) {
        Statistics<T> statistics = new Statistics<>();
        statistics.setItems(items);
        return statistics;
    }
}