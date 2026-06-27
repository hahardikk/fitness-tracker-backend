package com.example.demo.dto;

import com.example.demo.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotBlank(message = "Email is Required")
    @Email(message = "Invalid Email")
    private String email;

    @NotBlank(message = "Password is Required")
    @Size(min=6, message = "Password must least have 6 characters")
    private String password;

    @NotBlank(message = "First Name is Required")
    private String firstName;
    private String lastName;
    private UserRole role;
}
