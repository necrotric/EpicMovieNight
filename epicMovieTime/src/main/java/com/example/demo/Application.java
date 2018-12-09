package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {
    @Autowired
    private MovieRepository repository;
    private String findmovie ="";
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {
            Movies movie = restTemplate.getForObject(
                    "http://www.omdbapi.com/?i=tt3896198&apikey=ea1db5cc", Movies.class);
            log.info(movie.toString());
            for (Movies movies : repository.findMovieByTitle("Guardians of the Galaxy Vol. 2")) {
                findmovie= movie.getTitle();
                System.out.println(findmovie);
          System.out.println(movies + "Found it");

         }
         if(findmovie == ""){
             System.out.println("got here");
             repository.save(movie);
             findmovie= "";
         }
            //repository.save(movie);
        };
    }


//    @Override
//    public void run(String... args) throws Exception {
//
//        repository.deleteAll();
//
//        // save a couple of customers
//        repository.save(new Movies("Alice", "Smith"));
//        repository.save(new Customer("Bob", "Smith"));

        // fetch all customers
//        System.out.println("Customers found with findAll():");
//        System.out.println("-------------------------------");
//        for (Customer customer : repository.findAll()) {
//            System.out.println(customer);
//        }
//        System.out.println();
//
//        // fetch an individual customer
//        System.out.println("Customer found with findByFirstName('Alice'):");
//        System.out.println("--------------------------------");
//        System.out.println(repository.findByFirstName("Alice"));
//
//        System.out.println("Customers found with findByLastName('Smith'):");
//        System.out.println("--------------------------------");
//        for (Customer customer : repository.findByLastName("Smith")) {
//            System.out.println(customer);
//        }

//    }
}