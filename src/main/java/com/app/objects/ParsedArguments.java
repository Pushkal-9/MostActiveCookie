package com.app.objects;

public class ParsedArguments {

    private final String filePath;
    private final String date;

    public ParsedArguments(String filePath, String date) {
        this.filePath = filePath;
        this.date = date;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getDate() {
        return date;
    }
}

