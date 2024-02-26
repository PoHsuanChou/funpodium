package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@AllArgsConstructor

public class DeleteUserRequest {
    @NonNull
    @Email
    @NotBlank
    private String email;

    @NonNull
    @NotBlank
    private String username;
}
