package com.itro.repositories;

import com.itro.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    Optional<Service> findByServiceName(String serviceName);
    List<Service> findByServiceType(Service.ServiceType serviceType);
    List<Service> findByIsActive(Boolean isActive);
}
