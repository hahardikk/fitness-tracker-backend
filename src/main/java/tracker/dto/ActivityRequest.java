package tracker.dto;

import tracker.model.ActivityType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityRequest {
    @NotNull(message = "Provide Activity Type")
    private ActivityType type;
    @Positive(message = "Duration must be greater than 0")
    private Integer duration;
    @Positive(message = "Calories burned must be greater than 0")
    private Integer caloriesBurned;
    private Map<String, Object> additionalMetrics;
    private LocalDateTime startTime;
}
