package com.bindy.poc.service;

import org.apache.camel.Handler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class IncrementalService
{
    @Value("${incrementBy}")
    private int increaseByNum;

    @Handler
    public Integer addNumber(Integer value)
    {
        return new Integer(value.intValue() + increaseByNum);
    }
}
