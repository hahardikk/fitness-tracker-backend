package com.example.demo.service;

import com.example.demo.Exception.InvalidCredentialException;
import com.example.demo.Exception.UserAlreadyExistsException;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.model.User;
import com.example.demo.model.UserRole;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register(UserRequest request) {
            if(userRepository.existsByEmail(request.getEmail())){
                throw new UserAlreadyExistsException("User Already Exits");
            }
            UserRole role = request.getRole() != null ? request.getRole() : UserRole.USER;
            User user = User.builder()
                    .email(request.getEmail())
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .role(role)
                    .password(passwordEncoder.encode(request.getPassword()))
                    .build();
            User savedUser = userRepository.save(user);
            return mapToResponse(savedUser);
    }

    public UserResponse mapToResponse(User savedUser) {
        UserResponse response = new UserResponse();
        response.setId(savedUser.getId());
        response.setEmail(savedUser.getEmail());
        response.setFirstName(savedUser.getFirstName());
        response.setLastName(savedUser.getLastName());
        response.setCreatedAt(savedUser.getCreatedAt());
        response.setUpdatedAt(savedUser.getUpdatedAt());
        return response;
    }

    public User authenticate(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new InvalidCredentialException("Invalid Credentials"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialException("Invalid Credentials");
        }
        return user;
    }
}
