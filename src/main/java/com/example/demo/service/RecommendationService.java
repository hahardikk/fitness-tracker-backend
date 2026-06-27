package com.example.demo.service;

import com.example.demo.Exception.AccessDeniedException;
import com.example.demo.Exception.NotFoundException;
import com.example.demo.dto.AiRecommendationResponse;
import com.example.demo.dto.RecommendationResponse;
import com.example.demo.model.Activity;
import com.example.demo.model.Recommendation;
import com.example.demo.model.User;
import com.example.demo.repository.ActivityRepository;
import com.example.demo.repository.RecommendationRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class RecommendationService {
    private final RecommendationRepository recommendationRepository;
    private final AiRecommendationService aiRecommendationService;
    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;

    public RecommendationResponse generateAiRecommendation(
            Activity activity) {
        try {
            AiRecommendationResponse aiResponse =
                    aiRecommendationService.generateRecommendation(activity);

            Recommendation recommendation =
                    Recommendation.builder()
                            .user(activity.getUser())
                            .activity(activity)
                            .type(activity.getType().name())
                            .recommendation(aiResponse.getRecommendation())
                            .improvement(aiResponse.getImprovement())
                            .suggestion(aiResponse.getSuggestion())
                            .safety(aiResponse.getSafety())
                            .build();
            Recommendation saved = recommendationRepository.save(recommendation);
            log.info(
                    "Recommendation generated successfully for activity {}",
                    activity.getId()
            );
            return mapToResponse(saved);
        } catch (Exception e) {
            log.error("Failed to generate recommendation for activity {}", activity.getId(), e);
            throw new RuntimeException(e);
        }
    }

    private RecommendationResponse mapToResponse(Recommendation recommendation) {
        RecommendationResponse response = new RecommendationResponse();
        response.setId(recommendation.getId());
        response.setUserId(recommendation.getUser().getId());
        response.setActivityId(recommendation.getActivity().getId());
        response.setType(recommendation.getType());
        response.setRecommendation(recommendation.getRecommendation());
        response.setImprovement(recommendation.getImprovement());
        response.setSuggestion(recommendation.getSuggestion());
        response.setSafety(recommendation.getSafety());
        response.setCreatedAt(recommendation.getCreatedAt());
        response.setUpdatedAt((recommendation.getUpdatedAt()));
        return response;
    }

    public RecommendationResponse generateRecommendation(String activityId) {
        Optional<Recommendation> existingRecommendation = recommendationRepository.findByActivityId(activityId);
        if (existingRecommendation.isPresent()) {
            log.info("Recommendation already exists for activity: {}", activityId);
            return mapToResponse(existingRecommendation.get());
        }
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new NotFoundException("Activity not found"));

        return generateAiRecommendation(activity);
    }

    public List<RecommendationResponse> getUserRecommendation(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("user not found"));
        List<Recommendation> recommendations = recommendationRepository.findByUserId(user.getId());
        return recommendations.stream().map(this::mapToResponse).toList();
    }

    public RecommendationResponse getActivityRecommendation(String activityId, String email) throws AccessDeniedException {
        Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new NotFoundException("Activity not Found"));

        if (!activity.getUser().getEmail().equals(email)) {
            throw new AccessDeniedException("You are not authorized to access this activity");
        }
        Recommendation recommendation = recommendationRepository.findByActivityId(activityId)
                .orElseThrow(() -> new NotFoundException("Recommendation not found"));
        return mapToResponse(recommendation);
    }
}
