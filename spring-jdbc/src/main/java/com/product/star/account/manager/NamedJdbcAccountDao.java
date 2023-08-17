package com.product.star.account.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public class NamedJdbcAccountDao implements AccountDao {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public NamedJdbcAccountDao(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
    }


    @Override
    public Account addAccount(long id, long amount) {
        namedJdbcTemplate.update(
                "INSERT INTO ACCOUNT(ID, AMOUNT) VALUES(:id, :amount)",
                new MapSqlParameterSource()
                    .addValue("id", id)
                    .addValue("amount", amount)
        );
        return new Account(id, amount);
    }

    @Override
    public Account addAccount(long amount) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedJdbcTemplate.update(
                "INSERT INTO ACCOUNT(AMOUNT) VALUES(:amount)",
                new MapSqlParameterSource("amount", amount),
                keyHolder,
                new String[] { "id" }
        );

        var accountId = keyHolder.getKey().longValue();
        return new Account(accountId, amount);
    }


    @Override
    public Account getAccount(long accountId) {
        return namedJdbcTemplate.queryForObject(
                "SELECT ID, AMOUNT FROM ACCOUNT WHERE ID = :id",
                new MapSqlParameterSource("id", accountId),
                (rs, i) -> new Account(rs.getLong("ID"), rs.getLong("AMOUNT"))
        );
    }

    @Override
    public void setAmount(long accountId, long amount) {
        namedJdbcTemplate.update(
                "UPDATE ACCOUNT SET AMOUNT = :amount WHERE ID = :id",
                new MapSqlParameterSource()
                        .addValue("id", accountId)
                        .addValue("amount", amount)
        );
    }

    @Override
    public List<Account> getAllAccounts() {
        return namedJdbcTemplate.query(
                "SELECT ID, AMOUNT FROM ACCOUNT",
                (rs, i) -> new Account(rs.getLong("ID"), rs.getLong("AMOUNT"))
        );
    }
}
