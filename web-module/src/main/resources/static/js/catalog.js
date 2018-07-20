(function(){

  $(".catalog-item-img").mouseover(function(){
    $(this).find(".card-img-overlay").css("display","block");
  });

   $(".card-img-overlay").mouseleave(function(){
     $(this).css("display","none");
   });

   $(".card").mouseleave(function(){
        $(this).find(".card-img-overlay").css("display","none");
   });
})();

(function() {
    var xmlhttp = new XMLHttpRequest();

    $(document).on("submit", "#form-cart", function() {
        var params = $(this).serialize();
        var url = $(this).attr("action") + "?" + params;
        xmlhttp.onreadystatechange = function() {
            if(xmlhttp.status == 200 && xmlhttp.readyState == 4) {
                $(".cart").empty();
                $(".cart").append($(xmlhttp.responseText).find(".cart").html());
            }
        }
        xmlhttp.open("POST", url, true);
        xmlhttp.send();

        return false;
    });
})();
