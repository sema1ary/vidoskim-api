package ormlite;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import ormlite.enums.NoSQLType;
import ormlite.enums.SQLType;

@UtilityClass
@SuppressWarnings("unused")
public class ConnectionSourceUtil {
    @SneakyThrows
    public JdbcPooledConnectionSource connectSqlite(@NonNull String filePath, @NonNull Class<?>... modelClasses) {
        return ConnectionSourceFactory.connectNoSQLDatabase(NoSQLType.SQLITE, filePath, modelClasses);
    }

    @SneakyThrows
    public JdbcPooledConnectionSource connectH2(@NonNull String filePath, @NonNull Class<?>... modelClasses) {
        return ConnectionSourceFactory.connectNoSQLDatabase(NoSQLType.H2, filePath, modelClasses);
    }

    @SneakyThrows
    public JdbcPooledConnectionSource connectMySQL(String host, String database
            , String user, String pass, @NonNull Class<?>... modelClasses) {
        return ConnectionSourceFactory.connectSQLDatabase(SQLType.MYSQL, host, database, user, pass, modelClasses);
    }

    @SneakyThrows
    public JdbcPooledConnectionSource connectMariaDB(String host, String database
            , String user, String pass, @NonNull Class<?>... modelClasses) {
        return ConnectionSourceFactory.connectSQLDatabase(SQLType.MARIADB, host, database, user, pass, modelClasses);
    }

    @SneakyThrows
    public JdbcPooledConnectionSource connectPostgreSQL(String host, String database
            , String user, String pass, @NonNull Class<?>... modelClasses) {
        return ConnectionSourceFactory.connectSQLDatabase(SQLType.POSTGRESQL, host, database, user, pass, modelClasses);
    }

    @SneakyThrows
    public void closeConnection(JdbcPooledConnectionSource connectionSource) {
        if(connectionSource != null) connectionSource.close();
     }
}
