(function(){
    $(document).on("submit",".login-form", function(){
        return validateLogin();
    });

    function validateLogin() {
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

    $(document).on("submit",".register-form", function(){
         return validateRegister();
     });

    function validateRegister() {
        var isValid = true;
        if($("input[name='name']").val().trim() === "") {
            $("input[name='name']").css("border","1px solid #e74c3c");
            isValid = false;
        }
        if($("input[name='password']").val().trim() === "") {
            $("input[name='password']").css("border","1px solid #e74c3c");
            isValid = false;
        }
        if($("input[name='email']").val().trim() === "") {
            $("input[name='email']").css("border","1px solid #e74c3c");
            isValid = false;
        }
        return isValid;
    }
})();