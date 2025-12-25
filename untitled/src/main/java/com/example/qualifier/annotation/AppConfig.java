package com.example.qualifier.annotation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.example.qualifier.annotation")
public class AppConfig {
}
