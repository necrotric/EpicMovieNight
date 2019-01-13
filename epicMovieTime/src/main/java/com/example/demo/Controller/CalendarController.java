package com.example.demo.Controller;
import java.util.ArrayList;
import java.util.List;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.Projections;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import com.example.demo.Classes.UserOauth;
import com.example.demo.GoogleCalendar.CalendarShowEvents;
import com.example.demo.GoogleCalendar.UpdateAccessToken;
import com.example.demo.Repository.UserOauthRepository;
import com.example.demo.entities.User;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CalendarController {
    private CalendarShowEvents calenderEvents;
    private final UserOauthRepository repository;
    private MongoOperations mongoOperation;
    CalendarShowEvents showEvents;
    @Autowired
    public CalendarController(UserOauthRepository repository,CalendarShowEvents calenderEvents,MongoOperations mongoOperation) {
        this.repository = repository;
        this.calenderEvents= calenderEvents;
        this.mongoOperation = mongoOperation;

    }

    @RequestMapping(value = "/storeauthcode", method = RequestMethod.POST)
    public String storeauthcode(@RequestBody String code, @RequestHeader("X-Requested-With") String encoding) throws IOException, GeneralSecurityException {
        final String CLIENT_ID = "194892071018-51pbsvfvvj6fnvr26u8guonj9qe6v63o.apps.googleusercontent.com";
        final String CLIENT_SECRET = "FXRSK_07hi2b2uqODS9rmowM";
        if (encoding == null || encoding.isEmpty()) {
            // Without the `X-Requested-With` header, this request could be forged. Aborts.
            return "Error, wrong headers";
        }


        GoogleTokenResponse tokenResponse = null;
        try {
            tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(),
                    JacksonFactory.getDefaultInstance(),
                    "https://www.googleapis.com/oauth2/v4/token",
                    CLIENT_ID,
                    CLIENT_SECRET,
                    code,
                    // nodehill.com blog auto-converts non https-strings to https, thus the concatenation.
                    "htt" + "p://localhost:8080")
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Store these 3in your DB
        String accessToken = tokenResponse.getAccessToken();
        String refreshToken = tokenResponse.getRefreshToken();
        Long expiresAt = System.currentTimeMillis() + (tokenResponse.getExpiresInSeconds() * 1000);

        // Debug purpose only
        System.out.println("accessToken: " + accessToken);
        System.out.println("refreshToken: " + refreshToken);
        System.out.println("expiresAt: " + expiresAt);
        // Get profile info from ID token (Obtained at the last step of OAuth2)
        GoogleIdToken idToken = null;
        try {
            idToken = tokenResponse.parseIdToken();
        } catch (IOException e) {
            e.printStackTrace();
        }
        GoogleIdToken.Payload payload = idToken.getPayload();

        // Use THIS ID as a key to identify a google user-account.
        String userId = payload.getSubject();

        String email = payload.getEmail();
        boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
        String name = (String) payload.get("name");
        String pictureUrl = (String) payload.get("picture");
        String locale = (String) payload.get("locale");
        String familyName = (String) payload.get("family_name");
        String givenName = (String) payload.get("given_name");

        // Debugging purposes, should probably be stored in the database instead (At least "givenName").
        System.out.println("userId: " + userId);
        System.out.println("email: " + email);
        System.out.println("emailVerified: " + emailVerified);
        System.out.println("name: " + name);
        System.out.println("pictureUrl: " + pictureUrl);
        System.out.println("locale: " + locale);
        System.out.println("familyName: " + familyName);
        System.out.println("givenName: " + givenName);

        UserOauth userDetails = new UserOauth();
        userDetails.setEmail(email);
        userDetails.setAccessToken(accessToken);
        userDetails.setRefreshToken(refreshToken);
        userDetails.setExpiresAt(expiresAt);
        List<UserOauth> userOauth = repository.findUserOauthByEmail(email);
        UserOauth oneUser = repository.findOneUserOauthByEmail(email);
//        System.out.println(userOauth.get(0).getEmail()+ " HERE is currently logged in user");
//


        if(repository.findUserOauthByEmail(email).isEmpty()) {
       repository.save(userDetails);
        }
        else if((!repository.findUserOauthByEmail(email).isEmpty())) {
            Query query = new Query();
            query.addCriteria(Criteria.where("email").is(email));
            query.fields().include("email");

            UserOauth userTest3 = mongoOperation.findOne(query, UserOauth.class);
            System.out.println("userTest3 - " + userTest3);

            Update update = new Update();
            update.set("accessToken", accessToken);
            update.set("refreshToken", refreshToken);

            mongoOperation.updateFirst(query, update, UserOauth.class);


        }
        updateAccessToken();
        calenderEvents.showEvents();
        return "OK";
    }
    public void updateAccessToken() {
        UpdateAccessToken updateAccess = new UpdateAccessToken();
        List<UserOauth> updateUser = repository.findAll();
        for (UserOauth anUpdateUser : updateUser) {
            // System.out.println(anUpdateUser.getEmail());
            GoogleCredential credential = updateAccess.getRefreshedCredentials(anUpdateUser.getRefreshToken());
            System.out.println(credential.getAccessToken() + " New code???");




            Query query = new Query();
            query.addCriteria(Criteria.where("email").is(anUpdateUser.getEmail()));
            query.fields().include("email");

            UserOauth userTest3 = mongoOperation.findOne(query, UserOauth.class);
            System.out.println("userTest3 - " + userTest3);

            Update update = new Update();
            update.set("accessToken", credential.getAccessToken());

            mongoOperation.updateFirst(query, update, UserOauth.class);
        }}
}
