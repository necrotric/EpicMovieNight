package com.example.demo.Controller;

import com.example.demo.Repository.MovieRepository;
import com.example.demo.Classes.Movies;
import com.example.demo.Classes.Search;
import com.example.demo.entities.User;
import com.example.demo.services.UserService;
import com.google.api.services.calendar.model.Event;
import org.apache.tomcat.jni.Local;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static java.lang.Integer.parseInt;

@RestController
public class MainController {

    private final MovieRepository repository;
    private final
    UserService userService;
   // private final NeweventTest calendar;
    private String findmovie = "";
    //private final CalendarQuickstart quick;

    @Autowired
    public MainController(MovieRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
//        NeweventTest calendar
//        CalendarQuickstart quick
//        this.quick = quick;
//        this.calendar = calendar;
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
        List<Movies> asaa = repository.findAll();
        asaa.size();
        // System.out.println(asaa.size() + " amount of movies inside" );
        // System.out.println("Inside API ;)"+ getFromDB);
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

//    @GetMapping("/main.html/giveinfo")
//    public ResponseEntity<List<String>> events() throws IOException, GeneralSecurityException {
//
//
//        ArrayList<List<Event>> asd = quick.showEvents();
//        System.out.println(asd.size());
//        System.out.println("After this ----------------------------------- \n\n\n");
//        // System.out.println(asd.get(0).get(0).getSummary());
//        List<String> filterEvent = new ArrayList<>();
//
//        for (List<Event> e : asd) {
//            for (Event ev : e) {
//                String sumAndDate = "";
//                if (ev.getStart().getDate() == null) {
//                    sumAndDate = "" + ev.getSummary() + "  " + ev.getStart().getDateTime();
//                }
//                if (ev.getStart().getDateTime() == null) {
//                    sumAndDate = "" + ev.getSummary() + "  " + ev.getStart().getDate();
//                }
////                System.out.println(sumAndDate);
//                if (!filterEvent.contains(sumAndDate)) {
//                    filterEvent.add(sumAndDate);
//                }
//            }
//
//        }
//        System.out.println(filterEvent.size());
//        if (filterEvent.isEmpty()) {
//            System.out.println("Do we get into filterevent == null");
//            return new ResponseEntity<>(filterEvent, HttpStatus.NO_CONTENT);
//        }
////        for (String s: filterEvent) {
////            System.out.println(s);
////        }
//        return new ResponseEntity<>(filterEvent, HttpStatus.OK);
//    }

//    @GetMapping("/main.html/suggestedDates")
//    public ResponseEntity<ArrayList<List<DateTime>>> availableDates() throws IOException, GeneralSecurityException {
//        ArrayList<List<DateTime>> bookedTime = new ArrayList<>();
//        DateTime now = new DateTime(System.currentTimeMillis());
//        ArrayList<List<Event>> allEvent = quick.showEvents();
//        for (List<Event> list : allEvent) {
//            for (Event event : list) {
//                if(event.getStart().getDateTime() != null) {
//                    List<DateTime> startAndEnd= new ArrayList<>();
//                    long start = event.getStart().getDateTime().getValue();
//                    long end = event.getEnd().getDateTime().getValue();
//                    DateTime jodaStart = new DateTime(start);
//                    DateTime jodaEnd = new DateTime(end);
//                    startAndEnd.add(jodaStart);
//                    startAndEnd.add(jodaEnd);
//                    bookedTime.add(startAndEnd);
//                } if(event.getStart().getDate() != null){
//                    List<DateTime> startAndEndDate= new ArrayList<>();
//                    long startDate = event.getStart().getDate().getValue();
//                    long endDate = event.getEnd().getDate().getValue();
//                    DateTime jodaStartDate = new DateTime(startDate);
//                    DateTime jodaEndDate = new DateTime(endDate);
//                    startAndEndDate.add(jodaStartDate);
//                    startAndEndDate.add(jodaEndDate);
//                    bookedTime.add(startAndEndDate);
//                }
//            }
//        }
//        ArrayList<List<DateTime>> hourPerDay= new ArrayList<>();
//        List<DateTime> availableTimes = new ArrayList<>();
//        int twentyDays = 480;
//        for (int i = 0; i < twentyDays ; i++) {
//            availableTimes = new ArrayList<>();
//            int count =0;
//            for (int j = 0; j < bookedTime.size(); j++) {
//                if (!now.isBefore(bookedTime.get(j).get(0).minusHours(1)) && !now.isAfter(bookedTime.get(j).get(1))) {
//                   // System.out.println(now);
//                    count++;
//                }
//            }
//            now = now.plusHours(1);
//           // System.out.println(count);
//            if(count == 0){
//                System.out.println("Day: "+now.getDayOfMonth() + ". Hour of day: " + now.getHourOfDay());
//                availableTimes.add(now);
//                hourPerDay.add(availableTimes);
//            }
//            count=0;
//        }
//        return new ResponseEntity<>(hourPerDay, HttpStatus.OK);
//    }


//    @GetMapping("/calendar/book")
//    public ResponseEntity<String> string(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String summary) throws IOException, GeneralSecurityException {
//        if (startDate.equals(":00") || endDate.equals(":00")) {
//            return new ResponseEntity<>("You need to select a YY-MM-DD TT-MM", HttpStatus.BAD_REQUEST);
//        }
//        if (summary == "" || summary == null) {
//            return new ResponseEntity<>("You need to select a movie", HttpStatus.BAD_REQUEST);
//        }
//        calendar.gogo(startDate, endDate, summary);
//        quick.showEvents();
//        // ArrayList<List<Event>> asd = quick.showEvents();
//
//        return new ResponseEntity<>("Thank you for Booking the movie: " + summary + ". Emails have been sent to registered users for the start:" + startDate + ", and endng at:" + endDate, HttpStatus.OK);
//    }
}
