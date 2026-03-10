package com.aman.projects.lovable_clone.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record SignupRequest(
       @Email String email,
       @Size(min=1, max=30) String name,
       @Size(min=1) String password
) {
}
