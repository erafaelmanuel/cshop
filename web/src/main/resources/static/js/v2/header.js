(function() {
    $(document).on("click", ".navbar-toggler", function() {
        $("#navbarResponsive").toggle();
    });

    $(document).ready(function() {
        $("#myAccount-button").dropdown();
    });
})();