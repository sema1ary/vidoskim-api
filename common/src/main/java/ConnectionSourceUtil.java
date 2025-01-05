import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import lombok.NonNull;
import lombok.SneakyThrows;

@SuppressWarnings("unused")
public class ConnectionSourceUtil {
    private JdbcPooledConnectionSource connectionSource;

    @SneakyThrows
    private void connect(@NonNull String host, @NonNull String database, @NonNull String user, @NonNull String pass) {

        connectionSource = new JdbcPooledConnectionSource("jdbc:mysql://" + host + "/" + database + "?useSSL=true&autoReconnect=true");
        connectionSource.setUsername(user);
        connectionSource.setPassword(pass);
        connectionSource.setMaxConnectionsFree(5);
    }

    @SneakyThrows
    private void closeConnection() {
        connectionSource.close();
    }
}
