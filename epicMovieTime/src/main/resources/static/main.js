const CLIENT_ID = "194892071018-51pbsvfvvj6fnvr26u8guonj9qe6v63o.apps.googleusercontent.com";


function start() {
    gapi.load('auth2', function() {
        let auth2 = gapi.auth2.init({
            client_id: CLIENT_ID,
            <!-- nodehill.com blog auto-converts non https-strings to https, thus the concatenation. -->
            scope: "htt"+"ps://www.googleapis.com/auth/calendar.events"
        });
    });
}

$('#signinButton').click(function() {
// signInCallback defined in step 6.
    auth2.grantOfflineAccess().then(signInCallback);
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
