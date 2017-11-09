(function(){
    var xmlhttp = new XMLHttpRequest();

    $(document).on("submit", ".form-add-to-cart", function() {
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

    $(document).ready(function(){
        var url = "/api/category";
        var categories;

        xmlhttp.onreadystatechange = function() {
            if(xmlhttp.status == 200 && xmlhttp.readyState == 4) {
               categories = JSON.parse(xmlhttp.responseText);
               $('#tree').treeview({
                    data: loadAndStartWithParentId(null),
                    enableLinks: true,
                    showBorder: false,
                    highlightSelected: false
               });
            }
        }
        xmlhttp.open("GET", url, true);
        xmlhttp.send();

        function loadAndStartWithParentId(parentId) {
            var _cat = [];
            for(var c of categories) {
                if(c.parentId == parentId) {
                    c.text = c.name;
                    c.href="/catalog?c=" + c.id;
                    c.nodes = loadAndStartWithParentId(c.id);
                    _cat.push(c);
                }
            }
            return _cat;
        }
    });
})();