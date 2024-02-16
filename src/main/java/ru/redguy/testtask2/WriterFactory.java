package ru.redguy.testtask2;

import java.io.IOException;

public class WriterFactory {
    public static Writer createWriter(CommandLineArgs args) throws IOException {
        switch (args.getOutputFormat()) {
            case "json":
                return new JsonWriter(args.getOutputFile());
            case "txt":
                return new TxtWriter(args.getOutputFile());
            case "xml":
                return new XmlWriter(args.getOutputFile());
            default:
                throw new IllegalArgumentException("Unknown format: " + args.getOutputFormat());
        }
    }
}
