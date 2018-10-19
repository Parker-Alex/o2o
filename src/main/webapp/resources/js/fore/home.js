// $(function () {
$(document).ready(function (){

    var infoUrl = "/o2o/fore/mainpageinfo";

    getInfo();

    //得到首页信息方法
    function getInfo() {
        $.get(
            infoUrl,
            function (result) {
                if (result.success) {

                    //得到商店类别列表
                    var sclist = result.shopCategories;

                    //使用.map(),回调函数中，元素在前，索引在后；.each()相反
                    sclist.map(function (item, index) {
                        //商店类别标题（无法将商店名字 h5 标签表示，不知道是什么原因）
                        var title = $("<h2></h2>").append(item.shopCategoryName);
                        // var title = "<h5 class='cardtitle'>" + item.shopCategoryName + "</h5>";

                        //商店类别描述
                        var desc = $("<p>Donec sed odio dui. Etiam porta sem malesuada magna mollis euismod. Nullam id dolor id nibh ultricies vehicula ut id elit. Morbi leo risus, porta ac consectetur ac, vestibulum at eros. Praesent commodo cursus magna.</p>").append(item.shopCategoryDesc);

                        // 进入商店类别按钮
                        // var into = $("<a class='btn btn-primary text-white'>进入</a>")
                        //                             .attr("data-id", item.shopCategoryId);
                        var into = $("<p><a class='btn btn-secondary' href='#' role='button'>View details &raquo;</a></p>")

                        //商品类别图片路径
                        var img = $("<img class='rounded-circle' width='140' height='140'>")
                            .attr("src", "../resources/img/shopcategory/" + item.shopCategoryId +".jpg");

                        //将图片变为超链接
                        var imgLink = $("<a href='#' class='myLink'></a>").append(img).attr("data-id", item.shopCategoryId);

                        //动态拼接
                        $("<div class='col-lg-4'></div>").append(imgLink).append(title).append(desc).append(into)
                            .appendTo(".marketing .myrow");
                    });

                    //得到头条列表
                    var hl = result.headlines;

                    hl.map(function (item, index) {
                        if (index === 1) {

                            //头条图片路径
                            var img = $("<img class='d-block w-100' alt='First slide'>")
                                .attr("src", "../resources/img/carousel/"+item.lineId+".jpg" );

                            //头条链接
                            var a = $("<a></a>").attr("href", item.link).append(img);

                            //如果是第一张轮播图，需要加active类
                            $("<div class='carousel-item active'></div>").append(a).appendTo(".carousel-inner");

                        }else {
                            var img = $("<img class='d-block w-100' alt='First slide'>")
                                .attr("src", "../resources/img/carousel/"+item.lineId+".jpg" );

                            var a = $("<a></a>").attr("href", item.link).append(img);

                            //如果不是第一张轮播图，则不需要加avtice类
                            $("<div class='carousel-item'></div>").append(a).appendTo(".carousel-inner");
                        }
                    })
                }else {
                    alert(result.msg);
                }
            }
        );
    }

    //点击一级类别进入店铺列表事件
    $(".marketing .myrow").on("click", ".myLink", function () {
        var parentId = $(this).attr("data-id");
        $(this).attr("href", "/o2o/main/shoplist?parentId=" + parentId);
    })
});