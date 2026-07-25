package com.lapakbaju.lapakbajustore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {
    @GetMapping("/")
    String base(){
        return "base";
    }
}
