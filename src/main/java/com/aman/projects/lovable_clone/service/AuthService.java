package com.aman.projects.lovable_clone.service;

import com.aman.projects.lovable_clone.dto.auth.AuthResponse;
import com.aman.projects.lovable_clone.dto.auth.LoginRequest;
import com.aman.projects.lovable_clone.dto.auth.SignupRequest;



public interface AuthService {
     AuthResponse signup(SignupRequest request);

     AuthResponse login(LoginRequest request);
}
