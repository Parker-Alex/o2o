$(function () {

    getlist();

    function getlist() {
        $.ajax({
            url:"/o2o/shopadmin/getshoplist",
            type:"get",
            dataType:"json",
            success:function (result) {
                if (result.success) {
                    showList(result.shopList);
                    showUser(result.user);
                }
            }
        });
    }
    //展示用户名称方法
    function showUser(data) {
        $("#userName").append("用户名：" + data.userName);
    }
    //展示店铺相关信息方法
    function showList(data) {
        $.each(data, function(index, item){
            var td1 = $("<td></td>").append(item.shopName);
            var td2 = $("<td></td>").append(shopStatus(item.status));
            // var td3 = $("<td></td>").append(doshop(item.status, item.shopId));
            var td3 = $("<td></td>").append(doshop(item.status, item.shopId));
            $("<tr></tr>").append(td1).append(td2).append(td3).appendTo("#mytbody");

        });
    }
    //将状态码转化为对应信息
    function shopStatus(status) {
        if (status == 0) {
            return "审核中";
        }else if (status == -1) {
            return "店铺下线";
        }else if (status == 2) {
            return "审核通过";
        }
    }

    function doshop(status, id) {
        if (status == 2) {
            url = "/o2o/shop/shopmanagement?shopId=" + id;
            return $("<a></a>").append("管理").attr("href", url);
        }else {
            return "";
        }
    }
});