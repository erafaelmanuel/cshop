(function(){
    var xmlhttp = new XMLHttpRequest();

        $(document).on("mouseover", ".category-1 .list-item", function() {
            var params = "cid=" + $(this).attr("categoryId");
            var url = "/category/subCategoriesOf?" + params;
            xmlhttp.onreadystatechange = function() {
                $(".category-2").empty();
                $(".category-2").css("display", "none");
                $(".category-2").css("height", $(".category-1").css("height"));
                if(xmlhttp.status == 200 && xmlhttp.readyState == 4) {
                    if ($(xmlhttp.responseText).find(".list-item").length > 0) {
                        $(".category-2").css("display", "block");
                        $(".category-2").append($(xmlhttp.responseText).find(".list").html());
                    } else {
                        $(".category-2").css("display", "none");
                    }
                    console.log($(xmlhttp.responseText).find(".list").html());
                    console.log(xmlhttp.responseText);
                }
            }
            xmlhttp.open("POST", url, true);
            xmlhttp.send();
        });

        $(document).on("mouseleave", ".categories", function() {
            $(".category-2").empty();
            $(".category-2").css("display", "none");
            $(".category-1").css("display", "none");
        });

        $(document).on("mouseover", ".categories", function() {
            $(".category-1").css("display", "block");
        });


})();