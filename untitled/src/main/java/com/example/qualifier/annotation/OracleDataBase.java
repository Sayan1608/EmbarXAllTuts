package com.example.qualifier.annotation;

import org.springframework.stereotype.Component;

@Component("oracle")
public class OracleDataBase implements DataBaseService{
    @Override
    public String getData() {
        return "Fetched data from Oracle server";
    }
}
