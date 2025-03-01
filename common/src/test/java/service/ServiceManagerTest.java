package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.impl.TestServiceImpl;

public class ServiceManagerTest {
    @Test
    void registerService() {
        TestService testService = (TestService) ServiceManager.registerService(TestService.class, new TestServiceImpl());

        Assertions.assertNotNull(testService);
    }
}
