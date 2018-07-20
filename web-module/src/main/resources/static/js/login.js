(function(){
    $(document).on("click", ".notif-cancel", function() {
        $(".notif-section").css("margin-right", "0px");
        $(".notif-section").css("opacity", "0");
    });

    $(document).on("submit", "#form-login", function() {
            var xmlhttp = new XMLHttpRequest();
            var params = $(this).serialize();
            var url = $(this).attr("action") + "?" + params;

            $(".notif-section").css("margin-right", "0px");
            $(".notif-section").css("opacity", "0");
            xmlhttp.onreadystatechange = function() {
                if(xmlhttp.status == 200 && xmlhttp.readyState == 4) {
                    window.location = '/catalog';
                }
                if (xmlhttp.status == 500 && xmlhttp.readyState == 4) {
                    var $notif =  jQuery.parseJSON(xmlhttp.responseText);

                    setTimeout(function() {
                        $(".notif-section").find(".notif-body h6").html($notif.title);
                        $(".notif-section").find(".notif-body p").html($notif.message);
                        $(".notif-section").css("margin-right", "20px");
                        $(".notif-section").css("opacity", "1");
                    }, 300);
                }
            }
            xmlhttp.open("POST", url, true);
            xmlhttp.send();
            return false;
        });
})();