package com.SettleLater.Backend.Common.Test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/public")
    public String publicTest(){
        return "This is Public Endpoint";
    }

    @GetMapping("/private")
    public String privateTest(){
        return "This is private Endpoint";
    }
}
