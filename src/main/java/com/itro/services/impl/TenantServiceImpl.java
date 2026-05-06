// Thành viên 4 (Vũ Tuấn Minh : 06/05/2026) — Hợp đồng, Khách thuê & Phòng
// Chức năng: Implementation của TenantService, xử lý logic nghiệp vụ quản lý khách thuê
package com.itro.services.impl;

import com.itro.dto.TenantDTO;
import com.itro.models.Tenant;
import com.itro.models.User;
import com.itro.repositories.TenantRepository;
import com.itro.repositories.UserRepository;
import com.itro.services.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TenantServiceImpl implements TenantService {

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public TenantDTO createTenant(Long userId, TenantDTO tenantDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));

        if (tenantRepository.findByIdNumber(tenantDTO.getIdNumber()).isPresent()) {
            throw new RuntimeException("Số CCCD/Hộ chiếu đã tồn tại");
        }

        Tenant tenant = Tenant.builder()
                .user(user)
                .idNumber(tenantDTO.getIdNumber())
                .idType(Tenant.IdType.valueOf(tenantDTO.getIdType().toUpperCase()))
                .address(tenantDTO.getAddress())
                .workplace(tenantDTO.getWorkplace())
                .emergencyContact(tenantDTO.getEmergencyContact())
                .emergencyPhone(tenantDTO.getEmergencyPhone())
                .isActive(true)
                .build();

        tenantRepository.save(tenant);
        return convertToTenantDTO(tenant);
    }

    @Override
    public TenantDTO updateTenant(Long tenantId, TenantDTO tenantDTO) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Khách thuê không tồn tại"));

        if (tenantDTO.getAddress() != null) tenant.setAddress(tenantDTO.getAddress());
        if (tenantDTO.getWorkplace() != null) tenant.setWorkplace(tenantDTO.getWorkplace());
        if (tenantDTO.getEmergencyContact() != null) tenant.setEmergencyContact(tenantDTO.getEmergencyContact());
        if (tenantDTO.getEmergencyPhone() != null) tenant.setEmergencyPhone(tenantDTO.getEmergencyPhone());

        tenantRepository.save(tenant);
        return convertToTenantDTO(tenant);
    }

    @Override
    public TenantDTO getTenantById(Long tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Khách thuê không tồn tại"));
        return convertToTenantDTO(tenant);
    }

    @Override
    public TenantDTO getTenantByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));
        Tenant tenant = tenantRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Khách thuê không tồn tại"));
        return convertToTenantDTO(tenant);
    }

    @Override
    public List<TenantDTO> getAllTenants() {
        return tenantRepository.findAll()
                .stream()
                .map(this::convertToTenantDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTenant(Long tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Khách thuê không tồn tại"));
        tenant.setIsActive(false);
        tenantRepository.save(tenant);
    }

    private TenantDTO convertToTenantDTO(Tenant tenant) {
        return TenantDTO.builder()
                .id(tenant.getId())
                .userId(tenant.getUser().getId())
                .userName(tenant.getUser().getFullName())
                .userEmail(tenant.getUser().getEmail())
                .userPhone(tenant.getUser().getPhone())
                .idNumber(tenant.getIdNumber())
                .idType(tenant.getIdType().toString())
                .address(tenant.getAddress())
                .workplace(tenant.getWorkplace())
                .emergencyContact(tenant.getEmergencyContact())
                .emergencyPhone(tenant.getEmergencyPhone())
                .isActive(tenant.getIsActive())
                .createdAt(tenant.getCreatedAt())
                .updatedAt(tenant.getUpdatedAt())
                .build();
    }
}
