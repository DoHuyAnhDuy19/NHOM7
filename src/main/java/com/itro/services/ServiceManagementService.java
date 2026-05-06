// Thành viên 3 (Lê Hoàng Nam : 06/05/2026) — Dịch vụ & Hóa đơn
// Chức năng: Interface định nghĩa các phương thức xử lý logic nghiệp vụ liên quan đến dịch vụ
package com.itro.services;

import com.itro.dto.ServiceDTO;
import java.util.List;

public interface ServiceManagementService {
    ServiceDTO createService(ServiceDTO serviceDTO);
    ServiceDTO updateService(Long serviceId, ServiceDTO serviceDTO);
    ServiceDTO getServiceById(Long serviceId);
    List<ServiceDTO> getAllServices();
    List<ServiceDTO> getServicesByType(String serviceType);
    void deleteService(Long serviceId);
}
