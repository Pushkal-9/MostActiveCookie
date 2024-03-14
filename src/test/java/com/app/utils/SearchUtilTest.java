package com.app.utils;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchUtilTest {

    @Test
    public void testFindStartIndex() {
        List<String> logLines = List.of(
                "cookie1,2021-12-08T10:00:00+00:00",
                "cookie2,2021-12-09T10:00:00+00:00",
                "cookie1,2021-12-09T11:00:00+00:00",
                "cookie3,2021-12-10T10:00:00+00:00"
        );
        int startIndex = SearchUtil.findStartIndex(logLines, "2021-12-09");
        assertEquals(1, startIndex, "The start index should correctly identify the first occurrence of the date.");
    }

    @Test
    public void testFindStartIndexWithNoMatch() {
        List<String> logLines = List.of(
                "cookie1,2021-12-08T10:00:00+00:00",
                "cookie2,2021-12-09T10:00:00+00:00",
                "cookie1,2021-12-09T11:00:00+00:00",
                "cookie3,2021-12-10T10:00:00+00:00"
        );
        int startIndex = SearchUtil.findStartIndex(logLines, "2021-12-11");
        assertEquals(-1, startIndex, "Should return -1 when the date is not found.");
    }

    @Test
    public void testFindEndIndex() {
        List<String> logLines = List.of(
                "cookie1,2021-12-08T10:00:00+00:00",
                "cookie2,2021-12-09T10:00:00+00:00",
                "cookie1,2021-12-09T11:00:00+00:00",
                "cookie3,2021-12-10T10:00:00+00:00"
        );
        int endIndex = SearchUtil.findEndIndex(logLines, "2021-12-09");
        assertEquals(2, endIndex, "The end index should correctly identify the last occurrence of the date.");
    }

    @Test
    public void testFindEndIndexWithNoMatch() {
        List<String> logLines = List.of(
                "cookie1,2021-12-08T10:00:00+00:00",
                "cookie2,2021-12-09T10:00:00+00:00",
                "cookie1,2021-12-09T11:00:00+00:00",
                "cookie3,2021-12-10T10:00:00+00:00"
        );
        int endIndex = SearchUtil.findEndIndex(logLines, "2021-12-11");
        assertEquals(-1, endIndex, "Should return -1 when the date is not found.");
    }
}