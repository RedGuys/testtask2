package ru.redguy.testtask2;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    private static final int MEMORY_LIMIT = 1024 * 1024;
    private static final int THREAD_COUNT = 10;

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        CommandLineArgs commandLineArgs = new CommandLineArgs(args);

        Comparator<String> comparator = commandLineArgs.getSortingOrder().equals("asc") ? Comparator.naturalOrder() : Comparator.reverseOrder();

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        List<Future<File>> futures = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(commandLineArgs.getInputFile()))) {
            List<String> lines = new ArrayList<>();
            String line;
            int maxLines = MEMORY_LIMIT / 50;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
                if (lines.size() == maxLines) {
                    List<String> finalLines = lines;
                    Future<File> future = executorService.submit(() -> writeToTempFile(finalLines, comparator));
                    futures.add(future);
                    lines = new ArrayList<>();
                }
            }
            if (!lines.isEmpty()) {
                List<String> finalLines = lines;
                Future<File> future = executorService.submit(() -> writeToTempFile(finalLines, comparator));
                futures.add(future);
            }
        }

        executorService.shutdown();

        List<File> tempFiles = new ArrayList<>();
        for (Future<File> future : futures) {
            tempFiles.add(future.get());
        }

        mergeFiles(tempFiles, comparator, commandLineArgs);
    }

    private static File writeToTempFile(List<String> lines, Comparator<String> comparator) throws IOException {
        if(lines.contains(null)) throw new IllegalArgumentException("lines contains null");
        lines.sort(comparator);
        File tempFile = Files.createTempFile("sort", "tmp").toFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
        return tempFile;
    }

    private static void mergeFiles(List<File> files, Comparator<String> comparator, CommandLineArgs commandLineArgs) throws IOException {
        PriorityQueue<QueueItem> queue = new PriorityQueue<>(Comparator.comparing((QueueItem item) -> item.line, comparator));
        List<BufferedReader> readers = new ArrayList<>();

        for (File file : files) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            readers.add(reader);
            String line = reader.readLine();
            if (line != null) {
                queue.add(new QueueItem(line, reader));
            }
        }

        Writer writer = WriterFactory.createWriter(commandLineArgs);
        while (!queue.isEmpty()) {
            QueueItem item = queue.poll();
            writer.writeLine(item.line);
            String line = item.reader.readLine();
            if (line != null) {
                queue.add(new QueueItem(line, item.reader));
            }
        }

        writer.close();
        for (BufferedReader reader : readers) {
            reader.close();
        }
    }

    private static class QueueItem {
        String line;
        BufferedReader reader;

        QueueItem(String line, BufferedReader reader) {
            this.line = line;
            this.reader = reader;
        }
    }
}