package com.smart.o2o.util;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestUtil {

    //将请求中对应键的值转化为int类型
    public static int getInt(HttpServletRequest request, String key) {
        try {
            return Integer.decode(request.getParameter(key));
        }catch (Exception e){
            return -1;
        }
    }

    //将请求中对应键的值转化为long类型
    public static long getLong(HttpServletRequest request, String key) {
        try {
            return Long.valueOf(request.getParameter(key));
        }catch (Exception e){
            return -1L;
        }
    }

    //将请求中对应键的值转化为Double类型
    public static Double getDouble(HttpServletRequest request, String key) {
        try {
            return Double.valueOf(request.getParameter(key));
        }catch (Exception e){
            return -1d;
        }
    }

    //将请求中对应键的值转化为boolean类型
    public static boolean getBoolean(HttpServletRequest request, String key) {
        try {
            return Boolean.valueOf(request.getParameter(key));
        }catch (Exception e){
            return false;
        }
    }

    //将请求中对应键的值转化为String类型
    public static String getString(HttpServletRequest request, String key) {
        try {
            String result = request.getParameter(key);
            if (result != null) {
                //去除字符串前后的空格
                return result.trim();
            }
            if ("".equals(request)) {
                return null;
            }
            return result;
        }catch (Exception e) {
            return null;
        }
    }
}
