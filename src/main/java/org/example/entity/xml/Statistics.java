package org.example.entity.xml;

import lombok.Data;

import java.util.List;

/**
 * A class for generating XML statistics.
 *
 * @param <T>
 */
@Data
public final class Statistics<T> {
    private List<Item<T>> items;
}
