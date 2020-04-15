package com.bindy.poc.route;

import com.bindy.poc.dao.Employee;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.spi.DataFormat;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PojoMarshalRoute  extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        DataFormat dataFormat = new BindyCsvDataFormat(Employee.class);
        List<Employee> employees = initData();

        from("{{inputPath}}")
                .routeId("pojoToCSVroute")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getIn().setBody(employees);
                    }
                })
                .log(LoggingLevel.INFO, "${body}")
                .marshal(dataFormat)
                .to("{{outputPath}}")
                ;
    }

    public static List<Employee> initData(){

        List<Employee> employees = new ArrayList<>();
        Employee emp = new Employee();
        Employee emp1 = new Employee();
        Employee emp2 = new Employee();
        Employee emp3 = new Employee();

        emp.setEmpId("111");
        emp.setFirstName("Ram");
        emp.setLastName("Pal");
        emp.setRole("QA");
        employees.add(emp);
        //----
        emp1.setEmpId("222");
        emp1.setFirstName("Raj");
        emp1.setLastName("Kumar");
        emp1.setRole("CA");
        employees.add(emp1);
        //----
        emp2.setEmpId("333");
        emp2.setFirstName("Amit");
        emp2.setLastName("rao");
        emp2.setRole("SSE");
        employees.add(emp2);
        //----
        emp3.setEmpId("444");
        emp3.setFirstName("Raka");
        emp3.setLastName("Loki");
        emp3.setRole("AVP");
        employees.add(emp3);

        return employees;
    }
}
