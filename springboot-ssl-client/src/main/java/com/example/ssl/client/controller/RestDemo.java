package com.example.ssl.client.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.Collections;

@Slf4j
@RestController
public class RestDemo {
    public final String LOCAL_SERVICE_URL = "https://localhost:1443/api/anno/success";
    public final String REMOTE_SERVICE_URL = "https://www.baidu.com/";

    @Autowired()
    @Qualifier("restTemplateTrustSelfCA")
    private RestTemplate restTemplate;

    @GetMapping("/trigger-remote")
    public int triggerHttpsRemote() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(
                REMOTE_SERVICE_URL, String.class, Collections.emptyMap());
        log.info("response of {}: statusCode={}", REMOTE_SERVICE_URL, response.getStatusCode());
        return response.getStatusCodeValue();
    }

    @GetMapping("/trigger-local")
    public int triggerHttpsLocal() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(
                LOCAL_SERVICE_URL, String.class, Collections.emptyMap());
        log.info("response of {}: statusCode={}", LOCAL_SERVICE_URL, response.getStatusCode());
        return response.getStatusCodeValue();
    }
}
