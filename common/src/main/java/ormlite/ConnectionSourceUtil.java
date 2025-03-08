package ormlite;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import service.ServiceManager;

@UtilityClass
@SuppressWarnings("unused")
public class ConnectionSourceUtil {
    @SneakyThrows
    public JdbcPooledConnectionSource connectSQLDatabase(@NonNull String driver, String host, String database, String user,
                                                         String pass, @NonNull Class<?>... modelClasses) {
        String params;

        if(driver.equalsIgnoreCase("mariadb") || driver.equalsIgnoreCase("postgresql")) {
            params = "?sslMode=trust&autoReconnect=true";
        } else {
            params = "?useSSL=true&autoReconnect=true";
        }

        JdbcPooledConnectionSource connectionSource = new JdbcPooledConnectionSource(
                "jdbc:" + driver + "://" + host + "/" + database + params);
        setUpTheConnection(connectionSource, user, pass);
        createModelDaoAndTable(connectionSource, modelClasses);
        return connectionSource;
    }

    @SneakyThrows
    public JdbcPooledConnectionSource connectSQLDatabaseWithoutSSL(
            @NonNull String driver, String host, String database, String user, String pass, @NonNull Class<?>... modelClasses) {
        String params;

        if(driver.equalsIgnoreCase("mariadb") || driver.equalsIgnoreCase("postgresql")) {
            params = "&autoReconnect=true";
        } else {
            params = "?useSSL=false&autoReconnect=true";
        }

        JdbcPooledConnectionSource connectionSource = new JdbcPooledConnectionSource(
                "jdbc:" + driver + "://" + host + "/" + database + params);
        setUpTheConnection(connectionSource, user, pass);
        createModelDaoAndTable(connectionSource, modelClasses);
        return connectionSource;
    }

    @SneakyThrows
    public JdbcPooledConnectionSource connectNoSQLDatabase(@NonNull String driver, @NonNull String filePath,
                                                           @NonNull Class<?>... modelClasses) {
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

    @SneakyThrows
    public void closeConnection(boolean shouldDisableServices, JdbcPooledConnectionSource connectionSource) {
        if(shouldDisableServices) ServiceManager.disableServices();
        if(connectionSource != null) connectionSource.close();
    }
}
