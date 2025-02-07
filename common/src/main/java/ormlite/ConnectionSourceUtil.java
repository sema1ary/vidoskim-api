package ormlite;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
@SuppressWarnings("unused")
public class ConnectionSourceUtil {
    @SneakyThrows
    public JdbcPooledConnectionSource connectSqlite(@NonNull String filePath) {
        JdbcPooledConnectionSource connectionSource = new JdbcPooledConnectionSource(
                "jdbc:sqlite:" + filePath);
        createModelDaoAndTable(connectionSource);
        return connectionSource;
    }

    @SneakyThrows
    public JdbcPooledConnectionSource connectMySQL(String host, String database, String user, String pass, @NonNull Class<?>... modelClasses) {
        JdbcPooledConnectionSource connectionSource = new JdbcPooledConnectionSource(
                "jdbc:mysql://" + host + "/" + database + "?useSSL=true&autoReconnect=true");
        setUpTheConnection(connectionSource, user, pass);
        createModelDaoAndTable(connectionSource);

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

    @SneakyThrows
    public void closeConnection(JdbcPooledConnectionSource connectionSource) {
        if(connectionSource != null) connectionSource.close();
    }
}
