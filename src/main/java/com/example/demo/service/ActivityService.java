package com.example.demo.service;

import com.example.demo.Exception.NotFoundException;
import com.example.demo.dto.ActivityRequest;
import com.example.demo.dto.ActivityResponse;
import com.example.demo.model.Activity;
import com.example.demo.model.User;
import com.example.demo.repository.ActivityRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;

    public ActivityResponse trackActivity(ActivityRequest request, String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
        Activity activity = Activity.builder()
                .user(user)
                .type(request.getType())
                .additionalMetrics(request.getAdditionalMetrics())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .build();
        Activity savedActivity = activityRepository.save(activity);
        return mapToResponse(savedActivity);
    }

    private ActivityResponse mapToResponse(Activity activity) {
        ActivityResponse response = new ActivityResponse();
        response.setId(activity.getId());
        response.setUserId(activity.getUser().getId());
        response.setType(activity.getType());
        response.setAdditionalMetrics(activity.getAdditionalMetrics());
        response.setDuration(activity.getDuration());
        response.setCaloriesBurned(activity.getCaloriesBurned());
        response.setStartTime(activity.getStartTime());
        response.setCreatedAt(activity.getCreatedAt());
        response.setUpdatedAt(activity.getUpdatedAt());
        return response;
    }

    public List<ActivityResponse> getUserActivities(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User Not Found"));
        List<Activity> activityList = activityRepository.findByUserId(user.getId());
        return activityList.stream().map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void deleteActivity(String activityId) {
        Activity activity = activityRepository.findById(activityId).orElseThrow(() ->
                new NotFoundException("Activity Not Found"));
        activityRepository.delete(activity);
    }
}
