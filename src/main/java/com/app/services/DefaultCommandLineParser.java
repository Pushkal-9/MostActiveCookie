package com.app.services;

import com.app.objects.ParsedArguments;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses command line arguments for the MostActiveCookie application.
 * This parser specifically looks for a file path and a date, expecting the format:
 * 'java MostActiveCookie <file_path> -d <date>'.
 */

public class DefaultCommandLineParser implements CommandLineParser {

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

        if (!isValidDateFormat(date)) {
            throw new IllegalArgumentException("Date must be in YYYY-MM-DD format.");
        }

        return new ParsedArguments(filePath,date);
    }

    private boolean isValidDateFormat(String date) {
        if (date == null || date.length() != 10) {
            return false;
        }

        if (date.charAt(4) != '-' || date.charAt(7) != '-') {
            return false;
        }

        try {
            int year = Integer.parseInt(date.substring(0, 4));
            int month = Integer.parseInt(date.substring(5, 7));
            int day = Integer.parseInt(date.substring(8, 10));

            if (year <= 0 || month <= 0 || month > 12 || day <= 0 || day > 31) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
}