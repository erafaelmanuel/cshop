(function() {
    var xmlhttp = new XMLHttpRequest();

    $(".item-option-add-to-cart").click(function() {
        $(this).parent().submit();
    });

    $(document).on("submit", ".catalog-item-option li form", function() {
        var params = $(this).serialize();
        var url = $(this).attr("action") + "?" + params;
        xmlhttp.onreadystatechange = function() {
            if(xmlhttp.status == 200 && xmlhttp.readyState == 4) {
                $(".cart-item").empty();
                $(".cart-item").append($(xmlhttp.responseText).find(".cart-item").html());
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
