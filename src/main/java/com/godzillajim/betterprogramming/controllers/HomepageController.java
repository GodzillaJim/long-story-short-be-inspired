package com.godzillajim.betterprogramming.controllers;

import com.godzillajim.betterprogramming.domain.payloads.HomepageResponse;
import com.godzillajim.betterprogramming.services.HighlightService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Public Homepage Route")
@RestController
@RequestMapping("/api/v1/public/homepage")
@RequiredArgsConstructor
public class HomepageController {
    private final HighlightService highlightService;
    @GetMapping
    public ResponseEntity<HomepageResponse> getHomepageDetails(){
        HomepageResponse home = highlightService.loadHomePage();
        return ResponseEntity.ok(home);
    }
}
