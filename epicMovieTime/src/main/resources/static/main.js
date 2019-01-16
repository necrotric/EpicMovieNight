const CLIENT_ID = "194892071018-51pbsvfvvj6fnvr26u8guonj9qe6v63o.apps.googleusercontent.com";

$('#signinButton').click(function() {

    let auth2 = gapi.auth2.init({
        client_id: CLIENT_ID,
        scope: "htt"+"ps://www.googleapis.com/auth/calendar.events"
    });
    auth2.grantOfflineAccess().then(signInCallback);
});

function signInCallback(authResult) {
    console.log('authResult', authResult);
    if (authResult['code']) {

        $('#signinButton').attr('style', 'display: none');
        $('#signOut').attr('style', 'display: block');
        $('#mainButton').attr('style', 'display: block');

        $.ajax({
            type: 'POST',
            url: 'htt' + 'p://localhost:8080/storeauthcode',
            headers: {
                'X-Requested-With': 'XMLHttpRequest'
            },
            contentType: 'application/octet-stream; charset=utf-8',
            success: function(result) {
            },
            processData: false,
            data: authResult['code']
        });
    } else {
        // There was an error.
    }
}
