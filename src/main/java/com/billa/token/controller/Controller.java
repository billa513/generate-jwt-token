package com.billa.token.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/sayHello/{name}")
    public String sayHello(@PathVariable String name){
        return "Hello "+name;
    }
}
