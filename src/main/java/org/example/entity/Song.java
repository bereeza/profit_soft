package org.example.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.paser.AttributeExtractor;

import java.util.Map;

/**
 * The main entity of the application - {@link Song}.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Song {
    private String title;
    private Float duration;
    private String album;
    private String genre;
    private String artist;

    /**
     * Represents the attributes of the {@link Song} entity with @Getter.
     * Key - attribute name, Value - function to extract attribute value.
     * Used {@link AttributeExtractor} for greater flexibility.
     */
    public static final Map<String, AttributeExtractor<Song, ?>> ENTITY_ATTRIBUTES = Map.of(
            "title", Song::getTitle,
            "duration", Song::getDuration,
            "album", Song::getAlbum,
            "genre", Song::getGenre,
            "artist", Song::getArtist
    );
}