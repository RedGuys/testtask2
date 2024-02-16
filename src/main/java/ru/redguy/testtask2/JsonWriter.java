package ru.redguy.testtask2;

import java.io.IOException;

public class JsonWriter extends Writer {
    private String previousLine = null;
    public JsonWriter(String outputFile) throws IOException {
        super(outputFile);
        _write("{");
    }

    @Override
    protected void writeLine(String line) throws IOException {
        if (previousLine != null) {
            _write("\n\t\"");
            _write(previousLine);
            _write("\",");
        }
        previousLine = line;
    }

    @Override
    public void close() throws IOException {
        if (previousLine != null) {
            _write("\n\t\"");
            _write(previousLine);
            _write("\"");
        }
        _write("\n}");
        super.close();
    }
}
