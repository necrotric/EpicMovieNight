let attempt = 3;
function validate(){
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;
    if ( username === "user" && password === "user"){
        window.open("/main.html"); // Redirecting to other page.
        return false;
    }
    else{
        attempt --;
        alert("You have "+attempt+" attempts left");
    }
    if (attempt === 0) {
        alert("To many attempts, try again in 60 seconds");
        setTimeout(unlock,60000)
        document.getElementById("username").disabled = true;
        document.getElementById("password").disabled = true;
        document.getElementById("loginBt").disabled = true;
        return false;
    }
}

function unlock () {
        attempt = 1;
        document.getElementById("username").disabled = false;
        document.getElementById("password").disabled = false;
        document.getElementById("submit").disabled = false;
        return false;
}