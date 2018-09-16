$(function () {

    var listUrl = "/shopadmin/productcategorylist";
    var addUrl = "/shopadmin/addproductcategory";
    var deleteUrl = "/shopadmin/deleteproductcategory";

    getList();

    //得到商品分类列表方法
    function getList() {
        $.get(
            listUrl,
            function (result) {
                if (result.success) {
                   var list = result.data;
                   $.each(list, function (index, item) {
                       var td1 = $("<td></td>").append(item.name);
                       var td2 = $("<td></td>").append(item.priority);
                       // var td3 = $("<td></td>").append($("<a></a>")
                       //     .html("删除").attr("data-id",item.id).attr("href",deleteUrl+item.id));
                       var td3 = $("<td></td>").append($("<button class='mybutton btn btn-danger'></button>")
                           .html("删除").attr("data-id",item.id));
                       $("<tr></tr>").append(td1).append(td2).append(td3).appendTo("#mytbody");
                   })
                }else {
                    alert(result.msg);
                }
            }
        );
    }
    //返回按钮事件
    $("#back").click(function () {
        history.back();
    });
    //添加事件
    $("#add").click(function () {
        var td1 = $("<td></td>").append($("<input>").attr("class","name"));
        var td2 = $("<td></td>").append($("<input>").attr("class","priority"));
        var td3 = $("<td></td>").append($("<button class='del btn btn-secondary'></button>").html("取消"));
        $("<tr></tr>").attr("class","mytr").append(td1).append(td2).append(td3).appendTo(".old").appendTo(".table");
    });
    //提交按钮事件
    $("#submit").click(function () {
        var list = [];
        var tempArr = $("#mytbody tr.mytr");
        $.each(tempArr,function (index, item){
           var tempObj = {};
           tempObj.name = $(".mytr").find(".name").val();
           tempObj.priority = $("tr input.priority").val();
           if (tempObj.name && tempObj.priority) {
               list.push(tempObj);
           }
        });
        $.ajax({
            url:addUrl,
            type:"POST",
            data:JSON.stringify(list),
            contentType:"application/json",
            success:function (result) {
                if (result.success) {
                    alert("提交成功");
                    location.reload();
                }else {
                    alert(result.msg);
                }
            }
        });
    });
    //取消按钮事件
    $(".table").on("click",".del",function() {
        console.log($(this).parent().parent().remove());
        $(this).parent().parent().remove();
    });

    //动态增加元素，使用on函数触发点击事件，第一个是父类元素，第二个是事件，第三个是要点击的元素
    $(".table").on("click", ".mybutton",function (e) {
        var target = e.currentTarget;
        $.confirm({
            title:"确定",
            content:"是否删除?",
            type:"green",
            buttons:{
                ok: {
                    action:function () {
                        $.ajax({
                            url:deleteUrl,
                            type: "POST",
                            data: {id:target.dataset.id},
                            dataType:"json",
                            success:function (result) {
                                if (result.success) {
                                    alert("删除成功");
                                    location.reload();
                                }else {
                                    alert("删除失败,因为: " + result.msg);
                                }
                            }
                        });
                    }
                },
                cancel:{
                }
            }
        });
    });
});

