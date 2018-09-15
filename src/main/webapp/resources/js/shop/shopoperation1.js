$(function () {
    //获得店铺id
    var shopId = getQueryString("shopId");
    //shopId为空,就创建店铺；不为空，就修改店铺
    var isEdit = shopId ? true : false;
    //得到店铺初始化信息url
    var getInfoUrl = "/shopadmin/getshopinfo";
    //创建店铺url
    var createshop = "/shopadmin/createshop";
    //得到店铺详细信息
    var getShopInfoUrl = "/shopadmin/getshopbyid?shopId=" + shopId;
    //更新店铺url
    var updateshop = "/shopadmin/updateshop";
    if (isEdit) {
        getShop(shopId);
    }else {
        getShopInfo();
    }

    //得到店铺详细信息方法
    function getShop(shopId) {
        $.getJSON(getShopInfoUrl, function(data) {
            if (data.success) {
                var shop = data.shop;
                $("#shopName").val(shop.shopName).attr("disabled", "disabled");
                $("#shopAddr").val(shop.shopAddr);
                $("#shopTel").val(shop.shopTel);
                $("#shopDesc").val(shop.shopDesc);
                var shopCategory = '<option disabled data-id="'
                    + shop.scid + '" selected>'
                    + shop.shopCategory.shopCategoryName + '</option>';
                $("#shopCategory").html(shopCategory).attr("disabled", "disabled");
                // $("<option></option>").append(shop.shopCategory.shopCategoryName).appendTo("#shopCatrgory");
                    // .attr("data-id",shop.shopCategory.shopCategoryId)
                    // .attr("selected", "selected")
                    // .appendTo("#shopCatrgory");
                $.each(data.areaList, function(index, item) {
                    $("<option></option>").append(item.areaName).attr("data-id", item.areaId).appendTo("#shop_area");
                });
                $("#shop_area option[data-id='" + shop.area.areaId + "']").attr("selected", "selected");
            }else{
                alert(data.msg);
            }
        })
    }

    //得到店铺初始化信息方法
    function getShopInfo() {
        //获取店铺相关信息
        //通过url得到数据
        $.getJSON(getInfoUrl,function (data) {
            // 判断数据是否获取成功
            if (data.success) {
                //获取区域信息,循环遍历区域信息，并添加到区域下拉框中
                $.each(data.areaList,function (index, item) {
                    $("<option></option>").append(item.areaName).attr("data-id",item.areaId).appendTo("#shop_area");
                });
                //获取店铺类别信息,循环遍历店铺类别信息，并添加到店铺类别下拉框中
                $.each(data.shopCategoryList,function (index,item) {
                    $("<option></option>").append(item.shopCategoryName).attr("data-id",item.shopCategoryId).appendTo("#shopCategory");
                })
            }
        });
    }

    //提交店铺信息
    $("#shop_submit").click(function () {
        var shop = {};
        if (isEdit) {
            shop.shopId = shopId;
        }
        shop.shopName = $("#shopName").val();
        shop.shopAddr = $("#shopAddr").val();
        shop.shopTel = $("#shopTel").val();
        shop.shopDesc = $("#shopDesc").val();
        shop.shopCategory = {
            shopCategoryId : $("#shopCategory").find("option").not(function () {
                return !this.selected;
            }).data("id")
        };
        shop.area = {
            areaId : $("#shop_area").find("option").not(function () {
                return !this.selected;
            }).data("id")
        };
        //获取上传的图片
        var shopImg = $("#shopImg")[0].files[0];
        //定义表格数据对象
        var formData = new FormData();
        formData.append("shopImg", shopImg);
        formData.append("shopStr", JSON.stringify(shop));
        var code = $("#code").val();
        if (!code) {
            alert("请输入验证码!");
            return ;
        }else {
            formData.append("code", code);
        }
        alert(isEdit);
        $.ajax({
            url:isEdit ? updateshop : createshop,
            type:"POST",
            data:formData,
            contentType:false,
            processData:false,
            cache:false,
            success:function (data) {
                if (data.success) {
                    alert("提交成功!");
                }else {
                    alert("提交失败:" + data.msg);
                }
                //更换验证码
                $("#shop-code").click();
            }
        });
    });
    //返回按钮点击事件
    $("#shop_back").click(function() {
        history.back();
    });
});