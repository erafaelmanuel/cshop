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

    $("#btn-cart-add").click(function() {
        $("#form-cart-add").submit();
    });

    $(document).on("submit", "#form-cart-add", function() {
        var params = $(this).serialize();
        var url = $(this).attr("action") + "?" + params;
        xmlhttp.onreadystatechange = function() {
            if(xmlhttp.status == 200 && xmlhttp.readyState == 4) {
                $("#cart-badge").empty();
                $("#cart-badge").append($(xmlhttp.responseText).find("#art-badge").html());
                modalContent(xmlhttp, params);
            }
        }
        xmlhttp.open("POST", url, true);
        xmlhttp.send();

        return false;
    });

    function modalContent(xmlhttp, params) {
        var url = "/catalog/show/modal?" + params;
        xmlhttp.onreadystatechange = function() {
            if(xmlhttp.status == 200 && xmlhttp.readyState == 4) {
                 $(".modal-body").empty();
                 $(".modal-body").append($(xmlhttp.responseText).find(".modal-body").html());
                 $('#cart-modal').modal('show');
            }
        }
        xmlhttp.open("POST", url, true);
        xmlhttp.send();
    }
})();
