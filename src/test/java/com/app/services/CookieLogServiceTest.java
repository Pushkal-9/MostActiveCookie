package com.app.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CookieLogServiceTest {
    @TempDir
    Path tempDir;
    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private CookieLogService cookieLogService;

    @BeforeEach
    public void setUp() {
        CommandLineParser parser = new DefaultCommandLineParser();
        cookieLogService = new CookieLogServiceImpl(parser);
        System.setOut(new PrintStream(outContent));
        Logger.getLogger(CookieLogService.class.getName()).setLevel(Level.OFF); // Turn off logging for testing
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testProcessCookiesWithValidInput() throws Exception {
        Path testLogFile = tempDir.resolve("test_log.csv");
        List<String> lines = List.of(
                "cookie1,2021-12-09T14:19:00+00:00",
                "cookie2,2021-12-09T17:13:00+00:00",
                "cookie1,2021-12-09T19:10:00+00:00"
        );
        Files.write(testLogFile, lines);

        cookieLogService.processCookies(new String[]{testLogFile.toString(), "-d", "2021-12-09"});

        String expectedOutput = "cookie1" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString(),
                "Expected output to be the most active cookie for the given date.");
    }

    @Test
    public void testProcessCookiesWithInvalidArguments() {
        cookieLogService.processCookies(new String[]{});

        String output = outContent.toString();
        assertTrue(output.isEmpty(),
                "Output should indicate an error occurred or be empty due to invalid arguments.");
    }
}