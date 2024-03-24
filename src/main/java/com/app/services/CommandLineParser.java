package com.app.services;

import com.app.objects.ParsedArguments;

public interface CommandLineParser {
    ParsedArguments parse(String[] args);
}

