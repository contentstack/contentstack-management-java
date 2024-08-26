package com.contentstack.cms.core;

import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

/**
 * The Contentstack Logger
 *
 * @author ***REMOVED***
 * @version v0.1.0
 * @since 2022-10-20
 */
public class CMALogger {

    private final Logger logger;

    /**
     * The function returns the logger object.
     *
     * @return The method is returning an instance of the Logger class.
     */
    public Logger getLogger() {
        return logger;
    }

    // The `CMALogger` constructor is used to create an instance of the `CMALogger`
    // class. It takes a
    // parameter `className` of type `Class<?>` (any class) and initializes the
    // `logger` object using the
    // `getLogger` method of the `Logger` class. The `getLogger` method takes the
    // simple name of the
    // `className` and returns an instance of the `Logger` class. The `@NotNull`
    // annotation indicates that
    // the `className` parameter cannot be null.
    public CMALogger(@NotNull Class<?> className) {
        logger = Logger.getLogger(className.getSimpleName());
    }

    // The `info` method in the `CMALogger` class is used to log a message at an
    // informational level of
    // detail using the logger. It takes a `String` parameter named `message` and
    // logs the message using
    // the `info` method of the logger.
    // The `info` method in the `CMALogger` class is used to log a message at an
    // informational level of
    // detail using the logger. It takes a `String` parameter named `message` and
    // logs the message
    // using the `info` method of the logger. The `@NotNull` annotation indicates
    // that the `message`
    // parameter cannot be null.
    public void info(@NotNull String message) {
        logger.info(message);
    }

    // The `fine` method in the `CMALogger` class is used to log a message at a fine
    // level of detail using
    // the logger. It takes a `String` parameter named `message` and logs the
    // message using the `info`
    // method of the logger.
    public void fine(@NotNull String message) {
        logger.info(message);
    }

    // The `finer` method in the `CMALogger` class is used to log a message at a
    // finer level of detail
    // using the logger. It takes a `String` parameter named `message` and logs the
    // message using the
    // `finer` method of the logger.
    public void finer(@NotNull String message) {
        logger.finer(message);
    }

    /**
     * The function "warning" logs a warning message using a logger.
     *
     * @param message A string parameter named "message" which is annotated with the
     *                "@NotNull" annotation.
     */
    public void warning(@NotNull String message) {
        logger.warning(message);
    }

    /**
     * The function "severe" logs a severe-level message using a logger.
     *
     * @param message A string parameter named "message" which is annotated with the
     *                "@NotNull" annotation.
     */
    public void severe(@NotNull String message) {
        logger.severe(message);
    }
}
