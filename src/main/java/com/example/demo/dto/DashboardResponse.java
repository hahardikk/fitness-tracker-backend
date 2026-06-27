package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponse {
    private Long totalActivities;
    private Integer totalCalories;
    private Double averageDuration;
    private String mostPerformedActivity;
    private String latestActivity;
}