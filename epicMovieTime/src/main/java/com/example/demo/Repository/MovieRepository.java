package com.example.demo.Repository;

import java.util.List;

import com.example.demo.Classes.Movies;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository extends MongoRepository<Movies, String> {

    public List<Movies> findMovieByTitle(String title);
    public Movies findMoviesByimdbID(String imdbID);

}
