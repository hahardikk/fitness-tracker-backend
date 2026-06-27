package com.example.demo.controller;

import com.example.demo.dto.CaloriesTrendResponse;
import com.example.demo.service.CaloriesTrendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashboard")
public class CaloriesTrendController {
    private final CaloriesTrendService caloriesTrendService;

    @GetMapping("/calories-trend")
    public ResponseEntity<List<CaloriesTrendResponse>> getCaloriesTrend(Authentication authentication) {
        return ResponseEntity.ok(caloriesTrendService.getCaloriesTrend(authentication.getName()));
    }
}
