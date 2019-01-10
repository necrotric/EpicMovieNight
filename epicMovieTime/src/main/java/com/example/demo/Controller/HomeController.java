//package com.example.demo.Controller;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@Controller
//public class HomeController {
//    @GetMapping(value ="/")
//    public ResponseEntity<String> index(){
//        return new ResponseEntity<>("index", HttpStatus.OK);
//    }
//
//
//    @GetMapping(value = "/private")
//    public String privateArea(){
//        return "Private area";
//    }
//}
