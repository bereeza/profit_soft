package org.example.exceptions;

/**
 * Exception fot invalid attribute.
 */
public class WrongAttributeException extends RuntimeException {
    /**
     * Default exception with an empty constructor.
     */
    public WrongAttributeException() {
    }

    /**
     * An exception with an error message.
     *
     * @param message - error message.
     */
    public WrongAttributeException(String message) {
        super(message);
    }
}
