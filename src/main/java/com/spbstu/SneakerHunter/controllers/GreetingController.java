package com.spbstu.SneakerHunter.controllers;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class GreetingController {
    @GetMapping
    public String list(){
        return "Hello daun";
    }
}
