package com.example.demo.filetransmit.controller;

import com.sun.deploy.net.HttpResponse;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;

public class FileTransmitTest {
    private RestTemplate restTemplate;
    private final String URL_ROOT = "http://localhost:8080/rest";

    @Before
    public void init() {
        restTemplate = new RestTemplate();
    }

    @Test
    public void FormDataTest() {
        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap();
        requestBody.add("file_excel", new FileSystemResource(new File("E:\\dataJava\\data\\city_info.xlsx")));
        requestBody.add("file_excel", new FileSystemResource(new File("E:\\dataJava\\data\\user_info.xlsx")));
        requestBody.add("file_img", new FileSystemResource(new File("E:\\dataJava\\data\\github.png")));
        requestBody.add("email", "town@163.com");
        requestBody.add("email", "harden@163.com");
        requestBody.add("id", "123");
        requestBody.add("id", "12345");
        requestBody.add("name", "zhangsan");
        requestBody.add("name", "kuangtu");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(requestBody, httpHeaders);
        String response = restTemplate.postForObject(URL_ROOT+"/form-data", requestEntity, String.class);
        System.out.println(response);
    }

    @Test
    public void receiveSingleTest() {
        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap();
        requestBody.add("file_excel", new FileSystemResource(new File("E:\\dataJava\\data\\city_info.xlsx")));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(requestBody, httpHeaders);

        String response = restTemplate.postForObject(URL_ROOT+"/form-data", requestEntity, String.class);
        System.out.println(response);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL_ROOT+"/form-data", requestEntity, String.class);
        System.out.println(responseEntity);
    }
}
