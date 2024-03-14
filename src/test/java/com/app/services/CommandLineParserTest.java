package com.app.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CommandLineParserTest {

    @Test
    public void testValidArguments() {
        String[] args = {"path/to/file.csv", "-d", "2021-12-09"};
        CommandLineParser parser = new CommandLineParser(args);
        Assertions.assertEquals("path/to/file.csv", parser.getFilePath());
        Assertions.assertEquals("2021-12-09", parser.getDate());
    }

    @Test
    public void testInvalidArguments() {
        String[] args = {"path/to/file.csv"};
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new CommandLineParser(args);
        });
    }
}
