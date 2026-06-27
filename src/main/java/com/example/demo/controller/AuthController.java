package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.model.User;
import com.example.demo.security.JwtUtils;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest request){
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            User user = userService.authenticate(loginRequest);
        String token = jwtUtils.generateToken(user.getEmail(), user.getRole().name());
            return ResponseEntity.ok(new LoginResponse(
                    token, userService.mapToResponse(user)
            ));
        }
        catch (AuthenticationException e) {
            e.printStackTrace();
            return ResponseEntity.status(401).build();
        }
    }
}
