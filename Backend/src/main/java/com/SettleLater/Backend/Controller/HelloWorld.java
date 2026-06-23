package com.SettleLater.Backend.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloWorld {
    @GetMapping("/helloWorld")
    public String helloWorld(){
        return "helloworld";
    }
}
