package com.bindy.poc.route;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;

@Component
public class SimpleRoute  extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("{{inputPath}}")
                .routeId("SimpleRoute")
                .log("BODY  :: ${body}")
                .split().tokenize("\n").streaming()
               .aggregationStrategy(new AggregationStrategy() {
                    @Override
                    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
                        if (newExchange.getProperty("CamelSplitIndex").toString().equalsIgnoreCase("0")
                                || newExchange.getProperty("CamelSplitComplete").toString().equals(true)) {

                            Object newbody = newExchange.getIn().getBody();
                            Map<String, Object> newHeaders = newExchange.getIn().getHeaders();

                            ArrayList list = null;

                            if (oldExchange == null) {
                                list = new ArrayList<Object>();
                                list.add(newbody);
                                newExchange.getIn().setBody(list);
                                return newExchange;
                            } else {
                                Map<String, Object> oldHeader = oldExchange.getIn().getHeaders();
                                oldHeader.putAll(newHeaders);
                                list = oldExchange.getIn().getBody(ArrayList.class);
                                list.add(newbody);
                                return oldExchange;
                            }
                        } else {
                            return oldExchange;
                        }
                    }
                })

                .choice()
                    .when(simple("${property.CamelSplitIndex} == 0"))
                        .convertBodyTo(String.class)
                        .setHeader("headerRow",simple("${bodyAs(String).trim()}"))

                    .when(simple("${property.CamelSplitComplete} == true"))
                        .log("BODY 1 :: ${body}")
                        .setHeader("footerRow",simple("${body}"))
                    .end()
                .end()

//                .log(LoggingLevel.INFO, "Body :: ${body}")
//                .log(LoggingLevel.INFO,"${property.CamelSplitSize}")
//                .log(LoggingLevel.INFO,"${property.CamelSplitComplete}")

                .log(LoggingLevel.INFO, "headerRow :: ${header.headerRow} ")
                .log(LoggingLevel.INFO, "footerRow :: ${header.footerRow} ")
        .to("mock:end") //{{outputPath}}")
        ;
    }
}
