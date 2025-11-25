package com.example.day1.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/demo")
public class Home {
    @GetMapping("/home")
    public String home(){
        return "this is home for testing";
    }

    @GetMapping("/{name}")
    public String byName(@PathVariable String name){
        return "Your name is "+name;
    }

    @GetMapping("/details")
    public String getDetails(
            @RequestParam String name,
            @RequestParam int age,
            @RequestParam(required = false) String city
    ) {
        return "Name: " + name + ", Age: " + age + ", City: " + city;
    }

    @PutMapping("/save")
    public String saveUserDetails(@RequestBody Map<String, Object> data) {
        return "Received data: " + data;
    }


}
