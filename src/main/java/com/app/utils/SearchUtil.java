package com.app.utils;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class providing search functionalities specific to cookie log processing.
 * It includes methods for extracting date components from timestamps and finding the start and end indexes of cookies for a given date.
 */
public class SearchUtil {
    private static final Logger logger = Logger.getLogger(SearchUtil.class.getName());

    /**
     * Extracts the date component from a timestamp string.
     * Assumes the timestamp format starts with the date in "yyyy-MM-dd" format followed by additional time information.
     * If the timestamp is shorter than the length of a standard date, it returns the original timestamp.
     *
     * @param timestamp The full timestamp string from which the date component is to be extracted.
     * @return The date component of the timestamp or an empty string if an error occurs during extraction.
     */
    private static String extractDateComponent(String timestamp) {
        try {
            return timestamp.length() >= 10 ? timestamp.substring(0, 10) : timestamp;
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to extract date part from timestamp: " + timestamp, e);
            return "";
        }
    }

    /**
     * Finds the start index of log entries for a specific date within a list of log lines.
     * Uses binary search to efficiently find the position where the specified date starts.
     *
     * @param logLines A list of log lines from the cookie log file.
     * @param date The date for which to find the start index, in "yyyy-MM-dd" format.
     * @return The start index for the specified date, or -1 if the date is not found.
     */
    public static int findStartIndex(List<String> logLines, String date) {
        int low = 0;
        int high = logLines.size() - 1;
        int startIndex = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            String midDate = extractDateComponent(logLines.get(mid).split(",")[1]);

            if (midDate.compareTo(date) < 0) {
                high = mid - 1;
            } else if (midDate.compareTo(date) > 0) {
                low = mid + 1;
            } else {
                startIndex = mid;
                high = mid - 1;
            }
        }

        return startIndex;
    }

    /**
     * Finds the end index of log entries for a specific date within a list of log lines.
     * Uses binary search to efficiently find the position where the specified date ends.
     *
     * @param logLines A list of log lines from the cookie log file.
     * @param date The date for which to find the end index, in "yyyy-MM-dd" format.
     * @return The end index for the specified date, or -1 if the date is not found.
     */
    public static int findEndIndex(List<String> logLines, String date) {
        int low = 0;
        int high = logLines.size() - 1;
        int endIndex = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            String midDate = extractDateComponent(logLines.get(mid).split(",")[1]);

            if (midDate.compareTo(date) < 0) {
                high = mid - 1;
            } else if (midDate.compareTo(date) > 0) {
                low = mid + 1;
            } else {
                endIndex = mid;
                low = mid + 1;
            }
        }

        return endIndex;
    }
}
