package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.impl.TestServiceImpl;

public class ServiceUtilTest {
    @Test
    void registerService() {
        TestService testService = (TestService) ServiceUtil.registerService(TestService.class, new TestServiceImpl());

        Assertions.assertNotNull(testService);
    }
}
