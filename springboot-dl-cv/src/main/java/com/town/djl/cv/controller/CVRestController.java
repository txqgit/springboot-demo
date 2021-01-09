package com.town.djl.cv.controller;

import ai.djl.ModelException;
import ai.djl.translate.TranslateException;
import com.town.djl.cv.domain.DetectResultVO;
import com.town.djl.cv.service.CVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class CVRestController {
    @Autowired
    private CVService cvService;

    //@RequestMapping(value = "/detect") //不限制请求方式, GET、PUT、POST都可以
    @RequestMapping(value = "/detect", method=RequestMethod.GET) //限制只能以GET方式请求
    // URL: localhost:8080/detect?imageName=dog_bike_car
    public List<DetectResultVO> detectImageOfName(@RequestParam(value = "imageName", required = true)String imageName) throws ModelException, TranslateException, IOException {
        return cvService.objectDetectForImg(imageName);
    }
}
