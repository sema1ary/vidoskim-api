package ormlite;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
@SuppressWarnings("unused")
public class ConnectionSourceUtil {
    @SneakyThrows
    public JdbcPooledConnectionSource connect(@NonNull String host, @NonNull String database, @NonNull String user, @NonNull String pass) {
        JdbcPooledConnectionSource connectionSource = new JdbcPooledConnectionSource(
                "jdbc:mysql://" + host + "/" + database + "?useSSL=true&autoReconnect=true");
        connectionSource.setUsername(user);
        connectionSource.setPassword(pass);
        connectionSource.setMaxConnectionsFree(5);
        return connectionSource;
    }

    @SneakyThrows
    public void closeConnection(JdbcPooledConnectionSource connectionSource) {
        connectionSource.close();
    }
}
