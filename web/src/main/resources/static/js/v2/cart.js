(function() {
    var xmlhttp = new XMLHttpRequest();

    $(".item-option-add-to-cart").click(function() {
        $(this).parent().submit();
    });

    $(document).on("submit", ".catalog-item-option li form", function() {
        var url = $(this).attr("action") + "?" + $(this).serialize();
        xmlhttp.onreadystatechange = function() {
            if(xmlhttp.status == 200 && xmlhttp.readyState == 4) {
                 $(".cart-item").empty();
                 $(".cart-item").append($(xmlhttp.responseText).find(".cart-item").html());
            }
        }
        xmlhttp.open("GET", url, true);
        xmlhttp.send();

        return false;
    });
})();
