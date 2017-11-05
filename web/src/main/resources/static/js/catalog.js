(function(){
    var xmlhttp = new XMLHttpRequest();
    $(".form-add-to-cart").on("submit", function() {
        var url = $(this).attr("action") + "?" + $(this).serialize();
        xmlhttp.onreadystatechange = function() {
            if(xmlhttp.status == 200 && xmlhttp.readyState == 4) {
                 $("#dropdown-cart").empty();
                 $("#dropdown-cart").append($(xmlhttp.responseText).find("#dropdown-cart").html());
            }
        }
        xmlhttp.open("GET", url, true);
        xmlhttp.send();

        return false;
    });
})();