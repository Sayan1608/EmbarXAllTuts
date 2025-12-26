package com.sayan.firstapi.controller;

import com.sayan.firstapi.response.HelloResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping(path = "/hello")
    public HelloResponse sayHello(){
        return new HelloResponse("Hello World!");
    }

    @PostMapping(path = "/hello")
    public HelloResponse getPost(@RequestBody String name){
        return new HelloResponse("Hello "+name + "!");
    }
}
