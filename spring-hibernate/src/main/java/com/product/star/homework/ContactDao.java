package com.product.star.homework;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ContactDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public ContactDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Contact> getAllContacts() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            return session.createQuery("FROM Contact c", Contact.class)
                    .getResultList();
        }
    }

    public Contact getContact(long contactId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Contact.class, contactId);
        }
    }

    public long addContact(Contact contact) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            long contactId = (long) session.save(contact);
            session.getTransaction().commit();
            return contactId;
        }
    }

    public void updatePhoneNumber(long contactId, String phoneNumber) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Contact contact = session.get(Contact.class, contactId);
            if (contact != null) {
                contact.setPhone(phoneNumber);
                session.update(contact);
                session.getTransaction().commit();
            }
        }
    }

    public void updateEmail(long contactId, String email) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Contact contact = session.get(Contact.class, contactId);
            if (contact != null) {
                contact.setEmail(email);
                session.update(contact);
                session.getTransaction().commit();
            }
        }
    }

    public void deleteContact(long contactId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Contact contact = session.get(Contact.class, contactId);
            if (contact != null) {
                session.delete(contact);
                session.getTransaction().commit();
            }
        }
    }
}