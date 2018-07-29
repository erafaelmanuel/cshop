(function(){
    $(document).on("click", ".notif-cancel", function() {
        $(".notif-section").css({
            "margin-right": "0px",
            "opacity": 0
        });
    });

    $(document).on("submit", "#form-login", function() {
        $.ajax({
            type: "POST",
            url: $(this).attr("action"),
            data: objectifyForm($(this).serializeArray()),
            success: function(data) {
                window.location = '/catalog';
            },
            error: function(data) {
                setTimeout(function() {
                    $(".notif-section").find(".notif-body h6").html(data.responseJSON.title);
                    $(".notif-section").find(".notif-body p").html(data.responseJSON.message);
                    $(".notif-section").css("margin-right", "20px");
                    $(".notif-section").css("opacity", "1");
                }, 300);
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