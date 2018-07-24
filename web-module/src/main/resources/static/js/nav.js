(function(){
    var xmlhttp = new XMLHttpRequest();

        $(document).on("mouseenter", ".list-item", function() {
            $item = $(this);
            var params = "cid=" + $item.attr("categoryId");
            var url = "/category/subCategoriesOf?" + params;

            xmlhttp.onreadystatechange = function() {
                if(xmlhttp.status == 200 && xmlhttp.readyState == 4) {
                    if ($(xmlhttp.responseText).find(".list-item").length > 0) {
                        $item.addClass("item-collapse");
                        if ( $item.find(".category-child").length > 0) {
                            $item.find(".category-child").empty();
                        } else {
                            $item.append($(document.createElement('ul'))
                                .css("height", $(".category-parent").css("height"))
                                .addClass("list category-child")
                            );
                        }
                        $item.find(".category-child").append($(xmlhttp.responseText).find(".list-item"));
                    }
                }
            }
            xmlhttp.open("POST", url, true);
            xmlhttp.send();
        });

        $(document).on("mouseleave", ".list-item", function() {
            $(this).removeClass("item-collapse");
            $(this).find(".list").remove();
        });

        $(document).on("mouseenter", ".categories", function() {
            $(".category-parent").css("display", "block");
            clearTimeout($(this).data('timeout'));
        });

        $(document).on("mouseleave", ".categories", function() {
            var timeout = setTimeout(function() {
                $(".category-parent").css("display", "none");
            }, 500);
            $(this).data("timeout", timeout);
        });

})();