package org.example.parser.xml;

import org.example.paser.xml.XmlParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class XmlParserTest {
    private XmlParser<Integer> xmlParser;
    private Map<Integer, Integer> xmlStatistic;

    @BeforeEach
    public void setUp() {
        xmlParser = new XmlParser<>();
        xmlStatistic = new HashMap<>();
        xmlStatistic.put(1, 1);
        xmlStatistic.put(2, 2);
    }

    @Test
    @DisplayName("Write statistics to xml.")
    public void writeStatisticTest() {
        xmlParser.writeStatistic(xmlStatistic, "attribute");
        String path = "src/main/resources/xml/" ;
        File xmlFile = new File(path);

        assertTrue(xmlFile.exists());
        assertNotNull(xmlStatistic);
    }
}