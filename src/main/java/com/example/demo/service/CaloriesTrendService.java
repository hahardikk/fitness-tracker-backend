package com.example.demo.service;

import com.example.demo.Exception.NotFoundException;
import com.example.demo.dto.CaloriesTrendResponse;
import com.example.demo.model.Activity;
import com.example.demo.model.User;
import com.example.demo.repository.ActivityRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CaloriesTrendService {
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;

    public List<CaloriesTrendResponse> getCaloriesTrend(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));

        List<Activity> activities = activityRepository.findByUserIdByStartTimeDesc(user.getId());

        Map<String, Integer> caloriesByDate = activities.stream()
                .collect(Collectors.groupingBy(
                        activity -> activity.getStartTime().toLocalDate().toString(),
                        Collectors.summingInt(Activity::getCaloriesBurned)
                ));

        return caloriesByDate.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .map(entry -> new CaloriesTrendResponse(entry.getKey(), entry.getValue())).toList();
    }
}
