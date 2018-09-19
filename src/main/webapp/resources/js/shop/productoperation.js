$(function () {

    var productId = getQueryString("pid");
    //添加商品路径
    var addUrl = "/shopadmin/addproduct";
    //通过productId得到商品信息路径
    var infoUrl = "/shopadmin/getproductbyid?pid=" + productId;
    //得到商品分类路径
    var categoryUrl = "/shopadmin/productcategorylist";
    //更新商品路径
    var updateUrl = "/shopadmin/updateproduct";
    //标明是商品添加还是修改操作
    var isEdit = false;
    //如果有存在productId
    if (productId) {
        //页面为修改页面
        getInfo(productId);
        isEdit = true;
    }else {
        //否则为添加页面
        getCategory();
    }
    //得到具体商品信息
    function getInfo(productId) {
        $.get(
            infoUrl,
            function (result) {
                if (result.success){
                    var product = result.product;
                    $("#name").val(product.productName);
                    $("#category").append($("<option></option>")
                        .append(product.productCategory.name).attr("data-id", product.productCategory.id))
                        .attr("disabled", "disabled");
                    $("#original_price").val(product.normalPrice);
                    $("#promote_price").val(product.promotionPrice);
                    $("#priority").val(product.priority);
                    $("#desc").val(product.productDesc);
                }else {
                    alert(result.msg);
                }
            }
        );
    }
    //得到商品类别信息
    function getCategory() {
        $.get(
            categoryUrl,
            function (result) {
                if(result.success) {
                    var list = result.data;
                    $.each(list, function (index, item) {
                        $("<option></option>").append(item.name)
                            .attr("data-id", item.id)
                            .appendTo("#category");
                    })
                }else {
                    alert(result.msg);
                }
            }
        );
    }

    $("#product_submit").click(function () {
        var product = {};
        if (isEdit) {
            product.productId = productId;
        }
        product.productName = $("#name").val();
        product.productDesc = $("#desc").val();
        product.priority = $("#priority").val();
        product.normalPrice = $("#original_price").val();
        product.promotionPrice = $("#promote_price").val();
        product.productCategory = {
            id : $("#category").find("option").not(function () {
                return !this.selected;
            }).data("id")
        };
        var formData = new FormData();
        formData.append("productStr", JSON.stringify(product));
        var code = $("#code").val();
        if (!code) {
            alert("验证码为空!");
            return false;
        }else {
            formData.append("code", code);
        }
        //得到缩略图
        var thumbnail = $("#thumbnail")[0].files[0];
        formData.append("thumbnail",thumbnail);
        //得到详情图
        $(".detail-img").map(
            function (index, item) {
                //判断是否选择了文件
                if ($(".detail-img")[index].files.length > 0) {
                    formData.append("productImg" + index, $(".detail-img")[index].files[0]);
                }
            }
        );
        $.ajax({
            url: isEdit ? updateUrl : addUrl,
            type: "POST",
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (result) {
                if (result.success) {
                    alert("提交成功");
                }else {
                    alert("提交失败: " + result.msg);
                }
                $("#product_code").click();
            }
        });
    });
    // $("#product_back").click(function () {
    //     history.back();
    // });

    //点击添加详情图后，又生成文件上传框方法
    $(".detail-img-div").on("change",".detail-img:last-child", function () {
        if ($(".detail-img").length < 6) {
            $("#detail-img").append("<input type='file' class='detail-img'>");
        }
    })
});