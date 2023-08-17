package com.product.star.homework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ContactReader {
    private static final String SEPARATOR = ",";

    public List<Contact> readFromFile1(Path filePath) {
        List<Contact> contactsList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(String.valueOf(filePath)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] field = line.split(SEPARATOR);
                Contact contact = new Contact(field[0], field[1], field[2], field[3]);
                contactsList.add(contact);
            }
        }
        catch (IOException e) { e.printStackTrace(); }
        return contactsList;
    }

    public List<Contact> readFromFile2(Path filePath) {
        try {
            return Files.lines(filePath)
                        .map(ContactReader::parseContact)
                        .collect(Collectors.toList());
        }
        catch (IOException e) { throw new RuntimeException(e); }
    }

    private static Contact parseContact(String line) {
        String[] field = line.split(SEPARATOR);
        return new Contact(field[0], field[1], field[2], field[3]);
    }
}