package com.bindy.poc;

import com.bindy.poc.route.CSVUnmarshalRoute;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpoints;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = CSVUnmarshalRoute.class)
@MockEndpoints
public class CSVUnmarshalRoutTest {

   @Produce(uri="file:src/test/resources/input?noop=true")
    private ProducerTemplate producerTemplate;

    @EndpointInject(uri = "mock:output")
    private MockEndpoint mockCamel;

    @Test
    public void test() throws InterruptedException {
        String body = "Camel";
//        mockCamel.expectedMessageCount(1);

        producerTemplate.sendBody(body);

        mockCamel.assertIsSatisfied();
    }
}
