package tracker.controller;

import tracker.dto.ActivityRequest;
import tracker.dto.ActivityResponse;
import tracker.service.ActivityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/activities")
public class ActivityController {
    private final ActivityService activityService;
    @PostMapping("/add")
    public ResponseEntity<ActivityResponse> trackActivity(Authentication authentication, @Valid @RequestBody ActivityRequest request){
        return ResponseEntity.ok(activityService.trackActivity(request, authentication.getName()));
    }

    @GetMapping("/get")
    public ResponseEntity<List<ActivityResponse>> retrieveActivity(Authentication authentication){
        String email = authentication.getName();
        return ResponseEntity.ok(activityService.getUserActivities(email));
    }

    @DeleteMapping("/{activityId}")
    public ResponseEntity<String> deleteActivity(@PathVariable String activityId){
        activityService.deleteActivity(activityId);
        return ResponseEntity.ok("Activity deleted successfully");
    }
}
