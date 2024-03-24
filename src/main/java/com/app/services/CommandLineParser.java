package com.app.services;

import com.app.objects.ParsedArguments;

/**
 * Parses command line arguments for the MostActiveCookie application.
 * This parser specifically looks for a file path and a date, expecting the format:
 * 'java MostActiveCookie <file_path> -d <date>'.
 */

public class CommandLineParser implements CommandLineParserInterface {

    @Override
    public ParsedArguments parse(String[] args) {

        if (args.length != 3) {
            throw new IllegalArgumentException("Invalid number of arguments.");
        }

        String filePath = args[0];
        String flag = args[1];
        String date = args[2];

        if (!"-d".equals(flag)) {
            throw new IllegalArgumentException("Expected '-date' flag.");
        }

        return new ParsedArguments(filePath,date);
    }
}