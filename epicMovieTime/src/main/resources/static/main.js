const CLIENT_ID = "613443354164-5kcj891c1oo134499n7q07nla718nuh8.apps.googleusercontent.com";


function start() {
    gapi.load('auth2', function() {
        auth2 = gapi.auth2.init({
            client_id: CLIENT_ID,
            <!-- nodehill.com blog auto-converts non https-strings to https, thus the concatenation. -->
            scope: "htt"+"p://www.googleapis.com/auth/calendar.events"
        });
    });
}
function onSignIn(googleUser) {
    var profile = googleUser.getBasicProfile();
    console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
    console.log('Name: ' + profile.getName());
    console.log('Image URL: ' + profile.getImageUrl());
    console.log('Email: ' + profile.getEmail());// This is null if the 'email' scope is not present.
}

$('#signinButton').click(function() {
// signInCallback defined in step 6.
    var ga = gapi.auth2.getAuthInstance();
    ga.grantOfflineAccess().then(signInCallback);
});

function signInCallback(authResult) {
    console.log('authResult', authResult);
    if (authResult['code']) {


        // Hide the sign-in button now that the user is authorized, for example:
        $('#signinButton').attr('style', 'display: none');

        // Send the code to the server
        $.ajax({
            type: 'POST',
            <!-- nodehill.com blog auto-converts non https-strings to https, thus the concatenation. -->
            url: 'htt' + 'p://localhost:8080/storeauthcode',
            // Always include an `X-Requested-With` header in every AJAX request,
            // to protect against CSRF attacks.
            headers: {
                'X-Requested-With': 'XMLHttpRequest'
            },
            contentType: 'application/octet-stream; charset=utf-8',
            success: function(result) {
                // Handle or verify the server response.
            },
            processData: false,
            data: authResult['code']
        });
    } else {
        // There was an error.
    }
}