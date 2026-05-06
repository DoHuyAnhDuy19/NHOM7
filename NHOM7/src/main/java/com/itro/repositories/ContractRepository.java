package com.itro.repositories;

import com.itro.models.Contract;
import com.itro.models.Room;
import com.itro.models.Tenant;
import com.itro.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    Optional<Contract> findByContractNumber(String contractNumber);
    List<Contract> findByRoom(Room room);
    List<Contract> findByTenant(Tenant tenant);
    List<Contract> findByOwner(User owner);
    List<Contract> findByStatus(Contract.ContractStatus status);
    List<Contract> findByOwnerAndStatus(User owner, Contract.ContractStatus status);
}
