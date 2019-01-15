package com.example.demo.GoogleCalendar;

import com.example.demo.Classes.UserOauth;
import com.example.demo.Repository.UserOauthRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

@Service
public class CalendarCreateEvents {
    private UserOauthRepository repository;

    @Autowired
    public CalendarCreateEvents(UserOauthRepository repository) {
        this.repository = repository;

    }

    public CalendarCreateEvents() {
    }

    public void gogo(String startTime, String endTime, String summary) throws IOException, GeneralSecurityException, IOException {
        List<UserOauth> epicAccessToken = repository.findUserOauthByEmail("epicmovienight12@gmail.com");
        System.out.println(epicAccessToken.get(0).getAccessToken());

        GoogleCredential credential = new GoogleCredential().setAccessToken(epicAccessToken.get(0).getAccessToken());

        Calendar calendar =
                new Calendar.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential)
                        .setApplicationName("Movie Night")
                        .build();

        // System.out.println(userService.getAllUsers().get(0).getEmail()+ " IT IS IN CREATE EVENTS");
        Event event = new Event()
                .setSummary(summary)
                .setLocation("Center of the universe")
                .setDescription("A chance to be in the middle ... litterly");
        //"2018-12-20T09:00:00-07:00"
        DateTime startDateTime = new DateTime(startTime + "+01:00");
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("Europe/Zurich");
        event.setStart(start);

        DateTime endDateTime = new DateTime(endTime + "+01:00");
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("Europe/Zurich");
        event.setEnd(end);

        String[] recurrence = new String[]{"RRULE:FREQ=DAILY;COUNT=1"};
        event.setRecurrence(Arrays.asList(recurrence));
        List<UserOauth> users = repository.findAll();
        users.size();
        EventAttendee[] attendees = new EventAttendee[users.size()];
        for (int i = 0; i < users.size(); i++) {
            attendees[i] = new EventAttendee().setEmail(users.get(i).getEmail());
        }

        event.setAttendees(Arrays.asList(attendees));

        EventReminder[] reminderOverrides = new EventReminder[]{
                new EventReminder().setMethod("email").setMinutes(24 * 60),
                new EventReminder().setMethod("popup").setMinutes(10),
        };
        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);

        String calendarId = "primary";
        event = calendar.events().insert(calendarId, event).execute();
        System.out.printf("Event created: %s\n", event.getHtmlLink());
    }
}

