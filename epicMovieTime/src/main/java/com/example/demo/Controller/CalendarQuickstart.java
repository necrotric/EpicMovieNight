//package com.example.demo.Controller;
//
//import com.example.demo.Repository.UserRepository;
//import com.example.demo.entities.User;
//import com.example.demo.services.UserService;
//import com.google.api.client.auth.oauth2.Credential;
//import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
//import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
//import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
//import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.client.util.DateTime;
//import com.google.api.client.util.store.FileDataStoreFactory;
//import com.google.api.services.calendar.Calendar;
//import com.google.api.services.calendar.CalendarScopes;
//import com.google.api.services.calendar.model.Event;
//import com.google.api.services.calendar.model.Events;
//import org.springframework.beans.factory.annotation.Autowired;
//
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.security.GeneralSecurityException;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//import com.example.demo.Repository.MovieRepository;
//import com.example.demo.Classes.Movies;
//import com.example.demo.Classes.Search;
//import com.example.demo.entities.User;
//import com.example.demo.services.UserService;
//import com.google.api.services.calendar.model.Event;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.client.RestTemplate;
//
//import java.io.IOException;
//import java.security.GeneralSecurityException;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//@Service
//public class CalendarQuickstart {
//    final UserService userService;
//    private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
//    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
//    private static final String TOKENS_DIRECTORY_PATH = "tokens";
////    private UserService userService;
////    private UserRepository repo;
////    private static List<User> usersCalendar;
//    /**
//     * Global instance of the scopes required by this quickstart.
//     * If modifying these scopes, delete your previously saved tokens/ folder.
//     */
//    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
//    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
//
//    @Autowired
//    public CalendarQuickstart(UserService userService) {
//        this.userService = userService;
//    }
//
//    /**
//     * Creates an authorized Credential object.
//     *
//     * @param HTTP_TRANSPORT The network HTTP Transport.
//     * @return An authorized Credential object.
//     * @throws IOException If the credentials.json file cannot be found.
//     */
//    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
//        // Load client secrets.
//        InputStream in = CalendarQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
//        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
//
//        // Build flow and trigger user authorization request.
//        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
//                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
//                .setAccessType("offline")
//                .build();
//        System.out.println(flow.getCredentialDataStore());
//        //System.out.println(getCredentials(HTTP_TRANSPORT));
//        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
//        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
//    }
//
//    public void main(String... args) throws IOException, GeneralSecurityException {
//        // Build a new authorized API client service.
//        showEvents();
//    }
//
//    //    public void dostuff(ArrayList<List<User>> asdasda){
////        System.out.println(asdasda);
////    }
//    public ArrayList<List<Event>> showEvents() throws IOException, GeneralSecurityException {
//        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
//        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
//                .setApplicationName(APPLICATION_NAME)
//                .build();
//
//           System.out.println("HERE AM I" +userService.getAllUsers().toString());
////        System.out.println(userService.getAllUsers().size());
////        System.out.println(userService.getAllUsers().get(0).getUsername());
//        // List the next 10 events from the primary calendar.
//        DateTime now = new DateTime(System.currentTimeMillis());
//        ArrayList<String> alotUsers = new ArrayList<>();
//        alotUsers.add("necrotric@gmail.com");
//        alotUsers.add("johanmovienight12@gmail.com");
//        alotUsers.add("epicmovienight12@gmail.com");
//        ArrayList<List<Event>> allEvents = new ArrayList<>();
//        for (int i = 0; i < alotUsers.size(); i++) {
//
//
//            Events events = service.events().list(alotUsers.get(i))
//                    .setMaxResults(10)
//                    .setTimeMin(now)
//                    .setOrderBy("startTime")
//                    .setSingleEvents(true)
//                    .execute();
//
//            List<Event> items = events.getItems();
//            if (items.isEmpty()) {
//                System.out.println("No upcoming events found.");
//            } else {
//                System.out.println("Upcoming events");
//                for (Event event : items) {
//                        DateTime start = event.getStart().getDateTime();
//                        if (start == null) {
//                            start = event.getStart().getDate();
//                        }
//                    System.out.printf("%s (%s)\n", event.getSummary(), start);
//                }
//            }
//            allEvents.add(items);
//        }
//        return allEvents;
//    }
//}