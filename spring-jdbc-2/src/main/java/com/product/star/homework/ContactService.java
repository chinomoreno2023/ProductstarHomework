package com.product.star.homework;

import java.nio.file.Path;
import java.util.List;

public class ContactService {

    private final ContactDao contactDao;
    private final ContactReader contactReader;

    public ContactService(ContactDao contactDao, ContactReader contactReader) {
        this.contactDao = contactDao;
        this.contactReader = contactReader;
    }

    public void saveContacts(Path filePath) {
        var contacts = contactReader.readFromFile1(filePath);
        contactDao.saveAll(contacts);
    }

    public List<Contact> getContacts() {
        return contactDao.getAllContacts();
    }
}
