package com.example.demo.services;


import com.example.demo.Controller.CalendarQuickstart;
import com.example.demo.Controller.MainController;
import com.example.demo.Repository.UserRepository;
import com.example.demo.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
//    CalendarQuickstart quickstart;
//    List<User> allUser;
    @Autowired
    public UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repo.save(user);
    }
    public List<User> getAllUsers(){
        return repo.findAll();
    }
}
