$(function () {

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
                        var title = $("<h4 class=‘mt-0’></h4>").append(item.shopCategoryName);
                        // var title = "<h5 class='cardtitle'>" + item.shopCategoryName + "</h5>";

                        //商店类别描述
                        var desc = $("<p class='card-text'></p>").append(item.shopCategoryDesc);

                        //进入商店类别按钮
                        // var into = $("<a class='btn btn-primary text-white'>进入</a>")
                        //                         //     .attr("data-id", item.shopCategoryId);

                        //商品类别图片路径
                        var img = $("<img class='mr-3' style='width: 150px; '>")
                            .attr("src", "../resources/img/shopcategory/" + item.shopCategoryId +".jpg");

                        var body = $("<div class='media-body'></div>").append(title).append(desc);

                        $("<div class='col-4'></div>").append($("<div class='media mt-3'></div>").append(img).append(body))
                            .appendTo(".myrow");
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

    $(".mycard").on("click", ".btn", function (e) {
        var id = $(this).attr("data-id");
        // alert($(this).attr("data-id"));
        // alert(e.currentTarget.dataset.id);
        $(this).attr("href", "/fore/shoplist?parentId=" + id);
    })
});