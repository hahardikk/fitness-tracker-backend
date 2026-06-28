package tracker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/heatlh")
    public String home() {
        return "Fitness Tracker Backend API is running!";
    }
}
