package com.bindy.poc.route;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;

@Component
public class SimpleRoute  extends RouteBuilder {

    @Value("${incrementBy}")
    private int increaseByNum;

    @Override
    public void configure() throws Exception {

        from("{{inputPath}}")
                .routeId("SimpleRoute")
                .log("BODY  :: ${body}")
                .setProperty("RowCount", (simple(String.valueOf(increaseByNum + Integer.parseInt("100")))))

                .log(LoggingLevel.INFO, simple("${bean:incrementalService?method=addNumber(property.RowCount)}").toString())
                .setProperty("propCount", simple("${bean:incrementalService?method=addNumber(property.RowCount)}"))  //number Increment by given num

                .log(LoggingLevel.INFO, "headerRow :: ${header.headerRow} ")
                .log(LoggingLevel.INFO, "RowCount :: ${property.RowCount}  ::  ${property.propCount} ")
        .to("mock:end") //{{outputPath}}")
        ;
    }
}
