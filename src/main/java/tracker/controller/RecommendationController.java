package tracker.controller;

import tracker.exception.AccessDeniedException;
import tracker.dto.RecommendationResponse;
import tracker.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommendation")
public class RecommendationController {
    private final RecommendationService recommendationService;

    @PostMapping("/generate/{activityId}")
    public ResponseEntity<RecommendationResponse> generateRecommendation(@PathVariable String activityId){
        return ResponseEntity.ok(recommendationService.generateRecommendation(activityId));
    }

    @GetMapping("/user")
    public ResponseEntity<List<RecommendationResponse>> getUserRecommendation(Authentication authentication) {
        String email = authentication.getName();
        List<RecommendationResponse> recommendation = recommendationService.getUserRecommendation(email);
        return ResponseEntity.ok(recommendation);
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<RecommendationResponse> getActivityRecommendation(@PathVariable String activityId, Authentication authentication) throws AccessDeniedException {
        RecommendationResponse recommendation = recommendationService.getActivityRecommendation(activityId, authentication.getName());
        return ResponseEntity.ok(recommendation);
    }
}
