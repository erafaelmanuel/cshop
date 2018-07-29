(function() {
    $(".catalog-item-img").mouseover(function(){
        $(this).find(".card-img-overlay").css({"display":"block"});
    });

    $(".card-img-overlay").mouseleave(function(){
        $(this).css({"display":"none"});
    });

    $(".card").mouseleave(function(){
        $(this).find(".card-img-overlay").css({"display":"none"});
    });

    $(document).on("submit", "#form-cart", function() {
        $url = $(this).attr("action");

        $.ajax({
            type: "POST",
            url: $url,
            data: objectifyForm($(this).serializeArray()),
            success: function(data) {
                $(".cart").empty();
                $(".cart").append($(data).find(".cart").html());
            }
        });
        return false;
    });

    function objectifyForm(params) {
        var data = {};
        $.each(params, function(i, param) {
            data[param.name] = param.value;
        });
        return data;
    }
})();
