package ru.itis.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.itis.models.Account;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class AccountsRepositoryJdbcTemplateImpl implements AccountsRepository {

    //language=SQL
    private static final String SQL_SELECT_ALL = "select * from account order by id";

    //language=SQL
    private static final String SQL_FIND_BY_LOGIN_AND_PASSWORD = "select * from account where login = ? and password = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public AccountsRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<Account> accountRowMapper = (row, rowNumber) -> {
        int id = row.getInt("id");
        String firstName = row.getString("first_name");
        String lastName = row.getString("last_name");
        int age = row.getInt("age");

        return new Account(id, firstName, lastName, age);
    };

    @Override
    public List<Account> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, accountRowMapper);
    }


    @Override
    public Optional<Account> findByLoginAndPassword(String login, String password) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_BY_LOGIN_AND_PASSWORD, accountRowMapper, login, password));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
