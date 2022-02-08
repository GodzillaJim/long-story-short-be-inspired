package com.godzillajim.betterprogramming.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@Tag(name = "Health Checks")
@RestController
@RequestMapping("/api/v1/public")
public class HomeController {
    @GetMapping
    public RedirectView home(){
        return new RedirectView("/api/v1/public/swagger-ui.html");
    }
}
