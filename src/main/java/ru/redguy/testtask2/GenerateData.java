package ru.redguy.testtask2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GenerateData {
    public static void main(String[] args) throws IOException {
        FileWriter writer = new FileWriter("data.txt");
        Random random = new Random();
        for (int i = 0; i < 61_000_000; i++) {
            for (int j = random.nextInt(50); j < 50; j++) {
                writer.write((char) (random.nextInt(94) + 33));
            }
            writer.write('\n');
        }
        writer.close();
    }
}
