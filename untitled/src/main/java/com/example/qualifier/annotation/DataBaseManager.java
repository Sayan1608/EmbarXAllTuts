package com.example.qualifier.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class DataBaseManager {
    private final DataBaseService dataBaseService;

    @Autowired
    public DataBaseManager(@Qualifier("oracle")DataBaseService dataBaseService) {
        this.dataBaseService = dataBaseService;
    }

    public String getDataFromDb(){
        return dataBaseService.getData();
    }
}
