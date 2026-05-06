// Thành viên 2 (Đỗ Huy Anh Duy : 06/05/2026) — Đăng ký & Đăng nhập
// Chức năng: Interface định nghĩa các phương thức xử lý logic nghiệp vụ xác thực người dùng
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
