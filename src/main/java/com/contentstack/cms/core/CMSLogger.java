package com.contentstack.cms.core;

import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class CMSLogger {

    private final Logger logger;

    public Logger getLOGGER() {
        return logger;
    }

    public CMSLogger(@NotNull Class className) {
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
