package com.app.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.app.objects.ParsedArguments;

public class CommandLineParserTest {

    @Test
    public void testValidArguments() {
        String[] args = {"path/to/file.csv", "-d", "2021-12-09"};
        CommandLineParser parser = new DefaultCommandLineParser();
        ParsedArguments parsedArgs = parser.parse(args);
        Assertions.assertEquals("path/to/file.csv", parsedArgs.getFilePath());
        Assertions.assertEquals("2021-12-09", parsedArgs.getDate());
    }

    @Test
    public void testInvalidArgumentsTooFew() {
        String[] args = {"path/to/file.csv"};
        CommandLineParser parser = new DefaultCommandLineParser();
        Assertions.assertThrows(IllegalArgumentException.class, () -> parser.parse(args));
    }

    @Test
    public void testInvalidArgumentsWrongFlag() {
        String[] args = {"path/to/file.csv", "-date", "2021-12-09"};
        CommandLineParser parser = new DefaultCommandLineParser();
        Assertions.assertThrows(IllegalArgumentException.class, () -> parser.parse(args));
    }
}

