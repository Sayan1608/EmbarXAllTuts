package com.example.qualifier.annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context
                = new AnnotationConfigApplicationContext(AppConfig.class);

        DataBaseManager dataBaseManager = context.getBean("dataBaseManager", DataBaseManager.class);
        System.out.println(dataBaseManager.getDataFromDb());
    }
}
