package com.app.services;

/**
 * Parses command line arguments for the MostActiveCookie application.
 * This parser specifically looks for a file path and a date, expecting the format:
 * 'java MostActiveCookie <file_path> -d <date>'.
 */
public class CommandLineParser {
    private final String filePath;
    private final String date;

    /**
     * Constructs a services.CommandLineParser with the provided command line arguments.
     * Expects the arguments to contain exactly three elements: the file path, the "-d" flag, and the date.
     * Throws an IllegalArgumentException if the arguments do not match the expected format.
     *
     * @param args Command line arguments passed to the application.
     * @throws IllegalArgumentException If the number of arguments is incorrect or the "-d" flag is missing.
     */
    public CommandLineParser(String[] args) {
        if (args.length != 3 || !"-d".equals(args[1])) {
            throw new IllegalArgumentException("Expected Command : 'java MostActiveCookie <file_path> -d <date>'");
        }
        this.filePath = args[0];
        this.date = args[2];
    }

    /**
     * Gets the file path from the command line arguments.
     *
     * @return The file path specified in the command line arguments.
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Gets the date from the command line arguments.
     *
     * @return The date specified in the command line arguments.
     */
    public String getDate() {
        return date;
    }
}