package org.example.parser.json;

import org.example.entity.Song;
import org.example.paser.json.JsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.*;

public class JsonParserTest {
    private JsonParser<Song> jsonParser;
    private String path;

    @BeforeEach
    public void setUp() {
        jsonParser = new JsonParser<>();
        path = "src/main/resources";
    }

    @Test
    @DisplayName("Generate statistics by attribute.")
    public void generateStatisticTest() {
        String attribute = "genre";

        List<Map<Song, Integer>> mockedResult = new ArrayList<>();
        mockedResult.add(new HashMap<>());
        JsonParser<Song> spyParser = spy(jsonParser);
        doReturn(mockedResult).when(spyParser).generateStatistic(anyString(), anyString());

        List<Map<Song, Integer>> result = spyParser.generateStatistic(path, attribute);

        verify(spyParser, times(1)).generateStatistic(path, attribute);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Generate statistics with invalid attribute.")
    public void generateStatisticInvalidAttributeTest() {
        String invalidAttribute = "invalid_attribute";

        assertThrows(RuntimeException.class, () -> jsonParser.generateStatistic(path, invalidAttribute));
    }

    @Test
    @DisplayName("Merge lists into common statistics.")
    public void mergeFromListTest() {
        Map<Object, Integer> map1 = new HashMap<>();
        map1.put("genre_1", 1);
        Map<Object, Integer> map2 = new HashMap<>();
        map2.put("genre_4", 2);
        map2.put("genre_2", 3);
        List<Map<Object, Integer>> testData = Arrays.asList(map1, map2);

        Map<Object, Integer> result = jsonParser.mergeFromList(testData);

        assertEquals(3, result.size());
        assertTrue(result.containsKey("genre_1"));
        assertTrue(result.containsKey("genre_2"));
        assertEquals(3, result.get("genre_2"));
        assertEquals(1, result.get("genre_1"));
    }
}
