package org.example.paser;

/**
 * Functional interface for extracting an attribute.
 *
 * @param <T> the type of entity from which the attribute is extracted.
 * @param <R> the type of attribute being extracted.
 */
@FunctionalInterface
public interface AttributeExtractor<T, R> {
    /**
     * Extracts the attribute from entity.
     *
     * @param entity the entity from which the attribute is to be extracted.
     * @return the extracted attribute.
     * @throws IllegalArgumentException if there is an error extracting the attribute
     */
    R extractAttribute(T entity) throws IllegalArgumentException;
}

