package com.example.demorest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/original")
public class RestOriginal {

    @GetMapping("/success")
    public String GetSuccess() {
        return "hello, don!";
    }

    @GetMapping("/error")
    public int GetError() {
        return 6/0;
    }
}
