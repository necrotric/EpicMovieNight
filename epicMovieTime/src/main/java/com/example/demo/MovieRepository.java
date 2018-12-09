package com.example.demo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository extends MongoRepository<Movies, String> {

    public List<Movies> findMovieByTitle(String title);

}
