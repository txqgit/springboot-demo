package org.spring.springboot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/concurrent/request")
public class MultiConcurrentRequest {
    @RequestMapping(value = "/multi", method = RequestMethod.GET)
    public ResponseEntity<Map<String, String>> dummytsp(@RequestParam(value="msg", defaultValue="Hello") String msg) {
        System.out.println("" + new Date() + " : start : ThreadId " + Thread.currentThread().getId() + " : param : " + msg);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("" + new Date() + " : end : ThreadId " + Thread.currentThread().getId() + " : param : " + msg);
        Map<String, String> response = new HashMap<>();
        response.put("message", msg);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
