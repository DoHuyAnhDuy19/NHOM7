// Thành viên 3 (Lê Hoàng Nam : 06/05/2026) — Dịch vụ & Hóa đơn
// Chức năng: Implementation của ServiceManagementService, xử lý logic nghiệp vụ quản lý dịch vụ
package com.itro.services.impl;

import com.itro.dto.ServiceDTO;
import com.itro.models.Service;
import com.itro.repositories.ServiceRepository;
import com.itro.services.ServiceManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class ServiceManagementServiceImpl implements ServiceManagementService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public ServiceDTO createService(ServiceDTO serviceDTO) {
        if (serviceRepository.findByServiceName(serviceDTO.getServiceName()).isPresent()) {
            throw new RuntimeException("Dịch vụ đã tồn tại");
        }

        com.itro.models.Service service = com.itro.models.Service.builder()
                .serviceName(serviceDTO.getServiceName())
                .description(serviceDTO.getDescription())
                .price(serviceDTO.getPrice())
                .serviceType(com.itro.models.Service.ServiceType.valueOf(serviceDTO.getServiceType().toUpperCase()))
                .isActive(true)
                .build();

        serviceRepository.save(service);
        return convertToServiceDTO(service);
    }

    @Override
    public ServiceDTO updateService(Long serviceId, ServiceDTO serviceDTO) {
        com.itro.models.Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Dịch vụ không tồn tại"));

        if (serviceDTO.getServiceName() != null) service.setServiceName(serviceDTO.getServiceName());
        if (serviceDTO.getDescription() != null) service.setDescription(serviceDTO.getDescription());
        if (serviceDTO.getPrice() != null) service.setPrice(serviceDTO.getPrice());

        serviceRepository.save(service);
        return convertToServiceDTO(service);
    }

    @Override
    public ServiceDTO getServiceById(Long serviceId) {
        com.itro.models.Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Dịch vụ không tồn tại"));
        return convertToServiceDTO(service);
    }

    @Override
    public List<ServiceDTO> getAllServices() {
        return serviceRepository.findByIsActive(true)
                .stream()
                .map(this::convertToServiceDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ServiceDTO> getServicesByType(String serviceType) {
        return serviceRepository.findByServiceType(com.itro.models.Service.ServiceType.valueOf(serviceType.toUpperCase()))
                .stream()
                .map(this::convertToServiceDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteService(Long serviceId) {
        com.itro.models.Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Dịch vụ không tồn tại"));
        service.setIsActive(false);
        serviceRepository.save(service);
    }

    private ServiceDTO convertToServiceDTO(Service service) {
        return ServiceDTO.builder()
                .id(service.getId())
                .serviceName(service.getServiceName())
                .description(service.getDescription())
                .price(service.getPrice())
                .serviceType(service.getServiceType().toString())
                .isActive(service.getIsActive())
                .createdAt(service.getCreatedAt())
                .updatedAt(service.getUpdatedAt())
                .build();
    }
}
