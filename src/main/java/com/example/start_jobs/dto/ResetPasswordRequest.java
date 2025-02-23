package com.example.start_jobs.dto;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String token;
    private String newPassword;
}
