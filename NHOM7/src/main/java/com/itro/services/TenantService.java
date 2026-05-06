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
