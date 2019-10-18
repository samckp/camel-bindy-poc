package com.bindy.poc;

import com.bindy.poc.dao.Employee;
import com.bindy.poc.route.CSVUnmarshalRoute;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

public class CSVUnmarshalRouteTest extends CamelTestSupport {

	@Autowired
	ProducerTemplate producerTemplate;

	@Override
	public RouteBuilder createRouteBuilder(){

		return new CSVUnmarshalRoute();
	}

	@Test
	public void csvUnmarshalTest() throws InterruptedException {

		Exchange exchange = consumer.receive("direct:output");
		Thread.sleep(3000);
		List<Employee> emps = (List<Employee>) exchange.getIn().getBody();

		assertEquals("Ambarish", emps.get(0).getFirstName().trim().toString());
	}
}
