package com.example.demo.service;

import com.example.demo.Exception.NotFoundException;
import com.example.demo.dto.ActivityDistributionResponse;
import com.example.demo.model.User;
import com.example.demo.repository.ActivityRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityDistributionService {
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;

    public List<ActivityDistributionResponse> getActivityDistribution(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found: "+ email));
        return activityRepository.getActivityDistribution(user.getId()).stream()
                .map(row -> new ActivityDistributionResponse(
                        row[0].toString(), ((Long) row[1])
                )).toList();

    }
}
