package tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationResponse {
    private String id;
    private String userId;
    private String activityId;
    private String type;
    private String recommendation;
    private List<String> improvement;
    private List<String> suggestion;
    private List<String> safety;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
