package com.godzillajim.betterprogramming.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api/v1/public")
public class HomeController {
    @GetMapping
    public RedirectView home(){
        return new RedirectView("/api/v1/public/swagger-ui.html");
    }
}
