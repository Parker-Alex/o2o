package com.smart.o2o.util;

public class PathUtil {

    //获取操作系统的分隔符
    private static String separator = System.getProperty("file.separator");

    /**
     * 根据操作系统的不同获取图片的根路径，是所有图片存放的路径
     * @return
     */
    public static String getImgBasePath() {
        //获取操作系统
        String os = System.getProperty("os.name");
        String basePath = "";
        //如果是windows系统
        if (os.toLowerCase().startsWith("win")) {
            basePath = "E:/File/o2o/";
        }else {
            //如果是linux系统
            basePath = "/home/o2o/image/";
        }
        //将分隔符替换成系统的分隔符
        basePath = basePath.replace("/", separator);
        return basePath;
    }

    /**
     * 获取店铺照片路径
     * @param shopId
     * @return
     */
    public static String getShopImgPath(long shopId) {
        String imgPath = "upload/items/shop/" + shopId + "/";
        return imgPath.replace("/", separator);
    }
}
