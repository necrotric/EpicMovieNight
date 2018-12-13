package com.example.demo.Controller;

import com.example.demo.Repository.MovieRepository;
import com.example.demo.Classes.Movies;
import com.example.demo.Classes.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
        public List<Search> search(@RequestParam String title ) {
            RestTemplate restTemplate = new RestTemplate();
       List<Search> search = Collections.singletonList(restTemplate.getForObject(
             "http://www.omdbapi.com/?s="+ title + "&apikey=ea1db5cc", Search.class));

            return search ;
        }
        @GetMapping("/title/movie")
        public Movies movies(@RequestParam String imdb){
            RestTemplate restTemplate = new RestTemplate();
         getFromDB = repository.findMoviesByimdbID(imdb);
            System.out.println(getFromDB);
            if(getFromDB == null) {
                movies = restTemplate.getForObject(
                        "http://www.omdbapi.com/?i=" + imdb + "&apikey=ea1db5cc", Movies.class);

                repository.save(movies);
                return movies;
            }
                return getFromDB;
        }
}
