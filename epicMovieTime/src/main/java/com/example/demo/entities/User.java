//package com.example.demo.entities;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonProperty;
//
//import java.util.List;
//import javax.persistence.*;
//import java.util.List;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;
//
//@Entity
//@ToString
//
//@JsonIgnoreProperties(ignoreUnknown = true)
//public class User {
//    @JsonProperty("Username")
//    private String username;
//    @JsonProperty("Password")
//    private String password;
//    @JsonProperty("Email")
//    private String email;
//    private List<Role> roles;
//    User(){}
//    public User(String username, String password, String email, List<Role> roles) {
//        this.username = username;
//        this.password = password;
//        this.email = email;
//        this.roles = roles;
//
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public List<Role> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(List<Role> roles) {
//        this.roles = roles;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//}
