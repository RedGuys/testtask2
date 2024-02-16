package ru.redguy.testtask2;

import java.io.FileWriter;
import java.io.IOException;

public abstract class Writer {
    protected String outputFile;
    private final FileWriter fileWriter;

    public Writer(String outputFile) throws IOException {
        this.outputFile = outputFile;
        this.fileWriter = new FileWriter(outputFile);
    }
    protected abstract void writeLine(String line) throws IOException;

    protected void _write(String line) throws IOException {
        fileWriter.write(line);
    }

    public void close() throws IOException {
        fileWriter.close();
    };
}
