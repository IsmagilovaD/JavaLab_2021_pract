import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.ArrayList;

public class PasswordRepositoryJdbcTemplateImpl implements PasswordBlackList {

    //language=SQL
    private static final String SQL_FIND_ALL = "select password from password_black_list";

    private final JdbcTemplate jdbcTemplate;

    public PasswordRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<String> passwordRowMapper = (row, rowNumber) -> row.getString("password");

    @Override
    public boolean contains(String password) {
        ArrayList<String> passwords = new ArrayList<>(jdbcTemplate.query(SQL_FIND_ALL, passwordRowMapper));
        return passwords.contains(password);
    }
}
