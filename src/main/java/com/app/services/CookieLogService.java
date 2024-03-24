package com.app.services;

public interface CookieLogService {
    /**
     * Processes cookie data from a log file and performs operations such as identifying the most active cookie.
     *
     * @param args Command line arguments or parameters needed for processing.
     *             This might include the file path of the log and the date for which the analysis is to be performed.
     */
    void processCookies(String[] args);
}

