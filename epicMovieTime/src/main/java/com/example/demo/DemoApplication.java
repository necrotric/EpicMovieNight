package com.example.demo;

import com.example.demo.Controller.MainController;
import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import com.example.demo.services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DemoApplication {
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

    }
}
