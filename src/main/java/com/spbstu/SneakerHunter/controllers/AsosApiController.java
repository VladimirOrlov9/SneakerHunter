package com.spbstu.SneakerHunter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class AsosApiController {
    @Autowired
    private RestTemplate restTemplate;
    private static String url = "";

}
