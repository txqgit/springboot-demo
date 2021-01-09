package com.town.djl.cv.service.impl;

import ai.djl.Application;
import ai.djl.ModelException;
import ai.djl.engine.Engine;
import ai.djl.inference.Predictor;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.modality.cv.output.BoundingBox;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.modality.cv.output.Rectangle;
import ai.djl.ndarray.NDManager;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelZoo;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import ai.djl.translate.TranslateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.town.djl.cv.domain.DetectResultVO;
import com.town.djl.cv.service.CVService;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

@Service
public class CVServiceImpl implements CVService {
    private static final String imgRoot = "src/test/resources/";
    private static final String outRoot = "build/output";
    private static final Logger logger = LoggerFactory.getLogger(CVServiceImpl.class);

    /*DJL提供的Image格式,根据文件名读取图片*/
    public Image readImageByPath(String imageName, String suffix) throws IOException {
        Path imageFile = Paths.get(imgRoot + imageName + "." + suffix);
        Image img = ImageFactory.getInstance().fromFile(imageFile);
        return img;
    }

    /*对输入的Image进行目标检测,返回检测结果*/
    public DetectedObjects predict(Image img, String imageName) throws IOException, ModelException, TranslateException {
        String backbone;
        if ("TensorFlow".equals(Engine.getInstance().getEngineName())) {
            backbone = "mobilenet_v2";
        } else {
            backbone = "resnet50";
        }

        Criteria<Image, DetectedObjects> criteria =
                Criteria.builder()
                        .optApplication(Application.CV.OBJECT_DETECTION)
                        .setTypes(Image.class, DetectedObjects.class)
                        .optFilter("backbone", backbone)
                        .optProgress(new ProgressBar())
                        .build();

        try (ZooModel<Image, DetectedObjects> model = ModelZoo.loadModel(criteria)) {
            try (Predictor<Image, DetectedObjects> predictor = model.newPredictor()) {
                DetectedObjects detection = predictor.predict(img);
                saveBoundingBoxImage(img, detection, imageName);
                return detection;
            }
        }
    }

    /*将检测所得的bbox画在图上并保存的指定文件*/
    private void saveBoundingBoxImage(Image img, DetectedObjects detection, String imageName)
            throws IOException {
        Path outputDir = Paths.get(outRoot);
        Files.createDirectories(outputDir);

        // Make image copy with alpha channel because original image was jpg
        Image newImage = img.duplicate(Image.Type.TYPE_INT_ARGB);
        newImage.drawBoundingBoxes(detection);

        Path imagePath = outputDir.resolve(imageName + ".png");
        // OpenJDK can't save jpg with alpha channel
        newImage.save(Files.newOutputStream(imagePath), "png");
        logger.info("Detected objects image has been saved in: {}", imagePath);
    }

    /*从djl的检测结果提取*/
    public List<DetectResultVO> extractDetectResult(Image img, DetectedObjects detections) {
        List<DetectResultVO> results = new ArrayList<>();

        int imageWidth = img.getWidth();
        int imageHeight = img.getHeight();
        List<DetectedObjects.DetectedObject> list = detections.items();
        Iterator iter = list.iterator();

        while(iter.hasNext()) {
            DetectedObjects.DetectedObject result = (DetectedObjects.DetectedObject)iter.next();
            BoundingBox box = result.getBoundingBox();
            Rectangle rectangle = box.getBounds();

            DetectResultVO detectResult = new DetectResultVO();
            detectResult.setClassName(result.getClassName());
            detectResult.setProbability(result.getProbability());
            detectResult.setTopLeftX((int)(rectangle.getX() * (double)imageWidth));
            detectResult.setTopLeftY((int)(rectangle.getY() * (double)imageHeight));
            detectResult.setWidth((int)(rectangle.getWidth() * (double)imageWidth));
            detectResult.setHeight((int)(rectangle.getHeight() * (double)imageHeight));

            results.add(detectResult);
        }
        return results;
    }

    /*
     * An example of inference using an object detection model
     * https://github.com/awslabs/djl/blob/master/examples/docs/object_detection.md
     */
    public List<DetectResultVO> objectDetectForImg(String imageName) throws IOException, ModelException, TranslateException {
        Image img = readImageByPath(imageName, "jpg");
        DetectedObjects detections = predict(img, imageName);
        return extractDetectResult(img, detections);
    }

    /*DJL提供的Image格式,从InputStream读取图片*/
    public Image readImageByFormData(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }
        //通过输入流获取图片数据
        InputStream inStream = file.getInputStream();
        //得到图片的二进制数据，以二进制封装得到数据，具有通用性
        Image img = ImageFactory.getInstance().fromInputStream(inStream);
        //关闭输出流
        inStream.close();
        return img;
    }

    public List<DetectResultVO> objectDetectFormData(MultipartFile file) throws IOException, ModelException, TranslateException {
//        saveImageLocal(file);
        String fileName = file.getOriginalFilename().split("\\.")[0]; //获取文件名
        System.out.println(fileName);
        Image img = readImageByFormData(file);
        DetectedObjects detections = predict(img, fileName);
        return extractDetectResult(img, detections);
    }

    public void saveImageLocal(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename(); //获取文件名
        System.out.println(fileName);
        if (!file.isEmpty()) {
            try {
                //通过输入流获取图片数据
                InputStream inStream = file.getInputStream();
                //得到图片的二进制数据，以二进制封装得到数据，具有通用性
                byte[] data = readInputStream(inStream);
                //new一个文件对象用来保存图片，默认保存当前工程根目录
                File imageFile = new File("src/test/resources/tmp.jpg");
                //创建输出流
                FileOutputStream outStream = new FileOutputStream(imageFile);
                //写入数据
                outStream.write(data);
                //关闭输出流
                outStream.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] readInputStream(InputStream inStream) throws IOException{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while( (len=inStream.read(buffer)) != -1 ){
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }

    public String objectDetectShowFormData(MultipartFile file) throws IOException, ModelException, TranslateException {
//        saveImageLocal(file);
        String fileName = file.getOriginalFilename().split("\\.")[0]; //获取文件名
        System.out.println(fileName);
        Image img = readImageByFormData(file);
        DetectedObjects detections = predict(img, fileName);

        File imgFile = new File(outRoot + "/" + fileName + ".png");
        InputStream inStream =new FileInputStream(imgFile);
        byte imgBytes[] = new byte[(int) imgFile.length()]; //创建合适文件大小的数组
        inStream.read(imgBytes); //读取文件里的内容到b[]数组
        inStream.close();
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encodeBuffer(imgBytes);
    }

}
