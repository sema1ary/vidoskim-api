package ormlite;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ormlite.model.TestUser;

public class ConnectionSourceUtilTest {
    @Test
    void connectMySQL() {
        JdbcPooledConnectionSource connectionSource = ConnectionSourceUtil.connectMySQL(
                "sql.freedb.tech:3306",
                "freedb_plugins_test",
                "freedb_plugin_tester",
                "2WJAHY$f!#Hgjma",
                TestUser.class
        );

        Assertions.assertNotNull(connectionSource);
    }

    @Test
    void connectMariaDB() {
        JdbcPooledConnectionSource connectionSource = ConnectionSourceUtil.connectMariaDB(
                "sql.freedb.tech:3306",
                "freedb_plugins_test",
                "freedb_plugin_tester",
                "2WJAHY$f!#Hgjma",
                TestUser.class
        );

        Assertions.assertNotNull(connectionSource);
    }

    @Test
    void connectPostgreSQL() {
        JdbcPooledConnectionSource connectionSource = ConnectionSourceUtil.connectPostgreSQL(
                "dpg-curjcj9u0jms73bv3i10-a.oregon-postgres.render.com:5432",
                "vidoskim_api_test_postgres",
                "vidoskim_api_test_postgres_user",
                "uIvWOTNs23nOiWIIxK1NRIWLxpFYEmgL",
                TestUser.class);

        Assertions.assertNotNull(connectionSource);
    }

    @Test
    void connectSqlite() {
        JdbcPooledConnectionSource connectionSource = ConnectionSourceUtil.connectSqlite(
                "src/test/resources/test_db.sqlite", TestUser.class);

        Assertions.assertNotNull(connectionSource);
    }
}
