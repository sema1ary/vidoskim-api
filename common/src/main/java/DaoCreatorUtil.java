import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
@SuppressWarnings("unused")
public class DaoCreatorUtil {
    @SneakyThrows
    public void create(@NonNull JdbcPooledConnectionSource connectionSource, @NonNull Class<?>... modelClasses) {
        for(Class<?> modelClass: modelClasses) {
            DaoManager.createDao(connectionSource, modelClass);
        }
    }
}
