$(function () {

    var listUrl = "/shopadmin/productcategorylist";
    var addUrl = "/shopadmin/addproductcategory";
    var deleteUrl = "/shopadmin/deleteproductcategory?id=";

    getList();

    function getList() {
        $.get(
            listUrl,
            function (result) {
                if (result.success) {
                   var list = result.data;
                   $.each(list, function (index, item) {
                       var td1 = $("<td></td>").append(item.name);
                       var td2 = $("<td></td>").append(item.priority);
                       var td3 = $("<td></td>").append($("<a></a>")
                           .html("删除").attr("data-id",item.id).attr("href",deleteUrl+item.id));
                       $("<tr></tr>").append(td1).append(td2).append(td3).appendTo("#mytbody");
                   })
                }else {
                    alert(result.msg);
                }
            }
        );
    }

    $("#add").click(function () {
        var td1 = $("<td></td>").append($("<input>").attr("class","name"));
        var td2 = $("<td></td>").append($("<input>").attr("class","priority"));
        var td3 = $("<td></td>").append($("<a></a>")
            .html("删除").attr("href", "#"));
        $("<tr></tr>").attr("class","mytr").append(td1).append(td2).append(td3).appendTo(".old").appendTo(".table");
    });

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

});