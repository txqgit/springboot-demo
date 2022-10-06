package com.example.demorest.controller;

import com.example.common.advice.annotation.ComRestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@ComRestController
@RequestMapping("/api/anno")
public class RestAnno {

    @GetMapping("/success")
    public String GetSuccess() {
        return "hello, don!";
    }

    @GetMapping("/error")
    public int GetError() {
        return 6/0;
    }
}
