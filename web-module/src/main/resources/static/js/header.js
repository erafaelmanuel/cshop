(function() {
    $(document).on("click", ".navbar-toggler", function() {
        $("#navbarResponsive").toggle();
    });

    $(document).ready(function() {
        $("#myAccount-button").dropdown();
    });

    $(function () {
        $('[data-toggle="tooltip"]').tooltip();
     });

    $(document).on("show.bs.tooltip", '[data-toggle="tooltip"]', function() {
        $('[data-toggle="tooltip"]').tooltip();
    });

     $(document).on("hide.bs.tooltip", '[data-toggle="tooltip"]', function() {
        $('[data-toggle="tooltip"]').tooltip();
     });
})();