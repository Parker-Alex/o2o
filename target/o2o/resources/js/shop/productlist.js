$(function () {
    var getListUrl = "/shopadmin/getproductlist?pageNum=1&pageSize=100";
    var changeStatusUrl = "/shopadmin/changestatus";

    getProductList();

    function getProductList() {
        $.ajax({
            url:getListUrl,
            type:"get",
            success:function (result) {
                if (result.success) {
                    showProduct(result.productList);
                } else {
                    alert(result.msg);
                }
            }

        });
    }

    function showProduct(data) {
        $.each(data, function (index, item) {
            var pid = item.productId;
            var url = "/shop/productop?pid=" + pid;
            var status = item.status;
            var nowStatus = "下架";
            if (status == 0) {
                nowStatus = "上架";
                status = 1;
            }else {
                status = 0;
            }
            var td1 = $("<td></td>").append(item.productName);
            var td2 = $("<td></td>").append(item.priority);
            var td3 = $("<td></td>").append($("<a>编辑</a>").attr("href", url))
                .append($("<a href='#' style='margin: 10px' id='changestatus'></a>")
                    .append(nowStatus).attr("data-id", pid).attr("status", status))
                .append($("<a href='#'>预览</a>"));
            $("<tr></tr>").append(td1).append(td2).append(td3).appendTo("#mytbody");
        })
    }

    $("#mytbody").on("click","#changestatus", function () {
        // alert($(this).attr("data-id"));
        var product = {};
        product.productId = $(this).attr("data-id");
        product.status = $(this).attr("status");
        $.confirm({
            title:"确定",
            content:"是否继续",
            type:"green",
            buttons:{
                ok:{
                    action:function() {
                        $.ajax({
                            url:changeStatusUrl,
                            type:"post",
                            data:{ productStr:JSON.stringify(product),
                            //    设置为true，跳过验证码校验
                            statusChange:true},
                            dateType:"json",
                            success:function (result) {
                                if (result.success) {
                                    alert("操作成功");
                                    location.reload();
                                }else {
                                    alert(result.msg);
                                }
                            }
                        });
                    }
                },
                cancel:{}
            }
        });
    })
});