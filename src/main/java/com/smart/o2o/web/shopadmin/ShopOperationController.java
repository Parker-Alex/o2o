package com.smart.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart.o2o.dto.ImageHandler;
import com.smart.o2o.dto.ShopExecution;
import com.smart.o2o.entity.Area;
import com.smart.o2o.entity.Shop;
import com.smart.o2o.entity.ShopCategory;
import com.smart.o2o.entity.User;
import com.smart.o2o.enums.ShopEnum;
import com.smart.o2o.service.AreaService;
import com.smart.o2o.service.ShopCategoryService;
import com.smart.o2o.service.ShopService;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/shopadmin")
public class ShopOperationController {
    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private AreaService areaService;

    /**
     * 通过shopId得到店铺信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getshopbyid", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getShopById(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if (shopId > -1) {
            try {
                Shop shop = shopService.readByShopId(shopId);
                shop.setScid(shopCategoryService.getByName(shop.getShopCategory().getShopCategoryName()).getShopCategoryId());
                List<Area> areaList = areaService.readAreaList();
                map.put("shop", shop);
                map.put("areaList", areaList);
                map.put("success", true);
            } catch (Exception e) {
                map.put("success", false);
                map.put("msg", e.toString());
            }
        } else {
            map.put("success", false);
            map.put("msg", "shopId is empty");
        }
        return map;
    }

    /**
     * 得到店铺相关的区域信息和店铺类别信息方法
     *
     * @return
     */
    @RequestMapping(value = "/getshopinfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getShopInfo() {

        Map<String, Object> map = new HashMap<>();
        try {
            List<Area> areaList = areaService.readAreaList();
            List<ShopCategory> shopCategoryList = shopCategoryService.readShopCategoryList(new ShopCategory());
            map.put("success", true);
            map.put("areaList", areaList);
            map.put("shopCategoryList", shopCategoryList);
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", e.getMessage());
            return map;
        }
        return map;
    }

    /**
     * 创建店铺方法
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/createshop", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> createShop(HttpServletRequest request) {

        Map<String, Object> map = new HashMap<>();
        if (!CodeUtil.checkCode(request)) {
            map.put("success", false);
            map.put("msg", "验证码错误，请重新输入");
            return map;
        }
        String shopData = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            //将json格式的数据转化为java对象
            shop = mapper.readValue(shopData, Shop.class);
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", e.toString());
            return map;
        }

        //获取请求中的文件流
        MultipartHttpServletRequest multipartHttpServletRequest = null;
        MultipartFile shopImg = null;
        MultipartResolver multipartResolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断请求中是否有文件
        if (multipartResolver.isMultipart(request)) {
            //如果有文件,从请求的shopImg属性中获取文件
            multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (MultipartFile) multipartHttpServletRequest.getFile("shopImg");
        } else {
            //如果没有文件,设置响应信息
            map.put("success", false);
            map.put("msg", "上传图片为空");
            return map;
        }
        //空值判断
        if (shop != null && shopImg != null) {
            User user = new User();
            user.setUserId(1L);
            shop.setUser(user);
            //注册店铺
            ShopExecution shopExecution;
            try {
                ImageHandler imageHandler = new ImageHandler(shopImg.getOriginalFilename(), shopImg.getInputStream());
                shopExecution = shopService.createShop(shop, imageHandler);
                if (shopExecution.getState() == ShopEnum.CHECK.getState()) {
                    map.put("success", true);
                    map.put("msg", "创建成功");
                } else {
                    //否则返回对应信息
                    map.put("success", false);
                    map.put("msg", shopExecution.getStateInfo());
                }
            } catch (IOException e) {
                map.put("success", false);
                map.put("msg", e.getMessage());
            }
            return map;
        } else {
            map.put("success", false);
            map.put("msg", "店铺信息为空，请重新输入");
            return map;
        }
    }

    /**
     * 更新店铺方法
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateshop", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateShop(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        //校验验证码
        if (!CodeUtil.checkCode(request)) {
            map.put("success", false);
            map.put("msg", "验证码错误");
            return map;
        }
        String shopStr = HttpServletRequestUtil.getString(request,"shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        }catch (Exception e){
            map.put("success", false);
            map.put("msg", e.toString());
            return map;
        }
        //获取请求中的文件流
        MultipartFile shopImg = null;
        MultipartResolver multipartResolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断请求中是否有文件
        if (multipartResolver.isMultipart(request)) {
            //如果有文件,从请求的shopImg属性中获取文件
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (MultipartFile) multipartHttpServletRequest.getFile("shopImg");
        } else {
            //如果没有文件,设置响应信息
            map.put("success", false);
            map.put("msg", "上传图片为空");
            return map;
        }
        if (shop != null && shop.getShopId() > 0) {
            User user = (User) request.getSession().getAttribute("user");
            shop.setUser(user);
            ShopExecution shopExecution;
            try {
                ImageHandler imageHandler = new ImageHandler(shopImg.getOriginalFilename(), shopImg.getInputStream());
                shopExecution = shopService.updateShop(shop, imageHandler);
                if (shopExecution.getState() == ShopEnum.SUCCESS.getState()) {
                    map.put("success", true);
                    List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
                    if (shopList == null || shopList.size() == 0) {
                        shopList = new ArrayList<Shop>();
                    }
                    shopList.add(shopExecution.getShop());
                    request.getSession().setAttribute("shopList", shopList);
                }else {
                    map.put("success", false);
                    map.put("msg", shopExecution.getStateInfo());
                }
            }catch (IOException e) {
                map.put("success", false);
                map.put("msg", e.toString());
            }
            return map;
        }else {
            map.put("success", false);
            map.put("msg", "请输入店铺");
            return map;
        }
    }

    /**
     * 获取店铺列表信息方法
     * @param request
     * @return
     */
    @RequestMapping(value = "/getshoplist", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getShopList(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        Shop shopCondition = new Shop();
        User user = new User();
        user.setUserId(1L);
        user.setUserName("测试");
        request.getSession().setAttribute("user", user);
        try {
            User userinfo = (User) request.getSession().getAttribute("user");
            shopCondition.setUser(userinfo);
            ShopExecution shopExecution = shopService.queryShopList(shopCondition, 1, 100);
            map.put("shopList", shopExecution.getShopList());
            map.put("user", userinfo);
            map.put("success", true);
        }catch (Exception e) {
            map.put("success", false);
            map.put("msg", e.getMessage());
        }
        return map;
    }

    @RequestMapping(value = "/getshopmanagementinfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getShopManagementInfo(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        //从请求中得到店铺id
        Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        //如果店铺id为空
        if (shopId <= 0) {
            //从session中获得shop对象
            Object currentShop = request.getSession().getAttribute("currentShop");
            //如果shop对象为空
            if (currentShop == null) {
                //进行从定向
                map.put("redirect", "true");
                map.put("url", "/shop/shoplist");
            }else {
                //如果shop对象不为空
                Shop shop = (Shop) currentShop;
                map.put("redirect", false);
                //将该shop对象的id保存在shopId中
                map.put("shopId", shop.getShopId());
            }
        }else {
            //如果店铺id不为空
            //创建新的shop对象
            Shop shop = new Shop();
            //将店铺id保存在新shop对象中
            shop.setShopId(shopId);
            //将shop对象放入session
            request.getSession().setAttribute("currentShop", shop);
            map.put("redirect", false);
        }
        return map;
    }
}

//    /**
//     * 将CommonsMultipartFile类型文件转化为File类型文件的方法
//     * @param is
//     * @param file
//     */
//    public static void inputStreamToFile(InputStream is, File file) {
//        FileOutputStream fos = null;
//        try {
//            fos = new FileOutputStream(file);
//            int readByte = 0;
//            //定义字节数组读取输入流数据
//            byte[] bytes = new byte[1024];
//            while ((readByte = is.read(bytes)) != -1) {
//                //当数据没有读完,将字节数组中的内容写入file
//                fos.write(bytes, 0, readByte);
//            }
//        }catch (Exception e) {
//            throw new RuntimeException("调用inputStreamToFile方法产生异常: " + e.getMessage());
//        }finally {
//            try {
//                //将输入输出流关闭
//                if (is != null) {
//                    is.close();
//                }
//                if (fos != null) {
//                    fos.close();
//                }
//            }catch (Exception e) {
//                throw new RuntimeException("inputStreamToFile方法关闭io产生异常: " + e.getMessage());
//            }
//        }
//    }

