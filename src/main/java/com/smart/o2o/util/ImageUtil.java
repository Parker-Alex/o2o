package com.smart.o2o.util;

import com.smart.o2o.dto.ImageHandler;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ImageUtil {

    private static final Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    //获取classpath的绝对路径(通过当前线程得到类加载器，然后再得到资源路径)
    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    //将时间格式化
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    //定义随机数
    private static final Random r = new Random();

    public static String generateThumbnail(String targetAddr, ImageHandler imageHandler) {

        String realFileName = getRandomFileName();
        String extension = getFileExtension(imageHandler.getImageName());
        makeDirPath(targetAddr);
        //文件相对路径
        String relativeAddr = targetAddr + realFileName + extension;
        logger.debug("current relativeAddr is:" + relativeAddr);

        //文件总路径：根路径+相对路径
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        logger.debug("current complete addr is:" + PathUtil.getImgBasePath() + relativeAddr);

        try {
            //对用户上传的图片进行处理
            Thumbnails.of(imageHandler.getImage()).size(200, 200)
                    //添加水印(参数1：水印位置 参数2：水印的路径 参数3：水印透明度)
                    .watermark(Positions.BOTTOM_RIGHT,
                            ImageIO.read(new File(basePath + "/watermark.jpg")), 0.5f)
                    //指定压缩程度
                    .outputQuality(0.8)
                    //指定处理后图片的生成位置
                    .toFile(dest);
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
//            throw new RuntimeException("创建缩略图失败:" + e.toString());
        }
        return relativeAddr;
    }

    public static String generateNormal(String targetAddr, ImageHandler imageHandler) {

        String realFileName = getRandomFileName();
        String extension = getFileExtension(imageHandler.getImageName());
        makeDirPath(targetAddr);
        //文件相对路径
        String relativeAddr = targetAddr + realFileName + extension;
//        logger.debug("current relativeAddr is:" + relativeAddr);

        //文件总路径：根路径+相对路径
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
//        logger.debug("current complete addr is:" + PathUtil.getImgBasePath() + relativeAddr);

        try {
            //对用户上传的图片进行处理
            Thumbnails.of(imageHandler.getImage()).size(337, 640)
                    //添加水印(参数1：水印位置 参数2：水印的路径 参数3：水印透明度)
                    .watermark(Positions.BOTTOM_RIGHT,
                            ImageIO.read(new File(basePath + "/watermark.jpg")), 0.9f)
                    //指定处理后图片的生成位置
                    .toFile(dest);
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
//            throw new RuntimeException("创建缩略图失败:" + e.toString());
        }
        return relativeAddr;
    }

    /**
     * 创建文件生成路径上的所涉及到的目录
     * @param targetAddr 生成文件的相对路径
     */
    public static void makeDirPath(String targetAddr) {
        //获取生成文件的绝对路径,也是全路径
        String realFilePath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFilePath);
        //如果文件不存在
        if (!dirPath.exists()) {
            //递归创建文件夹
            dirPath.mkdirs();
        }
    }

    /**
     * 获取文件名的扩张名
     * @param fileNmae
     * @return
     */
    public static String getFileExtension(String fileNmae) {
        //返回文件名最后一个.后的字符串(即文件后缀名)
        return fileNmae.substring(fileNmae.lastIndexOf("."));
    }

    /**
     * 自定义文件名，文件名为当前时间加随机数，防止重名
     *
     * @return
     */
    public static String getRandomFileName() {
        //获取五位随机数(10000-99999)
        int randomNum = r.nextInt(89999) + 10000;
        //获取当前格式化后的时间
        String nowDate = dateFormat.format(new Date());
        //字符串加整型，结果会自动转化为字符串
        return nowDate + randomNum;
    }

    /**
     * 删除文件或目录下文件
     * @param storePath
     */
    public static void deleteFileOrPath(String storePath) {
        File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
        //如果路径存在
        if (fileOrPath.exists()) {
            //如果是目录
            if (fileOrPath.isDirectory()) {
                File[] files = fileOrPath.listFiles();
                for (int i = 0; i < files.length; i++) {
                    files[i].delete();
                }
            }
            //删除文件或者文件夹
            fileOrPath.delete();
        }
    }

    public static void main(String[] args) {
        try {
            Thumbnails.of(new File("E:/File/o2o/1.jpg")).size(300, 300)
                    .watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath + "/watermark.jpg")),0.5f)
                    .outputQuality(0.8).toFile("E:/File/o2o/1-new.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
