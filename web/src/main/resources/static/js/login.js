(function() {
    var username = document.getElementById("username");
    var password = document.getElementById("password");

    document.getElementById("form-login").onsubmit = function(){
        if(!validate(username, password)) return false;
    };

    function validate(username, password) {
        var isValid = true;

        if(username.value.trim() === "") {
            username.style.border = "1px solid #c0392b";
            isValid = false;
        } else {
            username.style.border = "1px solid #bdc3c7";
        }
        if(password.value.trim() === "") {
            password.style.border = "1px solid #c0392b";
            isValid = false;
        } else {
            password.style.border = "1px solid #bdc3c7";
        }
        return isValid;
    }
})();