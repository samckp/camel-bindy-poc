package com.bindy.poc.route;

import com.bindy.poc.dao.Employee;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.spi.DataFormat;
import org.springframework.stereotype.Component;

@Component
public class PojoMarshalRoute  extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        DataFormat dataFormat = new BindyCsvDataFormat(Employee.class);
        Employee emp = new Employee();
        emp.setEmpId("111");
        emp.setFirstName("Ram");
        emp.setLastName("Pal");
        emp.setRole("QA");

        from("{{inputPath}}")
                .routeId("pojoToCSVroute")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getIn().setBody(emp);
                    }
                })
                .log(LoggingLevel.INFO, "${body}")
                .marshal(dataFormat)
                .to("{{outputPath}}")
                ;
    }
}
