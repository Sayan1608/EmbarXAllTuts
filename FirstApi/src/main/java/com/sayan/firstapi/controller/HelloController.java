package com.sayan.firstapi.controller;

import com.sayan.firstapi.response.HelloResponse;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @GetMapping(path = "/hello/{name}/show")
    public HelloResponse helloPath(@PathVariable String name){
        return new HelloResponse("Hello, "+name);
    }

    @GetMapping(path = "/hello")
    public HelloResponse sayHello(){
        return new HelloResponse("Hello World!");
    }

    @PostMapping(path = "/hello")
    public HelloResponse getPost(@RequestBody String name){
        return new HelloResponse("Hello "+name + "!");
    }
}
