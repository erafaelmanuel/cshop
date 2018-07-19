(function(){
    $(document).on("click", ".notificationCancel", function() {
        $(".notif-section").css("margin-right", "0px");
        $(".notif-section").css("opacity", "0");
    });

    $(document).on("submit", "#formLogin", function() {
            var xmlhttp = new XMLHttpRequest();
            var params = $(this).serialize();
            var url = $(this).attr("action") + "?" + params;

            $(".notif-section").css("margin-right", "0px");
            $(".notif-section").css("opacity", "0");
            xmlhttp.onreadystatechange = function() {
                if(xmlhttp.status == 200 && xmlhttp.readyState == 4) {

                }
                if (xmlhttp.status == 500 && xmlhttp.readyState == 4) {
                    var $notif =  jQuery.parseJSON(xmlhttp.responseText);

                    $(".notif-section").find(".notificationBody h6").html($notif.title);
                    $(".notif-section").find(".notificationBody p").html($notif.message);
                    $(".notif-section").css("margin-right", "20px");
                    $(".notif-section").css("opacity", "1");
                }
            }
            xmlhttp.open("POST", url, true);
            xmlhttp.send();
            return false;
        });
})();