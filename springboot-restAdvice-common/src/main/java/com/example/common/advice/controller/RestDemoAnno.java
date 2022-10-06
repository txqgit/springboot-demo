package com.example.common.advice.controller;

import com.example.common.advice.annotation.ComRestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ComRestController
@RequestMapping("/api/demo/anno")
public class RestDemoAnno {

    @GetMapping("/hello")
    public String Hello() {
        return "hello, world!";
    }

    @GetMapping("/wrong")
    public int Wrong() {
        return 3/0;
    }
}
