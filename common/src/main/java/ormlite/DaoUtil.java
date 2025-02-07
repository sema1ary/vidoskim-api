package ormlite;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
@SuppressWarnings("unused")
public class DaoUtil {
    @SneakyThrows
    void create(@NonNull JdbcPooledConnectionSource connectionSource, @NonNull Class<?>... modelClasses) {
        for(Class<?> modelClass: modelClasses) {
            DaoManager.createDao(connectionSource, modelClass);
        }
    }

    public <D extends Dao<T, ?>, T> D getDao(JdbcPooledConnectionSource connectionSource, Class<T> daoClass) {
        return DaoManager.lookupDao(connectionSource, daoClass);
    }
}
