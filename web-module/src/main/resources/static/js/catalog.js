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
