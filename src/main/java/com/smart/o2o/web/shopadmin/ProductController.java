package com.smart.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart.o2o.dto.ImageHandler;
import com.smart.o2o.dto.ProductExecution;
import com.smart.o2o.entity.Product;
import com.smart.o2o.entity.ProductCategory;
import com.smart.o2o.entity.Shop;
import com.smart.o2o.enums.ProductEnum;
import com.smart.o2o.exceptions.ProductException;
import com.smart.o2o.service.ProductCategoryService;
import com.smart.o2o.service.ProductService;
import com.smart.o2o.util.CodeUtil;
import com.smart.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/shopadmin")
public class ProductController {

//    图片最大上传数
    private static final int MAX_COUNT = 6;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

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
        ImageHandler imageHandler = null;
        List<ImageHandler> handlerList = new ArrayList<>();
        MultipartResolver multipartResolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());
        try {
            if (multipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest servletRequest = (MultipartHttpServletRequest) request;
                //得到商品缩略图
                MultipartFile thumbnail = servletRequest.getFile("thumbnail");
                imageHandler = new ImageHandler(thumbnail.getOriginalFilename(), thumbnail.getInputStream());
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
                map.put("msg", "上传文件为空");
                map.put("success", false);
                return map;
            }
        }catch (Exception e) {
            map.put("msg", e.toString());
            map.put("success", false);
            return map;
        }
        //将前端json数据转化为Product实体类
        try {
            String productStr = HttpServletRequestUtil.getString(request, "productStr");
            product = mapper.readValue(productStr, Product.class);
        }catch (Exception e) {
            map.put("success", false);
            map.put("msg", e.toString());
            return map;
        }
        //判断需添加的商品，商品缩略图，商品详情图列表是否为空
        if (product != null) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
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

    @RequestMapping(value = "/updateproduct", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateProduct(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        //判断商品状态是否改变(上架或下架),若上架，进行验证码校验;若下架,跳过校验
        boolean statusChange = HttpServletRequestUtil.getBoolean(request,"statusChange");
        if (!statusChange && !CodeUtil.checkCode(request)) {
            map.put("success", false);
            map.put("msg", "验证码错误");
            return map;
        }
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        String productStr = HttpServletRequestUtil.getString(request, "productStr");
        ImageHandler imageHandler = null;
        List<ImageHandler> handlerList = new ArrayList<>();
        MultipartResolver multipartResolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());
        try {
            if (multipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest servletRequest = (MultipartHttpServletRequest) request;
                //得到商品缩略图
                MultipartFile thumbnail = servletRequest.getFile("thumbnail");
                imageHandler = new ImageHandler(thumbnail.getOriginalFilename(), thumbnail.getInputStream());
                for (int i = 0; i < MAX_COUNT; i++) {
                    //得到商品详情图
                    MultipartFile productImg = servletRequest.getFile("productImg" + i);
                    if (productImg != null) {
                        ImageHandler img =
                                new ImageHandler(productImg.getOriginalFilename(), productImg.getInputStream());
                        handlerList.add(img);
                    } else {
                        break;
                    }
                }
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
        if (product != null) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                product.setLastEditTime(new Date());
                ProductExecution pe = productService.updateProduct(product, imageHandler, handlerList);
                if (pe.getCode() == ProductEnum.SUCCESS_UPDATE.getCode()) {
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

    /**
     * 通过商品id得到商品，以及商品类别方法
     * @param pid
     * @return
     */
    @RequestMapping(value = "/getproductbyid", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getProductById(@RequestParam("pid") Long pid) {
        Map<String, Object> map = new HashMap<>();
        if (pid != null) {
            try {
                Product product = productService.getProductById(pid);
                List<ProductCategory> categoryList = productCategoryService.listBySid(product.getShop().getShopId());
                map.put("product", product);
                map.put("pclist", categoryList);
                map.put("success", true);
            }catch (ProductException e) {
                map.put("success", false);
                map.put("msg", e.toString());
                return map;
            }
        }else {
            map.put("success", false);
            map.put("msg", "商品id为空");
        }
        return map;
    }

    /**
     * 得到商品列表方法
     * @param request
     * @return
     */
    @RequestMapping(value = "/getproductlist", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getProductList(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        //页数是第几页
        int pageNum = HttpServletRequestUtil.getInt(request, "pageNum");
        //每页的显示多少条
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        if ((pageNum > -1) && (pageSize > -1)
                && (currentShop != null) && (currentShop.getShopId() != null)) {
            long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
            String productName = HttpServletRequestUtil.getString(request, "productName");
            Product condition = compactProductCondition(currentShop.getShopId(), productCategoryId, productName);
            ProductExecution pe = productService.productListByCondition(condition, pageNum, pageSize);
            map.put("productList", pe.getProducts());
            map.put("count", pe.getNum());
            map.put("success", true);
        }else {
            map.put("success", false);
            map.put("msg", "输入条件不能为空");
        }
        return map;
    }

    /**
     * 只改变商品状态的更新方法
     * @param request
     * @return
     */
    @RequestMapping(value = "/changestatus", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> changeStatus(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        String productStr = HttpServletRequestUtil.getString(request, "productStr");
        Product product = null;
        try {
            product= mapper.readValue(productStr, Product.class);
        } catch (IOException e) {
            map.put("success", false);
            map.put("msg", e.toString());
            return map;
        }
        Product p = productService.getProductById(product.getProductId());
        int status = product.getStatus();
        p.setStatus(status);
        p.setLastEditTime(new Date());
        int num = productService.changeStatus(p);
        if (num > 0) {
            map.put("success", true);
        }else {
            map.put("success", false);
            map.put("msg", "操作失败");
        }
        return map;
    }

    /**
     * 将前端传过来的商品相关数据放进一个商品对象并返回
     * @param shopId
     * @param productCategoryId
     * @param productName
     * @return
     */
    private Product compactProductCondition(Long shopId, long productCategoryId, String productName) {
        Product condition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        condition.setShop(shop);
        if (productCategoryId != -1L) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setId(productCategoryId);
            condition.setProductCategory(productCategory);
        }
        if (productName != null) {
            condition.setProductName(productName);
        }
        return condition;
    }
}
