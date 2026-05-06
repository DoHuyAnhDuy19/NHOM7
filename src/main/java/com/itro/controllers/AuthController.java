// Thành viên 2 (Đỗ Huy Anh Duy : 06/05/2026) — Đăng ký & Đăng nhập
// Chức năng: Controller xử lý các API liên quan đến xác thực người dùng (đăng nhập, đăng ký)
package com.itro.controllers;

import com.itro.dto.ApiResponse;
import com.itro.dto.LoginRequest;
import com.itro.dto.RegisterRequest;
import com.itro.dto.UserDTO;
import com.itro.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            var response = authService.login(request);
            return ResponseEntity.ok(ApiResponse.success("Đăng nhập thành công", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Đăng nhập thất bại", e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterRequest request) {
        try {
            var user = authService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Đăng ký thành công", user));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Đăng ký thất bại", e.getMessage()));
        }
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<ApiResponse> getProfile(@PathVariable Long userId) {
        try {
            var user = authService.getUserProfile(userId);
            return ResponseEntity.ok(ApiResponse.success("Lấy hồ sơ thành công", user));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Lấy hồ sơ thất bại", e.getMessage()));
        }
    }

    @PutMapping("/profile/{userId}")
    public ResponseEntity<ApiResponse> updateProfile(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
        try {
            authService.updateProfile(userId, userDTO);
            return ResponseEntity.ok(ApiResponse.success("Cập nhật hồ sơ thành công"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Cập nhật hồ sơ thất bại", e.getMessage()));
        }
    }
}
