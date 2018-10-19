$(function () {

    //定义展示店铺相关信息路由
    let showShopUrl = "/o2o/fore/shopListpageinfo";

    //定义条件查询参数
    let parentId = getQueryString("parentId");
    if (parentId) {
        showShopUrl = "/o2o/fore/shopListpageinfo?parentId=" + parentId;
    }
    let shopName = "";
    let areaId = "";
    let shopCategoryId = "";

    let pageIndex = 1;
    let pageSize = 6;

    showShop();
    searchShop();

    //展示店铺相关信息方法(店铺区域，店铺类别)
    function showShop() {
        $.get(
            showShopUrl,
            function (result) {
                if (result.success) {
                    //获得店铺分类列表
                    let sclist = result.sclist;
                    //获得店铺区域列表
                    let arealist = result.arealist;
                    //将店铺类别逐个动态显示
                    sclist.map(function (item, index) {
                        $("<a class='btn btn-info text-white m-2'></a>")
                            .append(item.shopCategoryName).attr("data-id", item.shopCategoryId)
                            .appendTo(".shopCategory");
                    });
                    //将店铺区域信息动态添加到选择框中
                    arealist.map(function (item, index) {
                        $("<option></option>").append(item.areaName).attr("data-id", item.areaId)
                            .appendTo("#shopArea");
                    })
                }else {
                    alert(result.msg);
                }
            }
        );
    }

    //查找店铺方法
    function searchShop() {
        let url = "/o2o/fore/listshop?pageIndex=" + pageIndex +"&pageSize=" + pageSize + "&shopName=" + shopName
            + "&areaId=" + areaId + "&shopCategoryId=" + shopCategoryId + "&parentId=" + parentId;
        $.get(
            url,
            function (result) {
                if (result.success) {
                    //获取店铺列表时，先将原有的店铺删除
                    $(".container .shop").remove();
                    //得到店铺列表
                    let shoplist = result.shoplist;
                    //遍历店铺列表
                    $.each(shoplist, function (index, item) {
                        //卡片头部，显示店铺图片
                        let header = $("<div class='card-header'></div>")
                            // .append($("<img>").attr("src", imgPath + item.shopImg));
                            .append($("<img>").attr("src", item.shopImg));
                        //卡片身体，显示店铺名称、描述、和“进入按钮”
                        let body = $("<div class='card-body'></div>")
                            .append($("<h4 class='card-title'><i class='fas fa-store mr-3'></i></h4>").append(item.shopName))
                            .append($("<p class='card-text'></p>").append(item.shopDesc))
                            .append($("<a href= '#' class='btn btn-primary'>进入</a>"));
                        //卡片尾部，显示日期
                        let footer = $("<div class='card-footer text-muted'></div>")
                            .append("创建时间：" + moment(item.createTime).format("YYYY-MM-DD"));
                        //将上面三个卡片模块组成店铺卡片
                        let shop = $("<div class='card text-center' style='width: 530px;'></div>")
                            .append(header).append(body).append(footer);
                        //将店铺卡片添加到容器中
                        $("<div class='row justify-content-center m-4 shop'></div>").append(shop).appendTo(".container");

                    });
                } else{
                    alert(result.msg);
                }
            }
        );
    }

    //点击店铺类别进行查找店铺
    $(".shopCategory").on("click", ".btn", function () {
        //先将条件置为空
        parentId = "";
        shopCategoryId = "";
        //如果有parentId，则按店铺类别查找店铺
        if (parentId) {
            shopCategoryId = $(this).attr("data-id");
        } else {
            //否则按查找一级店铺类别下的店铺
            parentId = $(this).attr("data-id");
        }
        searchShop();
    });

    //搜索框查找店铺
    $("#search_shop").on("change", function () {
        shopName = $(this).val();
        searchShop();
    });

    //根据店铺区域查找店铺
    $("#shopArea").change(function () {
        areaId = $("#shopArea option:selected").attr("data-id");
        searchShop();
    });


    let colors = ["#feea00","#a9df85","#5dc0ad", "#ff9a00","#fa3f20"]

});

