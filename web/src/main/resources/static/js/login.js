(function() {
    var username = document.getElementById("username");
    var password = document.getElementById("password");

    document.getElementById("form-login").onsubmit = function(){
        if(!validate(username, password)) return false;
    };

    (function() {
        var elements = document.getElementsByClassName("form-control");
        for (e of elements) {
            e.addEventListener("focusout", function(){
                changeState(this);
            })
            changeState(e);
        }
    })();

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

    function changeState(formControl){
        if(formControl.value.length > 0){
        formControl.className += " has-value";
      }
      else{
        formControl.className = "form-control";
      }  
    }
})();