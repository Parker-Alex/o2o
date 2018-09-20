$(function () {
   var shopId = getQueryString("shopId");
   var shopInfourl = "/shopadmin/getshopmanagementinfo?shopId=" + shopId;

   $.getJSON(shopInfourl, function(data) {
      if (data.redirect) {
          location.href = data.url;
      } else {
          if (data.shopId != undefined && data.shopId != null) {
              shopId = data.shopId;
          }
          $("#shopinfo").attr("href", "/shop/opshop?shopId=" + shopId);
      }
   });

   $("#back").click(function () {
       history.back();
   });
});