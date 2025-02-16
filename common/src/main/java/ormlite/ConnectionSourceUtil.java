package ormlite;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
@SuppressWarnings("unused")
public class ConnectionSourceUtil {
    @SneakyThrows
    public JdbcPooledConnectionSource connectSqlite(@NonNull String filePath, @NonNull Class<?>... modelClasses) {
        JdbcPooledConnectionSource connectionSource = new JdbcPooledConnectionSource(
                "jdbc:sqlite:" + filePath);
        createModelDaoAndTable(connectionSource, modelClasses);
        return connectionSource;
    }

    @SneakyThrows
    private JdbcPooledConnectionSource connectSQLDatabase(SQLType type,
            String host, String database, String user, String pass, @NonNull Class<?>... modelClasses) {

        String driver;
        String params;

        if(type.equals(SQLType.MYSQL)) {
            driver = "mysql";
            params = "?useSSL=true&autoReconnect=true";
        } else {
            driver = "mariadb";
            params = "?sslMode=trust&autoReconnect=true";
        }

        JdbcPooledConnectionSource connectionSource = new JdbcPooledConnectionSource(
                "jdbc:" + driver + "://" + host + "/" + database + params);
        setUpTheConnection(connectionSource, user, pass);
        createModelDaoAndTable(connectionSource, modelClasses);
        return connectionSource;
    }

    @SneakyThrows
    public JdbcPooledConnectionSource connectMySQL(String host, String database
            , String user, String pass, @NonNull Class<?>... modelClasses) {
        return connectSQLDatabase(SQLType.MYSQL, host, database, user, pass, modelClasses);
    }

    @SneakyThrows
    public JdbcPooledConnectionSource connectMariaDB(String host, String database
            , String user, String pass, @NonNull Class<?>... modelClasses) {
        return connectSQLDatabase(SQLType.MARIADB, host, database, user, pass, modelClasses);
    }

    private void setUpTheConnection(@NonNull JdbcPooledConnectionSource connectionSource, String user, String pass) {
        connectionSource.setUsername(user);
        connectionSource.setPassword(pass);
        connectionSource.setMaxConnectionsFree(5);
    }

    private void createModelDaoAndTable(@NonNull JdbcPooledConnectionSource connectionSource, @NonNull Class<?>... modelClasses) {
        TableCreatorUtil.create(connectionSource, modelClasses);
        DaoUtil.create(connectionSource, modelClasses);
    }

    @SneakyThrows
    public void closeConnection(JdbcPooledConnectionSource connectionSource) {
        if(connectionSource != null) connectionSource.close();
    }
}
