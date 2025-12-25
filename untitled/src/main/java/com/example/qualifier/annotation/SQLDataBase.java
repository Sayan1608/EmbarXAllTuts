package com.example.qualifier.annotation;

import org.springframework.stereotype.Component;

@Component("sqlDataBase")
public class SQLDataBase implements DataBaseService{
    @Override
    public String getData() {
        return "Fetched data from SQL server";
    }
}
