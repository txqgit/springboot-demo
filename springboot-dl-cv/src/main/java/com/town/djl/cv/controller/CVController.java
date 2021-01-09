package com.town.djl.cv.controller;

import ai.djl.ModelException;
import ai.djl.translate.TranslateException;
import com.town.djl.cv.domain.DetectResultVO;
import com.town.djl.cv.service.CVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@Controller
@RequestMapping("")
public class CVController {
    @Autowired
    private CVService cvService;

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value="/detect/formdata", method = RequestMethod.POST)
    public List<DetectResultVO> detectImageOfFormData(@RequestParam("file") MultipartFile file) throws ModelException, TranslateException, IOException {
        return cvService.objectDetectFormData(file);
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value="/detectshow/formdata", method = RequestMethod.POST)
    public String detectShowImageOfFormData(@RequestParam("file") MultipartFile file) throws ModelException, TranslateException, IOException {
        return cvService.objectDetectShowFormData(file);
    }

}
