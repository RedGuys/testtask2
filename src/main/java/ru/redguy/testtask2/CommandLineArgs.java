package ru.redguy.testtask2;

import org.apache.commons.cli.*;

public class CommandLineArgs {

    private CommandLine cmd;

    public CommandLineArgs(String[] args) {
        Options options = new Options();

        Option input = new Option("i", "input", true, "input file");
        input.setRequired(true);
        options.addOption(input);

        Option outputFormat = new Option("f", "output", true, "output format");
        options.addOption(outputFormat);

        Option sortingOrder = new Option("s", "sorting", true, "sorting order");
        options.addOption(sortingOrder);

        Option output = new Option("o", "output", true, "output file");
        options.addOption(output);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("sort", options);
            System.exit(1);
            return;
        }
    }

    public String getOutputFormat() {
        return cmd.getOptionValue("f", "txt");
    }

    public String getSortingOrder() {
        return cmd.getOptionValue("s", "asc");
    }

    public String getOutputFile() {
        return cmd.getOptionValue("o", "out.txt");
    }

    public String getInputFile() {
        return cmd.getOptionValue("i");
    }
}
