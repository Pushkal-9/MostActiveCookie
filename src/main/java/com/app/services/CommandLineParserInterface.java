package com.app.services;

import com.app.objects.ParsedArguments;

public interface CommandLineParserInterface {
    ParsedArguments parse(String[] args);
}

