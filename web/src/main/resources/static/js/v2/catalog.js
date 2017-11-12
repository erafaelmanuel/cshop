(function(){

  $(function () {
    $('[data-toggle="tooltip"]').tooltip();
  });

  $(document).on("show.bs.tooltip", '[data-toggle="tooltip"]', function() {
    $('[data-toggle="tooltip"]').tooltip();
  });

  $(document).on("hide.bs.tooltip", '[data-toggle="tooltip"]', function() {
      $('[data-toggle="tooltip"]').tooltip();
   });

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
