package com.example.common.advice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo/original")
public class RestDemo {

    @GetMapping("/hello")
    public String Hello() {
        return "hello, world!";
    }

    @GetMapping("/wrong")
    public int Wrong() {
        return 3/0;
    }
}
