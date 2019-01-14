package com.example.demo;

import com.example.demo.Controller.CalendarController;
import com.example.demo.Controller.MainController;
import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DemoApplication {
    private static CalendarController controller;

    @Autowired
    public DemoApplication(CalendarController controller){
        this.controller = controller;
    }
    @Bean
    public CommandLineRunner setupDefaultUser(UserService service) {
        return args -> {
            service.save(new User(
                    "user2", //username
                    "user2", //password
                    "epicmovienight12@gmail.com",
                    Arrays.asList(new Role("USER"), new Role("ACTUATOR")),//roles
                    true//Active
            ));
        };
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        asd();
        Runnable runnable = new Runnable() {
            public void run() {
                // task to run goes here
                asd();
              	        System.out.println("Hello !!");
                }
        };
        ScheduledExecutorService service = Executors
                .newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable, 0, 59, TimeUnit.MINUTES);
    }

    public static void asd(){
        controller.updateAccessToken();
    }
}
