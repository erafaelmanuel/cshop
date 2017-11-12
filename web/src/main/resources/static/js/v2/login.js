(function(){
    $(document).on("submit",".login-form", function(){
        return validate();
    });

    function validate() {
        var isValid = true;
        if($("input[name='username']").val().trim() === "") {
            $("input[name='username']").css("border","1px solid #e74c3c");
            isValid = false;
        }
        if($("input[name='password']").val().trim() === "") {
            $("input[name='password']").css("border","1px solid #e74c3c");
            isValid = false;
        }
        return isValid;
    }
})();