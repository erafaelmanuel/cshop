(function(){
        $(document).on("mouseenter", ".list-item", function() {
            $item = $(this);
            var categoryId = $item.attr("categoryId");

            $.ajax({
                type: "GET",
                url: "/api/categories/search/findByParentId",
                data: {
                    "categoryId" : categoryId,
                    "size" : 100
                    },
                success: function(data) {
                    if (data.page.totalPages > 0) {
                        $item.addClass("item-collapse");
                        $item.find(".category-child").empty();
                        $item.append($(document.createElement('ul'))
                            .css({"height": $(".category-parent").css("height")})
                            .addClass("list category-child"));

                        $.each(data._embedded.categories, function(i, category) {
                            $href = category.uid + ".html";

                            $listItem = $(document.createElement('li'))
                                .addClass("list-item")
                                .attr("categoryId", category.uid)
                                .append($(document.createElement('a'))
                                    .attr({"href": $href})
                                    .html(category.name));
                            $item.find(".category-child").append($listItem);
                        });
                    }
                }
            });
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