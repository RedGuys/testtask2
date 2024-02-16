package ru.redguy.testtask2;

import java.io.IOException;

public class TxtWriter extends Writer{
    public TxtWriter(String outputFile) throws IOException {
        super(outputFile);
    }

    @Override
    protected void writeLine(String line) throws IOException {
        _write(line);
        _write("\n");
    }
}
