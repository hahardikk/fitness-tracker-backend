package tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroqResponse {
    private List<Choice> choices;

    @Data
    public static class Choice{
        private Message message;
    }
}
