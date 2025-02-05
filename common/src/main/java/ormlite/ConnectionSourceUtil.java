package ormlite;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
@SuppressWarnings("unused")
public class ConnectionSourceUtil {
    @SneakyThrows
    public JdbcPooledConnectionSource connect(String host, String database, String user, String pass, @NonNull Class<?>... modelClasses) {

        JdbcPooledConnectionSource connectionSource = new JdbcPooledConnectionSource(
           "jdbc:mysql://" + host + "/" + database + "?useSSL=true&autoReconnect=true");
        connectionSource.setUsername(user);
        connectionSource.setPassword(pass);
        connectionSource.setMaxConnectionsFree(5);

        TableCreatorUtil.create(connectionSource, modelClasses);
        DaoCreatorUtil.create(connectionSource, modelClasses);

        return connectionSource;
    }

    @SneakyThrows
    public void closeConnection(JdbcPooledConnectionSource connectionSource) {
        connectionSource.close();
    }

    private  <D extends Dao<T, ?>, T> D getDao(JdbcPooledConnectionSource connectionSource, Class<T> daoClass) {
        return DaoManager.lookupDao(connectionSource, daoClass);
    }
}
