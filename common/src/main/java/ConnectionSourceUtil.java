import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;

@Getter
@SuppressWarnings("unused")
public class ConnectionSourceUtil {
    private JdbcPooledConnectionSource connectionSource;

    @SneakyThrows
    public void connect(@NonNull String host, @NonNull String database, @NonNull String user, @NonNull String pass) {
        connectionSource = new JdbcPooledConnectionSource(
                "jdbc:mysql://" + host + "/" + database + "?useSSL=true&autoReconnect=true");
        connectionSource.setUsername(user);
        connectionSource.setPassword(pass);
        connectionSource.setMaxConnectionsFree(5);
    }

    @SneakyThrows
    public void closeConnection() {
        connectionSource.close();
    }
}
