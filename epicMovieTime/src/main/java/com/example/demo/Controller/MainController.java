package com.example.demo.Controller;

import com.example.demo.GoogleCalendar.CalendarCreateEvents;
import com.example.demo.GoogleCalendar.CalendarShowEvents;

import com.example.demo.Repository.MovieRepository;
import com.example.demo.Classes.Movies;
import com.example.demo.Classes.Search;
import com.example.demo.Repository.UserOauthRepository;

import com.example.demo.services.UserService;

import com.google.api.services.calendar.model.Event;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.GeneralSecurityException;

import java.util.*;



@RestController
public class MainController {
    private CalendarShowEvents calenderEvents;
    private CalendarCreateEvents calenderCreate;
    private final MovieRepository repository;
    private UserOauthRepository userOauthRepository;
    private final UserService userService;
    private CalendarController calendarController;
    private String findmovie = "";


    @Autowired
    public MainController(CalendarController calendarController, MovieRepository repository, CalendarShowEvents calenderEvents, CalendarCreateEvents calenderCreate, UserService userService, UserOauthRepository userOauthRepository) {
        this.repository = repository;
        this.userService = userService;
        this.calenderEvents = calenderEvents;
        this.calenderCreate = calenderCreate;
        this.userOauthRepository = userOauthRepository;
        this.calendarController = calendarController;
    }

    @GetMapping("/title")
    public ResponseEntity<ArrayList<Movies>> search(@RequestParam String title) {

        ArrayList<Movies> movies = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        if (title.length() > 1) {
            List<Search> newSearch = Collections.singletonList(restTemplate.getForObject(
                    "http://www.omdbapi.com/?s=" + title + "&apikey=ea1db5cc", Search.class));

            for (Search s : newSearch) {
                for (int i = 0; i < s.getSearch().size(); i++) {
                    Movies movie = new Movies();
                    movies.add(movie);
                    movies.get(i).setTitle(s.getSearch().get(i).getTitle());
                    movies.get(i).setImdbID(s.getSearch().get(i).getImdbID());
                    movies.get(i).setYear(s.getSearch().get(i).getYear());

                }
            }
        }
        if (movies.size() == 0) {
            return new ResponseEntity<>(movies, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @GetMapping("/title/movie")
    public ResponseEntity<Movies> movies(@RequestParam String imdb) {
        RestTemplate restTemplate = new RestTemplate();
        Movies getFromDB = repository.findMoviesByimdbID(imdb);
        List<Movies> moviesList = repository.findAll();
        moviesList.size();
        if (getFromDB == null) {
            Movies movies = restTemplate.getForObject(
                    "http://www.omdbapi.com/?i=" + imdb + "&apikey=ea1db5cc", Movies.class);

            repository.save(movies);
            if (movies == null) {
                return new ResponseEntity<>(movies, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(movies, HttpStatus.OK);
        }

        return new ResponseEntity<>(getFromDB, HttpStatus.OK);
    }

    @GetMapping("/main.html/giveinfo")
    public ResponseEntity<List<String>> events() throws IOException, GeneralSecurityException {


        ArrayList<List<Event>> bookedEvents = calenderEvents.showBookedEvents();
        List<String> filterEvent = new ArrayList<>();

        for (List<Event> e : bookedEvents) {
            for (Event ev : e) {
                String sumAndDate = "";
                if (ev.getStart().getDate() == null) {
                    sumAndDate = "" + ev.getSummary() + "  " + ev.getStart().getDateTime();
                }
                if (ev.getStart().getDateTime() == null) {
                    sumAndDate = "" + ev.getSummary() + "  " + ev.getStart().getDate();
                }

                if (!filterEvent.contains(sumAndDate)) {
                    filterEvent.add(sumAndDate);
                }
            }

        }

        if (filterEvent.isEmpty()) {

            return new ResponseEntity<>(filterEvent, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(filterEvent, HttpStatus.OK);
    }

    @GetMapping("/main.html/suggestedDates")
    public ResponseEntity<ArrayList<List<DateTime>>> availableDates() throws IOException, GeneralSecurityException {
        ArrayList<List<DateTime>> bookedTime = new ArrayList<>();
        DateTime now = new DateTime(System.currentTimeMillis());
        ArrayList<List<Event>> allEvent = calenderEvents.showEvents();
        for (List<Event> list : allEvent) {
            for (Event event : list) {
                if (event.getStart().getDateTime() != null) {
                    List<DateTime> startAndEnd = new ArrayList<>();
                    long start = event.getStart().getDateTime().getValue();
                    long end = event.getEnd().getDateTime().getValue();
                    DateTime jodaStart = new DateTime(start);
                    DateTime jodaEnd = new DateTime(end);
                    startAndEnd.add(jodaStart);
                    startAndEnd.add(jodaEnd);
                    bookedTime.add(startAndEnd);
                }
                if (event.getStart().getDate() != null) {
                    List<DateTime> startAndEndDate = new ArrayList<>();
                    long startDate = event.getStart().getDate().getValue();
                    long endDate = event.getEnd().getDate().getValue();
                    DateTime jodaStartDate = new DateTime(startDate);
                    DateTime jodaEndDate = new DateTime(endDate);
                    startAndEndDate.add(jodaStartDate);
                    startAndEndDate.add(jodaEndDate);
                    bookedTime.add(startAndEndDate);
                }
            }
        }
        ArrayList<List<DateTime>> hourPerDay = new ArrayList<>();
        List<DateTime> availableTimes = new ArrayList<>();
        int twentyDays = 480;
        for (int i = 0; i < twentyDays; i++) {
            availableTimes = new ArrayList<>();
            int count = 0;
            for (int j = 0; j < bookedTime.size(); j++) {
                if (!now.isBefore(bookedTime.get(j).get(0).minusHours(1)) && !now.isAfter(bookedTime.get(j).get(1))) {

                    count++;
                }
            }
            now = now.plusHours(1);
            if (count == 0) {
                availableTimes.add(now);
                hourPerDay.add(availableTimes);
            }
            count = 0;
        }
        return new ResponseEntity<>(hourPerDay, HttpStatus.OK);
    }


    @GetMapping("/calendar/book")
    public ResponseEntity<String> string(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String summary) throws IOException, GeneralSecurityException {
        if (startDate.equals(":00") || endDate.equals(":00")) {
            return new ResponseEntity<>("You need to select a YY-MM-DD TT-MM", HttpStatus.BAD_REQUEST);
        }
        if (summary == "" || summary == null) {
            return new ResponseEntity<>("You need to select a movie", HttpStatus.BAD_REQUEST);
        }
        calenderCreate.gogo(startDate, endDate, summary);
        calenderEvents.showEvents();

        return new ResponseEntity<>("Thank you for Booking the movie: " + summary + ". Emails have been sent to registered users for the start:" + startDate + ", and endng at:" + endDate, HttpStatus.OK);
    }
}
