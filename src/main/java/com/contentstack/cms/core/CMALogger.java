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

    public Logger getLogger() {
        return logger;
    }

    public CMALogger(@NotNull Class<?> className) {
        logger = Logger.getLogger(className.getSimpleName());
    }

    public void info(@NotNull String message) {
        logger.info(message);
    }

    public void fine(@NotNull String message) {
        logger.info(message);
    }

    public void finer(@NotNull String message) {
        logger.finer(message);
    }

    public void warning(@NotNull String message) {
        logger.warning(message);
    }

    public void severe(@NotNull String message) {
        logger.severe(message);
    }
}
