package org.example.comparator;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Simple Comparator to sort Map by value.
 */
public final class Comparator {

    /**
     *
     * @return a sorted descending Map< K, V > by value.
     * @param <K> map key
     * @param <V> map value
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortMapByValue(Map<K, V> map) {
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(java.util.Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }
}
