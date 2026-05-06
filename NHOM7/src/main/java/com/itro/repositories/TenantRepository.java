package com.itro.repositories;

import com.itro.models.Tenant;
import com.itro.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
    Optional<Tenant> findByUser(User user);
    Optional<Tenant> findByIdNumber(String idNumber);
    List<Tenant> findByIsActive(Boolean isActive);
}
