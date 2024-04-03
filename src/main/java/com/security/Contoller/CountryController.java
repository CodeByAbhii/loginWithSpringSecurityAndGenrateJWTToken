package com.security.Contoller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/countries")
public class CountryController {


    @PostMapping("/addCountry")
    public String getCountry() {
        return "done";
    }
}
