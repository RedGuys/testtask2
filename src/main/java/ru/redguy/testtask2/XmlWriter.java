package ru.redguy.testtask2;

import java.io.IOException;

public class XmlWriter extends Writer{
    public XmlWriter(String outputFile) throws IOException {
        super(outputFile);
        _write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        _write("\n<root>");
    }

    @Override
    protected void writeLine(String line) throws IOException {
        _write("\n\t<line>");
        _write(line);
        _write("</line>");
    }

    @Override
    public void close() throws IOException {
        _write("\n</root>");
        super.close();
    }
}
