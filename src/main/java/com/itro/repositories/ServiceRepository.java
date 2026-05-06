// Thành viên 3 (Lê Hoàng Nam : 06/05/2026) — Dịch vụ & Hóa đơn
// Chức năng: Repository interface cho entity Service, cung cấp các phương thức truy vấn dữ liệu dịch vụ
package com.itro.repositories;

import com.itro.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    Optional<Service> findByServiceName(String serviceName);
    List<Service> findByServiceType(com.itro.models.Service.ServiceType serviceType);
    List<Service> findByIsActive(Boolean isActive);
}
