package com.app.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CookieLogServiceTest {
    private static final String TEST_LOG_FILE_PATH = "testLogFile.txt";
    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    @TempDir
    Path tempDir;
    private CookieLogService cookieLogService;

    @BeforeEach
    public void setUp() {
        cookieLogService = new CookieLogService();
        System.setOut(new PrintStream(outContent));

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

        CookieLogService service = new CookieLogService();

        service.processCookies(new String[]{testLogFile.toString(), "-d", "2021-12-09"});

        String expectedOutput = "cookie1" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString(),
                "Expected output to be the most active cookie for the given date.");
    }

    @Test
    public void testProcessCookiesWithInvalidArguments() {
        CookieLogService service = new CookieLogService();

        service.processCookies(new String[]{});

        String output = outContent.toString();
        assertTrue(output.isEmpty(),
                "Output should indicate an error occurred or be empty due to invalid arguments.");
    }

    @Test
    public void testFindMostActiveCookies() throws IOException {
        String logContent = "cookie1,2024-03-14T12:30:00Z\ncookie2,2024-03-14T12:35:00Z\ncookie1,2024-03-14T12:40:00Z";
        Path path = Path.of(TEST_LOG_FILE_PATH);
        Files.write(path, logContent.getBytes());
        List<String> mostActiveCookies = cookieLogService.findMostActiveCookies(TEST_LOG_FILE_PATH, "2024-03-14");
        assertEquals(1, mostActiveCookies.size());
        assertEquals("cookie1", mostActiveCookies.get(0));
    }

    @Test
    public void testCountCookieOccurrences() {
        List<String> logLines = Arrays.asList(
                "cookie1,2024-03-14T12:30:00Z",
                "cookie2,2024-03-14T12:35:00Z",
                "cookie1,2024-03-14T12:40:00Z",
                "cookie3,2024-03-14T12:45:00Z",
                "cookie1,2024-03-14T12:50:00Z"
        );

        assertEquals(3, cookieLogService.countCookieOccurrences(logLines, "2024-03-14").get("cookie1"));
        assertEquals(1, cookieLogService.countCookieOccurrences(logLines, "2024-03-14").get("cookie2"));
        assertEquals(1, cookieLogService.countCookieOccurrences(logLines, "2024-03-14").get("cookie3"));
    }

    @Test
    public void testFindMaxOccurrence() {
        List<String> logLines = Arrays.asList(
                "cookie1,2024-03-14T12:30:00Z",
                "cookie2,2024-03-14T12:35:00Z",
                "cookie1,2024-03-14T12:40:00Z",
                "cookie3,2024-03-14T12:45:00Z",
                "cookie1,2024-03-14T12:50:00Z"
        );

        assertEquals(3, cookieLogService.findMaxOccurrence(cookieLogService.countCookieOccurrences(logLines, "2024-03-14")));
    }

    @Test
    public void testCollectMostActiveCookies() {
        List<String> logLines = Arrays.asList(
                "cookie1,2024-03-14T12:30:00Z",
                "cookie2,2024-03-14T12:35:00Z",
                "cookie1,2024-03-14T12:40:00Z",
                "cookie3,2024-03-14T12:45:00Z",
                "cookie1,2024-03-14T12:50:00Z"
        );

        List<String> mostActiveCookies = cookieLogService.collectMostActiveCookies(cookieLogService.countCookieOccurrences(logLines, "2024-03-14"), 3);
        assertEquals(1, mostActiveCookies.size());
        assertEquals("cookie1", mostActiveCookies.get(0));
    }

    @Test
    public void testReadLogLines() throws Exception {
        String logContent = "cookie1,2024-03-14T12:30:00Z\ncookie2,2024-03-14T12:35:00Z";
        Path path = Path.of(TEST_LOG_FILE_PATH);
        Files.write(path, logContent.getBytes());
        List<String> logLines = cookieLogService.readLogLines(TEST_LOG_FILE_PATH);
        assertEquals(2, logLines.size());
        assertTrue(logLines.contains("cookie1,2024-03-14T12:30:00Z"));
        assertTrue(logLines.contains("cookie2,2024-03-14T12:35:00Z"));
        Files.deleteIfExists(path);
    }
}
