package tracker.controller;

import tracker.dto.ActivityDistributionResponse;
import tracker.service.ActivityDistributionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ActivityDistributionController {
    private final ActivityDistributionService activityDistributionService;

    @GetMapping("/distribution")
    public ResponseEntity<List<ActivityDistributionResponse>> getActivityDistribution(Authentication authentication) {
        return ResponseEntity.ok(activityDistributionService.getActivityDistribution(authentication.getName()));
    }
}
