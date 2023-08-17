package com.product.star.homework;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import java.util.List;

public class ContactDao {
    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public ContactDao(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    public List<Contact> getAllContacts() {
        return namedJdbcTemplate.query("SELECT * FROM CONTACT",
                (rs, i) -> new Contact(rs.getLong("ID"),
                                       rs.getString("NAME"),
                                       rs.getString("SURNAME"),
                                       rs.getString("EMAIL"),
                                       rs.getString("PHONE_NUMBER"))
        );
    }

    public Contact getContact(long contactId) {
        return namedJdbcTemplate.queryForObject("SELECT * FROM CONTACT WHERE ID = :ID",
                new MapSqlParameterSource("ID", contactId),
                (rs, i) -> new Contact(rs.getLong("ID"),
                                       rs.getString("NAME"),
                                       rs.getString("SURNAME"),
                                       rs.getString("EMAIL"),
                                       rs.getString("PHONE_NUMBER"))

        );
    }

    public long addContact(Contact contact) {
        namedJdbcTemplate.update("INSERT INTO CONTACT(NAME, SURNAME, EMAIL, PHONE_NUMBER) " +
                        "VALUES(:NAME, :SURNAME, :EMAIL, :PHONE_NUMBER)",
                new MapSqlParameterSource().addValue("NAME", contact.getName())
                                           .addValue("SURNAME", contact.getSurname())
                                           .addValue("EMAIL", contact.getEmail())
                                           .addValue("PHONE_NUMBER", contact.getPhone())
        );
        return 1;
    }

    public void updatePhoneNumber(long contactId, String phoneNumber) {
        namedJdbcTemplate.update("UPDATE CONTACT SET PHONE_NUMBER = :PHONE_NUMBER WHERE ID = :ID",
                new MapSqlParameterSource().addValue("ID", contactId)
                                           .addValue("PHONE_NUMBER", phoneNumber)
        );
    }

    public void updateEmail(long contactId, String email) {
        namedJdbcTemplate.update("UPDATE CONTACT SET EMAIL = :EMAIL WHERE ID = :ID",
                new MapSqlParameterSource().addValue("ID", contactId)
                                           .addValue("EMAIL", email)
        );
    }

    public void deleteContact(long contactId) {
        namedJdbcTemplate.update("DELETE FROM CONTACT WHERE ID = :ID",
                new MapSqlParameterSource().addValue("ID", contactId)
        );
    }
}