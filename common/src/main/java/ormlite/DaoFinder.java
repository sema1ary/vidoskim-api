package ormlite;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;

@SuppressWarnings("unused")
public interface DaoFinder {
    default <D extends Dao<T, ?>, T> D getDao(
            JdbcPooledConnectionSource connectionSource, Class<T> daoClass) {
        return DaoManager.lookupDao(connectionSource, daoClass);
    }
}
