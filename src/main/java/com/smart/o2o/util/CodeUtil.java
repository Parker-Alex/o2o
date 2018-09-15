package com.smart.o2o.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

public class CodeUtil {

    public static boolean checkCode(HttpServletRequest request) {

        //获取输入的验证码
        String actualCode = HttpServletRequestUtil.getString(request, "code");
//        String actualCode = HttpServletRequestUtil.getString(request, "verifyCodeActual");

        //获取图片中的验证码
        String expectedCode = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);

        if (actualCode == null || !actualCode.equalsIgnoreCase(expectedCode)) {
            return false;
        }
        return true;

    }
}
