(function() {
    document.getElementById("form-login").onsubmit = function(){
        var username = document.getElementById("username");
        var password = document.getElementById("password");
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
        document.querySelector("#username").addEventListener("focusin", function() {
            document.querySelector("#username + label").style.color="#16a085";
            document.querySelector("#username + label").style.opacity=1;
            document.querySelector("#username + label").innerHTML="Username";
        });

        document.querySelector("#password").addEventListener("focusin", function() {
            document.querySelector("#password + label").style.color="#16a085";
            document.querySelector("#password + label").style.opacity=1;
            document.querySelector("#password + label").innerHTML="Password";
        });

        document.querySelector("#username").addEventListener("focusout", function() {
            document.querySelector("#username + label").style.color="#000";
            document.querySelector("#username + label").style.opacity=0.6;
        });
        document.querySelector("#password").addEventListener("focusout", function() {
            document.querySelector("#password + label").style.color="#000";
            document.querySelector("#password + label").style.opacity=0.6;
        });

        
    })();

    function validate(username, password) {
        var isValid = true;

        if(username.value.trim() === "") {
            document.querySelector("#username + label").style.color="#c0392b";
            document.querySelector("#username + label").innerHTML="Please enter a username";
            isValid = false;
        }
        if(password.value.trim() === "") {
            document.querySelector("#password + label").style.color="#c0392b";
            document.querySelector("#password + label").innerHTML="Please enter a password";
            isValid = false;
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