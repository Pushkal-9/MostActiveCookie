package com.app;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MostActiveCookieTest {

    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    @TempDir
    Path tempDir;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testMainWithValidInput() throws Exception {
        Path testLogFile = tempDir.resolve("test_log.csv");
        List<String> lines = List.of(
                "cookie1,2021-12-09T14:19:00+00:00",
                "cookie2,2021-12-09T10:13:00+00:00",
                "cookie1,2021-12-09T10:10:00+00:00"
        );
        Files.write(testLogFile, lines);

        MostActiveCookie.main(new String[]{testLogFile.toString(), "-d", "2021-12-09"});

        String expectedOutput = "cookie1" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString(),
                "Expected output to be the most active cookie for the given date.");
    }
}
