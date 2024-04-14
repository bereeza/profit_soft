package org.example.utils;

import java.io.File;
import java.util.Objects;

/**
 * Utility class for file operations.
 */
public class FileUtils {
    /**
     * Retrieves all files in the specified directory.
     *
     * @param path the path to the dir.
     * @param extension the file extension to filter by.
     * @return an array of files in the directory.
     */
    public static File[] getAllFiles(String path, String extension) {
        File dir = validatePath(path);
        return dir.listFiles((dir1, name) -> name.endsWith(extension));
    }

    private static File validatePath(String path) {
        Objects.requireNonNull(path, "Path must not be null");
        File dir = new File(path);
        if (!dir.exists() || !dir.isDirectory()) {
            throw new IllegalArgumentException("Invalid directory: " + path);
        }
        return dir;
    }
}