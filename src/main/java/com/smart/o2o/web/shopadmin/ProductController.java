package com.smart.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart.o2o.dto.ImageHandler;
import com.smart.o2o.dto.ProductExecution;
import com.smart.o2o.entity.Product;
import com.smart.o2o.entity.Shop;
import com.smart.o2o.enums.ProductEnum;
import com.smart.o2o.exceptions.ProductException;
import com.smart.o2o.service.ProductService;
import com.smart.o2o.util.CodeUtil;
import com.smart.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ProductController {

//    图片最大上传数
    private static final int MAX_COUNT = 6;

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/addproduct", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addProduct(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        if (!CodeUtil.checkCode(request)) {
            map.put("success", false);
            map.put("msg", "验证码错误");
            return map;
        }
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        String productStr = HttpServletRequestUtil.getString(request, "productStr");
        MultipartHttpServletRequest servletRequest = null;
        ImageHandler imageHandler = null;
        List<ImageHandler> handlerList = new ArrayList<>();
        MultipartResolver multipartResolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());
        try {
            if (multipartResolver.isMultipart(request)) {
                servletRequest = (MultipartHttpServletRequest) request;
                //得到商品缩略图
                MultipartFile file = servletRequest.getFile("file");
                imageHandler = new ImageHandler(file.getOriginalFilename(), file.getInputStream());
                for (int i = 0; i < MAX_COUNT; i++) {
                    //得到商品详情图
                    MultipartFile productImg = servletRequest.getFile("productImg" + i);
                    if (productImg != null) {
                        ImageHandler img =
                                new ImageHandler(productImg.getOriginalFilename(), productImg.getInputStream());
                        handlerList.add(img);
                    }else {
                        break;
                    }
                }
            }else {
                map.put("success", false);
                map.put("msg", "上传文件为空");
                return map;
            }
        }catch (Exception e) {
            map.put("success", false);
            map.put("msg", e.toString());
            return map;
        }
        //将前端json数据转化为Product实体类
        try {
            product = mapper.readValue(productStr, Product.class);
        }catch (Exception e) {
            map.put("success", false);
            map.put("msg", e.toString());
            return map;
        }
        //判断需添加的商品，商品缩略图，商品详情图列表是否为空
        if (product != null && imageHandler != null && handlerList.size() > 0) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                Shop shop = new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShop(shop);
                ProductExecution pe = productService.addProduct(product, imageHandler, handlerList);
                if (pe.getCode() == ProductEnum.SUCCESS.getCode()) {
                    map.put("success", true);
                }else {
                    map.put("success", false);
                    map.put("msg", pe.getMsg());
                }
            }catch (ProductException e) {
                map.put("success", false);
                map.put("msg", e.toString());
                return map;
            }
        }else {
            map.put("success", false);
            map.put("msg", "输入的商品信息为空，请重新输入");
        }
        return map;

    }
}
