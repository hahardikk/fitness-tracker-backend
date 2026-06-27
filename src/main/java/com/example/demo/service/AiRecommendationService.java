package com.example.demo.service;

import com.example.demo.dto.AiRecommendationResponse;
import com.example.demo.dto.GroqRequest;
import com.example.demo.dto.GroqResponse;
import com.example.demo.dto.Message;
import com.example.demo.model.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AiRecommendationService {
    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    @Value("${groq.api.key}")
    private String apiKey;

    @Value("${groq.api.url}")
    private String url;

    public AiRecommendationResponse generateRecommendation(Activity activity) {
        String metricsJson = objectMapper.writeValueAsString(activity.getAdditionalMetrics());
        String prompt = """
                You are an expert fitness coach.
                
                Analyze the workout and return ONLY valid JSON.
                
                IMPORTANT:
                - Return ONLY JSON.
                - No markdown.
                - No explanation.
                - No code block.
                - improvement MUST be a JSON array of strings.
                - suggestion MUST be a JSON array of strings.
                - safety MUST be a JSON array of strings.
                
                Return EXACTLY in this format:
                
                {
                  "recommendation": "string",
                  "improvement": [
                    "string",
                    "string"
                  ],
                  "suggestion": [
                    "string",
                    "string"
                  ],
                  "safety": [
                    "string",
                    "string"
                  ]
                }
                
                Workout:
                Type: %s
                Duration: %d
                Calories Burned: %d
                Additional Metrics: %s
                """.formatted(
                activity.getType(),
                activity.getDuration(),
                activity.getCaloriesBurned(),
                metricsJson
        );
        GroqRequest request = new GroqRequest(
                "llama-3.3-70b-versatile",
                List.of(
                        new Message(
                                "user",
                                prompt
                        )
                )
        );

        GroqResponse response =
                restClient.post()
                        .uri(url)
                        .header("Authorization", "Bearer " + apiKey)
                        .header("Content-Type", "application/json")
                        .body(request)
                        .retrieve()
                        .body(GroqResponse.class);

        String content = response.getChoices()
                .get(0)
                .getMessage()
                .getContent();

        log.info("AI Response: {}", content);

        try {
            AiRecommendationResponse aiResponse = objectMapper.readValue(content, AiRecommendationResponse.class);
            if (aiResponse.getRecommendation() == null || aiResponse.getRecommendation().isBlank()) {
                throw new RuntimeException("AI response missing Recommendation");
            }
            if (aiResponse.getImprovement() == null) {
                throw new RuntimeException("AI response is missing Improvements");
            }
            if (aiResponse.getSuggestion() == null) {
                throw new RuntimeException("AI response is missing Suggestions");
            }
            if (aiResponse.getSafety() == null) {
                throw new RuntimeException("AI response is missing Safety");
            }
            return aiResponse;
        } catch (Exception e) {
            log.info("Invalid AI Response", e);
            throw e;
        }
    }
}