package com.product.star.homework;

import com.product.star.account.manager.Account;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ContactDao {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public ContactDao(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    public List<Contact> getAllContacts() {
        return namedJdbcTemplate.query(
                "SELECT * FROM CONTACT",
                (rs, i) -> new Contact(rs.getLong("ID"),
                                       rs.getString("NAME"),
                                       rs.getString("SURNAME"),
                                       rs.getString("EMAIL"),
                                       rs.getString("PHONE_NUMBER"))
        );
    }

    public void saveAll(Collection<Contact> contacts) {
        var args = contacts.stream()
                                    .map(contact -> new MapSqlParameterSource()
                                    .addValue("NAME", contact.getName())
                                    .addValue("SURNAME", contact.getSurname())
                                    .addValue("EMAIL", contact.getEmail())
                                    .addValue("PHONE_NUMBER", contact.getPhone()))
                                    .toArray(MapSqlParameterSource[]::new);
        namedJdbcTemplate.batchUpdate("INSERT INTO CONTACT(NAME, SURNAME, EMAIL, PHONE_NUMBER) " +
                                           "VALUES(:NAME, :SURNAME, :EMAIL, :PHONE_NUMBER)", args);
    }
}
