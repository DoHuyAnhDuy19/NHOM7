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
