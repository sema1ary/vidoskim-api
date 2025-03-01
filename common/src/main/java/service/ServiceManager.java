package service;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class ServiceManager {
    private final static Map<Class<?>, Object> serviceMap = new HashMap<>();

    public static Object getService(Class<?> serviceClass) {
        Object object = serviceMap.get(serviceClass);
        if(object instanceof Service) {
            return object;
        }
        return null;
    }

    public static Object registerService(Class<?> serviceClass, Object service) {
        ((Service) service).enable();
        serviceMap.put(serviceClass, service);
        return getService(serviceClass);
    }
}
