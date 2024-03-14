package com.app;

import com.app.services.CookieLogService;

/**
 * The entry point class for the MostActiveCookie application.
 * This application identifies and displays the most active cookies from a log file for a given date.
 * The command line arguments are expected to include the file path of the log file and the target date.
 */
public class MostActiveCookie {

    /**
     * Main method to run the MostActiveCookie application.
     * It initializes the {@link CookieLogService} and passes the command line arguments to it
     * for further processing to find and display the most active cookies for the specified date.
     *
     * @param args Command line arguments passed to the application, expected to contain the file path and the date.
     */
    public static void main(String[] args) {
        CookieLogService cookieLogService = new CookieLogService();
        cookieLogService.processCookies(args);
    }
}
