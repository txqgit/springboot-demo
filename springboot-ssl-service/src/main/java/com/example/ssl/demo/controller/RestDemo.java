package com.example.ssl.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/anno")
public class RestDemo {
    @GetMapping("/success")
    public String GetSuccess() {
        return "hello, don!";
    }

    @GetMapping("/error")
    public int GetError() {
        return 6/0;
    }
}
