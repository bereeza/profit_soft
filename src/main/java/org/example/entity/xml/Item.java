package org.example.entity.xml;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class for further recording in the xml file.
 *
 * @param <T> accepts any parameter.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item<T> {
    private T value;
    private Integer count;
}