// Thành viên 2 (Đỗ Huy Anh Duy : 06/05/2026) — Đăng ký & Đăng nhập
// Chức năng: DTO class cho request đăng nhập
package com.itro.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {
    private String email;
    private String password;
}
