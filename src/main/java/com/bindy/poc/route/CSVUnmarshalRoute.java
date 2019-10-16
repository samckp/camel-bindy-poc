package com.bindy.poc.route;

import com.bindy.poc.dao.Employee;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.spi.DataFormat;
import org.springframework.stereotype.Component;

@Component
public class CSVUnmarshalRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        DataFormat dataFormat = new BindyCsvDataFormat(Employee.class);

        from("file:src/test/resources/input?noop=true")
                .routeId("csvUnmarshallId")
                .unmarshal(dataFormat)
                .log(LoggingLevel.INFO, "${body}")
                .to("direct:output")
                ;
    }
}
