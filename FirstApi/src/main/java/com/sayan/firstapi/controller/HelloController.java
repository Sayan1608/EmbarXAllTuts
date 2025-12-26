package com.sayan.firstapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping(path = "/hello")
    public String sayHello(){
        return "Hello World!";
    }

    @PostMapping(path = "/hello")
    public String getPost(@RequestBody String name){
        return "Hello "+name + "!";
    }
}
