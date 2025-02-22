package ormlite;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import ormlite.enums.NoSQLType;
import ormlite.enums.SQLType;

@UtilityClass
class ConnectionSourceFactory {
    @SneakyThrows
    public JdbcPooledConnectionSource connectSQLDatabase(SQLType type, String host, String database, String user,
                                                         String pass, @NonNull Class<?>... modelClasses) {
        String driver;
        String params;

        switch (type) {
            default -> {
                driver = "mysql";
                params = "?useSSL=true&autoReconnect=true";
            }
            case MARIADB -> {
                driver = "mariadb";
                params = "?sslMode=trust&autoReconnect=true";
            }
            case POSTGRESQL -> {
                driver = "postgresql";
                params = "?sslMode=trust&autoReconnect=true";
            }
        }

        JdbcPooledConnectionSource connectionSource = new JdbcPooledConnectionSource(
                "jdbc:" + driver + "://" + host + "/" + database + params);
        setUpTheConnection(connectionSource, user, pass);
        createModelDaoAndTable(connectionSource, modelClasses);
        return connectionSource;
    }

    @SneakyThrows
    public JdbcPooledConnectionSource connectNoSQLDatabase(@NonNull NoSQLType type, @NonNull String filePath,
                                                           @NonNull Class<?>... modelClasses) {
        String driver;
        if (type.equals(NoSQLType.H2)) {
            driver = "h2";
        } else {
            driver = "sqlite";
        }

        JdbcPooledConnectionSource connectionSource = new JdbcPooledConnectionSource(
                "jdbc:" + driver + ":" + filePath);
        createModelDaoAndTable(connectionSource, modelClasses);
        return connectionSource;
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
}
