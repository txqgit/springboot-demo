package com.town.djl.cv.service;

import ai.djl.ModelException;
import ai.djl.translate.TranslateException;
import com.town.djl.cv.domain.DetectResultVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CVService {
    /*
     * 传入图片名, 读取本地对应图片进行目标检测, 返回检测所得bounding box
     */
    public List<DetectResultVO> objectDetectForImg(String imageName) throws IOException, ModelException, TranslateException;

    /*
     * 前端以FormData传入图片数据, 后台服务对该图片进行目标检测, 返回检测所得bounding box
     */
    public List<DetectResultVO> objectDetectFormData(MultipartFile file) throws IOException, ModelException, TranslateException;

    /*
     * 前端以FormData传入图片数据, 后台服务对该图片进行目标检测, 返回检测结果图片的base64编码
     */
    public String objectDetectShowFormData(MultipartFile file) throws IOException, ModelException, TranslateException;


}
