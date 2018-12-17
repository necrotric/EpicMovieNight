package com.example.demo.Controller;

import com.example.demo.Repository.MovieRepository;
import com.example.demo.Classes.Movies;
import com.example.demo.Classes.Search;
import com.google.api.services.calendar.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@RestController
public class MainController {
    @Autowired
    private MovieRepository repository;
    private String findmovie = "";
    private Movies getFromDB;
    private Movies movies;


    @GetMapping("/title")
    public ArrayList<Movies> search(@RequestParam String title ) {

        ArrayList<Movies> movies = new ArrayList<>() ;
        RestTemplate restTemplate = new RestTemplate();
        if(title.length()>1) {
            List<Search> newSearch = Collections.singletonList(restTemplate.getForObject(
                    "http://www.omdbapi.com/?s=" + title + "&apikey=ea1db5cc", Search.class));

        for (Search s: newSearch) {
            for(int i = 0; i<s.getSearch().size(); i++){
                Movies movie = new Movies();
                movies.add(movie);
                movies.get(i).setTitle(s.getSearch().get(i).getTitle());
                movies.get(i).setImdbID(s.getSearch().get(i).getImdbID());
                movies.get(i).setYear(s.getSearch().get(i).getYear());

            }}}
        return movies ;
    }
    @GetMapping("/title/movie")
    public Movies movies(@RequestParam String imdb){
        RestTemplate restTemplate = new RestTemplate();
        getFromDB = repository.findMoviesByimdbID(imdb);
        List<Movies> asaa = repository.findAll();
        asaa.size();
        System.out.println(asaa.size() + " amount of movies inside" );
        System.out.println("Inside API ;)"+ getFromDB);
        if(getFromDB == null) {
            movies = restTemplate.getForObject(
                    "http://www.omdbapi.com/?i=" + imdb + "&apikey=ea1db5cc", Movies.class);

            repository.save(movies);
            return movies;
        }
        return getFromDB;
    }
    @GetMapping("/main.html/giveinfo")
    public List<String> events() throws IOException, GeneralSecurityException {
        CalendarQuickstart quickEvents = new CalendarQuickstart();
        quickEvents.showEvents();
        ArrayList<List<Event>> asd = quickEvents.showEvents();
        System.out.println(asd.size());
        System.out.println("After this ----------------------------------- \n\n\n");
       // System.out.println(asd.get(0).get(0).getSummary());
        List<String> filterEvent = new ArrayList<>();

        for (List<Event> e: asd) {
            for (Event ev:e) {
                String sumAndDate="";
                if(ev.getStart().getDate()==null){
                   sumAndDate = ""+ev.getSummary() + "  "+ ev.getStart().getDateTime();
                }
                if(ev.getStart().getDateTime()==null) {
                    sumAndDate = "" + ev.getSummary() + "  " + ev.getStart().getDate();
                }
//                System.out.println(sumAndDate);
               if(!filterEvent.contains(sumAndDate)){
                   filterEvent.add(sumAndDate);
               }
            }

        }
        for (String s: filterEvent) {
            System.out.println(s);
        }
        return filterEvent;
    }

    @GetMapping("/calendar/book")
    public String string(@RequestParam String startDate,@RequestParam String endDate,@RequestParam String summary) throws IOException, GeneralSecurityException {
        NeweventTest calendar = new NeweventTest();
        calendar.gogo(startDate,endDate,summary);
        CalendarQuickstart quickEvents = new CalendarQuickstart();
        quickEvents.showEvents();
        ArrayList<List<Event>> asd = quickEvents.showEvents();

        return "Thank you for Booking. Emails have been sent to registered users";
    }
}
