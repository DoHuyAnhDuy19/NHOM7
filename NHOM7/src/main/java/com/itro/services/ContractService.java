package com.itro.services;

import com.itro.dto.ContractDTO;
import java.util.List;

public interface ContractService {
    ContractDTO createContract(ContractDTO contractDTO);
    ContractDTO updateContract(Long contractId, ContractDTO contractDTO);
    ContractDTO getContractById(Long contractId);
    List<ContractDTO> getAllContracts();
    List<ContractDTO> getContractsByOwner(Long ownerId);
    List<ContractDTO> getContractsByTenant(Long tenantId);
    List<ContractDTO> getContractsByRoom(Long roomId);
    List<ContractDTO> getContractsByStatus(String status);
    void terminateContract(Long contractId);
}
