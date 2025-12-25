package com.example.qualifier.annotation;

import org.springframework.stereotype.Component;

@Component("postgres")
public class PostgresDataBase implements DataBaseService{
    @Override
    public String getData() {
        return "Fetched data from Postgres server";
    }
}
