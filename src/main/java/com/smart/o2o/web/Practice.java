package com.smart.o2o.web;

import com.smart.o2o.exceptions.MyException;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class Practice {

//    @RequestMapping("/file")
//    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
//        if (!file.isEmpty()) {
//            System.out.println(file.getOriginalFilename());
//            FileUtils.copyInputStreamToFile(file.getInputStream(), new File("E:\\File\\",
//                    System.currentTimeMillis() + file.getOriginalFilename()));
//        }
//        return "success";
//        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
//        MultipartFile file = multipartHttpServletRequest.getFile("file");
//        ModelAndView mv = new ModelAndView();
//        mv.setView(new MappingJackson2JsonView());
//        String fileName = file.getOriginalFilename();
//        File dest = new File(fileName);
//        try {
//            file.transferTo(dest);
//            mv.addObject("success", true);
//            mv.addObject("msg", "上传文件成功");
//        }catch (IllegalStateException | IOException e) {
//            mv.addObject("success", false);
//            mv.addObject("msg", "上传文件失败");
//            e.printStackTrace();
//        }
//        return mv;
//    }

    @RequestMapping(value = "/file",method = RequestMethod.POST)
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {//使用@RequestParam注解将表单中name=file的元素与
        // @RequestParam("file")中的指进行绑定，并转化为MultipartFile类型
        if (!file.isEmpty()){
            System.out.println(file.getOriginalFilename());//打印本地文件的名字
            //通过FileUtils的方法将文件拷贝,第一个参数为拷贝文件的源的流，第二个参数为文件输出的位置(可以是一个服务器的本地地址)
            FileUtils.copyInputStreamToFile(file.getInputStream(),
                    //第一个参数为根目录，第二个参数为文件名(时间戳加原文件名)
                    new File("E:\\File\\",System.currentTimeMillis()+file.getOriginalFilename()));
        }
        return "success";
    }


    @RequestMapping("/exception")
    public void exception(Model model) {
//        model.addAttribute("msg", "操作错误");
//        throw new RuntimeException("测试异常跳转");
        throw new MyException();
    }

    @ExceptionHandler(MyException.class)
    public String exception2(MyException e) {
        return "exception";
    }
}
