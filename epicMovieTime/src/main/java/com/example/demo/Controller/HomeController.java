package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {
    @GetMapping(value ="../")
    public String index(){
        return "did it work";
    }
//    @GetMapping(value="/private")
//    public String privateArea(){
//        return "Cant see me without access";
//    }
//@RequestMapping("../")
//public String index() {
//    return "Spring Boot Example";
//}
}
