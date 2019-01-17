package com.example.demo.GoogleCalendar;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import static org.springframework.security.oauth2.provider.token.AccessTokenConverter.CLIENT_ID;

public class UpdateAccessToken {

    public GoogleCredential getRefreshedCredentials(String refreshCode) {
        final String CLIENT_ID = "194892071018-51pbsvfvvj6fnvr26u8guonj9qe6v63o.apps.googleusercontent.com";
        final String CLIENT_SECRET = "FXRSK_07hi2b2uqODS9rmowM";
        try {
            GoogleTokenResponse response = new GoogleRefreshTokenRequest(
                    new NetHttpTransport(), JacksonFactory.getDefaultInstance(), refreshCode, CLIENT_ID, CLIENT_SECRET )
                    .execute();

//            System.out.println(new GoogleCredential().setAccessToken(response.getAccessToken()));
            return new GoogleCredential().setAccessToken(response.getAccessToken());
        }
        catch( Exception ex ){
            ex.printStackTrace();
            return null;
        }
    }
}
