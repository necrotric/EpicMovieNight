package com.example.demo.Controller;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

public class NeweventTest {
   private static ArrayList<String> allUser = new ArrayList<String>();


    private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
   private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    public static void main(String[] args) throws IOException, GeneralSecurityException {
        gogo();
    }
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        allUser.add("epicmovienight12@gmail.com");
        allUser.add("necrotric@gmail.com");

        // Load client secrets.
        InputStream in = CalendarQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        System.out.println(flow.getCredentialDataStore());
        //System.out.println(getCredentials(HTTP_TRANSPORT));
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
public static void gogo() throws IOException, GeneralSecurityException {

    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
            .setApplicationName(APPLICATION_NAME)
            .build();
    Event event = new Event()
            .setSummary("Google I/O 2015")
            .setLocation("800 Howard St., San Francisco, CA 94103")
            .setDescription("A chance to hear more about Google's developer products.");

    DateTime startDateTime = new DateTime("2018-12-24T09:00:00-07:00");
    EventDateTime start = new EventDateTime()
            .setDateTime(startDateTime)
            .setTimeZone("America/Los_Angeles");
event.setStart(start);

    DateTime endDateTime = new DateTime("2018-12-24T17:00:00-07:00");
    EventDateTime end = new EventDateTime()
            .setDateTime(endDateTime)
            .setTimeZone("America/Los_Angeles");
event.setEnd(end);

    String[] recurrence = new String[] {"RRULE:FREQ=DAILY;COUNT=2"};
event.setRecurrence(Arrays.asList(recurrence));
    int databasesize = allUser.size();
    EventAttendee[] attendees= new EventAttendee[databasesize];
    for (int i = 0; i < databasesize ; i++) {
        attendees[i] = new EventAttendee().setEmail(allUser.get(i));
    }

    event.setAttendees(Arrays.asList(attendees));

    EventReminder[] reminderOverrides = new EventReminder[] {
            new EventReminder().setMethod("email").setMinutes(24 * 60),
            new EventReminder().setMethod("popup").setMinutes(10),
    };
    Event.Reminders reminders = new Event.Reminders()
            .setUseDefault(false)
            .setOverrides(Arrays.asList(reminderOverrides));
event.setReminders(reminders);

    String calendarId = "primary";
    event = service.events().insert(calendarId, event).execute();
System.out.printf("Event created: %s\n", event.getHtmlLink());
}}
