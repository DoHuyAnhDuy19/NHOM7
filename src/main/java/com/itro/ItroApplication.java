// Thành viên 1 (Phạm Minh Duy : 06/05/2026) — Trang chính & Quản lý phòng
// Chức năng: Main class của ứng dụng Spring Boot, khởi tạo và chạy ứng dụng
package com.itro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ItroApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItroApplication.class, args);
    }
}
