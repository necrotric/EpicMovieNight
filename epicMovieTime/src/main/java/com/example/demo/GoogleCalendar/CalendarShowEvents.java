package com.example.demo.GoogleCalendar;

import com.example.demo.Classes.UserOauth;
import com.example.demo.Repository.UserOauthRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
@Service
public class CalendarShowEvents {
    private UserOauthRepository repository;
    @Autowired
    public CalendarShowEvents(UserOauthRepository repository) {
        this.repository = repository;

    }

    public CalendarShowEvents() {

    }


    public ArrayList<List<Event>> showEvents() throws IOException, GeneralSecurityException {
        List<UserOauth> allUsers = repository.findAll();
        ArrayList<List<Event>> allEvents = new ArrayList<>();


        for (UserOauth user : allUsers) {
            System.out.println(user.getEmail());
                    // Use an accessToken previously gotten to call Google's API
        GoogleCredential credential = new GoogleCredential().setAccessToken(user.getAccessToken());
        //        System.out.println("Here is credentials:" + credential);
        Calendar calendar =
                new Calendar.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential)
                        .setApplicationName("Movie Night")
                        .build();

    DateTime now = new DateTime(System.currentTimeMillis());
    Events events = null;
        try

    {
        events = calendar.events().list("primary")
                .setMaxResults(100)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
    } catch(
    IOException e)

    {
        e.printStackTrace();
    }

    List<Event> items = events.getItems();
        if(items.isEmpty())

    {
        System.out.println("No upcoming events found.");
    } else

    {
        System.out.println("Upcoming events");
        for (Event event : items) {
            DateTime start = event.getStart().getDateTime();
            if (start == null) { // If it's an all-day-event - store the date instead
                start = event.getStart().getDate();
            }
            DateTime end = event.getEnd().getDateTime();
            if (end == null) { // If it's an all-day-event - store the date instead
                end = event.getStart().getDate();
            }
            System.out.printf("%s (%s) -> (%s)\n", event.getSummary(), start, end);
        }
    }
        allEvents.add(items);
            System.out.println(allEvents);
        }
        return allEvents;
}
}
