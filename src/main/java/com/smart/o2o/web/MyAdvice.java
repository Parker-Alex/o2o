package com.smart.o2o.web;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@ControllerAdvice(basePackages = {"com.smart.o2o.web"})
public class MyAdvice {

    @ExceptionHandler(Exception.class)
    public String exception(Model model) {
        model.addAttribute("msg", "操作错误");
        return "exception";
    }
}
