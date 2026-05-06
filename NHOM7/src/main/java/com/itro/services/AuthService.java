package com.itro.services;

import com.itro.dto.LoginRequest;
import com.itro.dto.LoginResponse;
import com.itro.dto.RegisterRequest;
import com.itro.dto.UserDTO;
import com.itro.models.User;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    UserDTO register(RegisterRequest request);
    UserDTO getUserProfile(Long userId);
    void updateProfile(Long userId, UserDTO userDTO);
}
