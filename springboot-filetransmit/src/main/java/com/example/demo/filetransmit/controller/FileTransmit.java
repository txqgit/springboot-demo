package com.example.demo.filetransmit.controller;

import com.example.demo.filetransmit.bean.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/rest")
public class FileTransmit {
    @PostMapping(value = "/form-data")
    public String formData(HttpServletRequest request,
                           @RequestParam(value = "email", required = false) String email,
                           @RequestParam(value = "file_excel", required = false) MultipartFile multipartFile,
                           UserInfo userInfo) {
        StandardMultipartHttpServletRequest standardMultipartRequest = (StandardMultipartHttpServletRequest) request;

        log.info("multipartFile in @RequestParam={}", multipartFile.getOriginalFilename());
        MultiValueMap<String, MultipartFile> multiFileMap = standardMultipartRequest.getMultiFileMap();
        for (String paramKey : multiFileMap.keySet()) {
            List<MultipartFile> multipartFileList = multiFileMap.get(paramKey);
            log.info("MultipartFile key={}, size={}", paramKey, multipartFileList.size());
            for (MultipartFile curMultipartFile : multipartFileList) {
                log.info("MultipartFile key={}, file name={}",
                        paramKey, curMultipartFile.getOriginalFilename());
                log.info("MultipartFile in request equals to file in @RequestParam={}",
                        multipartFile.equals(curMultipartFile));
            }
        }

        log.info("email in @RequestParam={}", email);
        log.info("userInfo in RequestParam={}", userInfo);
        Map<String, String[]> paramMap = standardMultipartRequest.getParameterMap();
        for (String paramKey : paramMap.keySet()) {
            log.info("param key={}, size={}, value={}",
                    paramKey, paramMap.get(paramKey).length, paramMap.get(paramKey));
        }
        return "Form Data processed finished!";
    }

    @ResponseBody
    @PostMapping(value = "/receive/single")
    public String receiveSingle(@RequestParam(value = "file") MultipartFile multipartFile) throws IOException {
        if (multipartFile == null) {
            return "received file is null!";
        }
        log.info("文件content-type={}", multipartFile.getContentType());
        log.info("文件大小={}", multipartFile.getSize());
        log.info("文件名={}", multipartFile.getName());
        log.info("文件原始名={}", multipartFile.getOriginalFilename());
        File destFile = new File("E:\\Download\\"+multipartFile.getOriginalFilename());
        multipartFile.transferTo(destFile);
        return "Form Data processed finished!";
    }

    @CrossOrigin  //允许跨域访问
    @GetMapping(value = "/get-img-code")
    public String getImageBase64(String imageName) throws IOException {
        log.info("request param={}", imageName);
        String imgRootPath = "E:\\dataJava\\data\\";
        File imgFile = new File(imgRootPath + imageName + ".png");
        InputStream inStream =new FileInputStream(imgFile);
        byte[] imgBytes = new byte[(int) imgFile.length()]; //创建合适文件大小的数组
        inStream.read(imgBytes); //读取文件里的内容到b[]数组
        inStream.close();
        log.info("image size={}", imgBytes.length);
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encodeBuffer(imgBytes);
    }

    @CrossOrigin  //允许跨域访问
    @GetMapping(value = "/get-img-byte")
    public void getImageByte(HttpServletRequest request, HttpServletResponse response, String imageName) throws IOException {
        String imgRootPath = "E:\\dataJava\\data\\";
        File imgFile = new File(imgRootPath + imageName + ".png");
        InputStream inStream =new FileInputStream(imgFile);
        byte imgBytes[] = new byte[(int) imgFile.length()]; //创建合适文件大小的数组
        inStream.read(imgBytes); //读取文件里的内容到b[]数组
        inStream.close();

        response.setContentType("application/octet-stream;charSet=UTF-8");
        response.setContentLength(imgBytes.length);
        try (InputStream inputStream = new ByteArrayInputStream(imgBytes);
            OutputStream outputStream = response.getOutputStream()) {
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        }catch (IOException e) {
            log.error("{}", e.getMessage());
        }
    }
}
