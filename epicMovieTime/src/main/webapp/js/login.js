// function signOut() {
//     let auth2 = gapi.auth2.getAuthInstance();
//     auth2.signOut().then(function () {
//         console.log('User signed out.');
//     });
// }
//
// function onSignIn(googleUser) {
//     // Useful data for your client-side scripts:
//     let profile = googleUser.getBasicProfile();
//     console.log("ID: " + profile.getId()); // Don't send this directly to your server!
//     console.log('Full Name: ' + profile.getName());
//     console.log('Given Name: ' + profile.getGivenName());
//     console.log('Family Name: ' + profile.getFamilyName());
//     console.log("Image URL: " + profile.getImageUrl());
//     console.log("Email: " + profile.getEmail());
//
//     // The ID token you need to pass to your backend:
//     let id_token = googleUser.getAuthResponse().id_token;
//     console.log("ID Token: " + id_token);
// };

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