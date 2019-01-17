package com.example.demo.Controller;

import java.util.List;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import com.example.demo.Classes.UserOauth;
import com.example.demo.GoogleCalendar.CalendarShowEvents;
import com.example.demo.GoogleCalendar.UpdateAccessToken;
import com.example.demo.Repository.UserOauthRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.security.GeneralSecurityException;


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
                    "htt" + "p://localhost:8080")
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }


        String accessToken = tokenResponse.getAccessToken();
        String refreshToken = tokenResponse.getRefreshToken();
        Long expiresAt = System.currentTimeMillis() + (tokenResponse.getExpiresInSeconds() * 1000);

        GoogleIdToken idToken = null;
        try {
            idToken = tokenResponse.parseIdToken();
        } catch (IOException e) {
            e.printStackTrace();
        }
        GoogleIdToken.Payload payload = idToken.getPayload();

        String userId = payload.getSubject();

        String email = payload.getEmail();
        boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
        String name = (String) payload.get("name");
        String pictureUrl = (String) payload.get("picture");
        String locale = (String) payload.get("locale");
        String familyName = (String) payload.get("family_name");
        String givenName = (String) payload.get("given_name");

//        System.out.println("userId: " + userId);
//        System.out.println("email: " + email);
//        System.out.println("emailVerified: " + emailVerified);
//        System.out.println("name: " + name);
//        System.out.println("pictureUrl: " + pictureUrl);
//        System.out.println("locale: " + locale);
//        System.out.println("familyName: " + familyName);
//        System.out.println("givenName: " + givenName);

        UserOauth userDetails = new UserOauth();
        userDetails.setEmail(email);
        userDetails.setAccessToken(accessToken);
        userDetails.setRefreshToken(refreshToken);
        userDetails.setExpiresAt(expiresAt);
        List<UserOauth> userOauth = repository.findUserOauthByEmail(email);
        UserOauth oneUser = repository.findOneUserOauthByEmail(email);



        if(repository.findUserOauthByEmail(email).isEmpty()) {
       repository.save(userDetails);
        }
        else if((!repository.findUserOauthByEmail(email).isEmpty())) {
            Query query = new Query();
            query.addCriteria(Criteria.where("email").is(email));
            query.fields().include("email");

            UserOauth userTest3 = mongoOperation.findOne(query, UserOauth.class);

            Update update = new Update();
            update.set("accessToken", accessToken);
            update.set("refreshToken", refreshToken);

            mongoOperation.updateFirst(query, update, UserOauth.class);


        }
        calenderEvents.showEvents();
        return "OK";
    }
    public void updateAccessToken() {
        UpdateAccessToken updateAccess = new UpdateAccessToken();
        List<UserOauth> updateUser = repository.findAll();
        for (UserOauth anUpdateUser : updateUser) {
            GoogleCredential credential = updateAccess.getRefreshedCredentials(anUpdateUser.getRefreshToken());





            Query query = new Query();
            query.addCriteria(Criteria.where("email").is(anUpdateUser.getEmail()));
            query.fields().include("email");

            UserOauth userTest3 = mongoOperation.findOne(query, UserOauth.class);


            Update update = new Update();
            update.set("accessToken", credential.getAccessToken());

            mongoOperation.updateFirst(query, update, UserOauth.class);
        }}
}
