// Thành viên 2 (Đỗ Huy Anh Duy : 06/05/2026) — Đăng ký & Đăng nhập
// Chức năng: DTO class để truyền dữ liệu người dùng giữa các tầng của ứng dụng
package com.itro.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String email;
    private String username;
    private String fullName;
    private String phone;
    private String role;
    private String address;
    private String avatar;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
