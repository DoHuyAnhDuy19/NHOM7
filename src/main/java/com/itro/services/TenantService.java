// Thành viên 4 (Vũ Tuấn Minh : 06/05/2026) — Hợp đồng, Khách thuê & Phòng
// Chức năng: Interface định nghĩa các phương thức xử lý logic nghiệp vụ liên quan đến khách thuê
package com.itro.services;

import com.itro.dto.TenantDTO;
import java.util.List;

public interface TenantService {
    TenantDTO createTenant(Long userId, TenantDTO tenantDTO);
    TenantDTO updateTenant(Long tenantId, TenantDTO tenantDTO);
    TenantDTO getTenantById(Long tenantId);
    TenantDTO getTenantByUser(Long userId);
    List<TenantDTO> getAllTenants();
    void deleteTenant(Long tenantId);
}
