package tracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiRecommendationResponse {
    @NotBlank
    private String recommendation;
    @NotNull
    private List<String> improvement;
    @NotNull
    private List<String> suggestion;
    @NotNull
    private List<String> safety;
}
