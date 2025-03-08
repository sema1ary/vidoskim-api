package service;

@SuppressWarnings("unused")
public interface ServiceGetter {
    default <T> T getService(Class<T> clazz) {
        return ServiceManager.getService(clazz);
    }
}
