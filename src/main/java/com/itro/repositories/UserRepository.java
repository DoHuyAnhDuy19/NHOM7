// Thành viên 2 (Đỗ Huy Anh Duy : 06/05/2026) — Đăng ký & Đăng nhập
// Chức năng: Repository interface cho entity User, cung cấp các phương thức truy vấn dữ liệu người dùng
package com.itro.repositories;

import com.itro.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
}
