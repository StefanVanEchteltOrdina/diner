package com.cafe.diner.controller;

import com.cafe.diner.DinerApplication;
import com.cafe.diner.config.DinerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(classes = DinerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = {DinerConfig.class})
public class BaseIT {

    @Autowired
    protected RestTemplate restTemplate;

    protected MockRestServiceServer mockRestServiceServer;

    @BeforeEach
    public void setup() {
        this.mockRestServiceServer = MockRestServiceServer.bindTo(restTemplate).build();
    }
}
