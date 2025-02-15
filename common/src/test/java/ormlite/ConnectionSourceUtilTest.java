package ormlite;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ormlite.model.TestUserModel;

public class ConnectionSourceUtilTest {
    @Test
    void connectMySQL() {
        JdbcPooledConnectionSource connectionSource = ConnectionSourceUtil.connectMySQL(
                "sql.freedb.tech:3306",
                "freedb_marry-test",
                "freedb_vidoskim",
                "p7xB#b%As%Mhp$n"
        );

        Assertions.assertNotNull(connectionSource);
    }

    @Test
    void connectSqlite() {
        JdbcPooledConnectionSource connectionSource = ConnectionSourceUtil.connectSqlite(
                "src/test/resources/test_db.sqlite", TestUserModel.class);

        Assertions.assertNotNull(connectionSource);
    }
}
