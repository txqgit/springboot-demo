package org.spring.springboot.base;


import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseTest {
    private final Logger LOG = LoggerFactory.getLogger(BaseTest.class);

    @Before
    public void init() {
        LOG.info(">>>>>openMock invoked>>>>>");
        MockitoAnnotations.openMocks(this);
    }
}
