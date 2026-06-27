package tracker.service;

import tracker.Exception.NotFoundException;
import tracker.dto.ActivityDistributionResponse;
import tracker.model.User;
import tracker.repository.ActivityRepository;
import tracker.repository.UserRepository;
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
