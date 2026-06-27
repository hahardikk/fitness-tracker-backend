package tracker.service;

import tracker.Exception.NotFoundException;
import tracker.dto.DashboardResponse;
import tracker.model.Activity;
import tracker.model.User;
import tracker.repository.ActivityRepository;
import tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;

    public DashboardResponse getDashboardStats(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not Found"));

        List<Activity> activities = activityRepository.findByUserId(user.getId());

        Long totalActivities = (long) activities.size();

        Integer totalCalories = activities.stream()
                .mapToInt(Activity::getCaloriesBurned)
                .sum();

        Double averageDuration = Math.round(activities.stream()
                .mapToInt(Activity::getDuration)
                .average()
                .orElse(0.0) * 100.0) / 100.0;

        String mostPerformedActivity = activities.stream()
                .collect(Collectors.groupingBy(activity -> activity.getType().name(), Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
        ;

        String latestActivity = activities.stream()
                .max(Comparator.comparing(Activity::getStartTime))
                .map(activity -> activity.getType().name())
                .orElse("N/A");

        return new DashboardResponse(
                totalActivities,
                totalCalories,
                averageDuration,
                mostPerformedActivity,
                latestActivity
        );
    }
}
