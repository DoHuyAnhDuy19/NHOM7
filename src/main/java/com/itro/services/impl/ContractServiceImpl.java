// Thành viên 4 (Vũ Tuấn Minh : 06/05/2026) — Hợp đồng, Khách thuê & Phòng
// Chức năng: Implementation của ContractService, xử lý logic nghiệp vụ quản lý hợp đồng
package com.itro.services.impl;

import com.itro.dto.ContractDTO;
import com.itro.models.Contract;
import com.itro.models.Room;
import com.itro.models.Tenant;
import com.itro.models.User;
import com.itro.repositories.ContractRepository;
import com.itro.repositories.RoomRepository;
import com.itro.repositories.TenantRepository;
import com.itro.repositories.UserRepository;
import com.itro.services.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ContractDTO createContract(ContractDTO contractDTO) {
        Room room = roomRepository.findById(contractDTO.getRoomId())
                .orElseThrow(() -> new RuntimeException("Phòng không tồn tại"));

        Tenant tenant = tenantRepository.findById(contractDTO.getTenantId())
                .orElseThrow(() -> new RuntimeException("Khách thuê không tồn tại"));

        User owner = userRepository.findById(contractDTO.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Chủ nhà không tồn tại"));

        String contractNumber = "HOP_DONG_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Contract contract = Contract.builder()
                .contractNumber(contractNumber)
                .room(room)
                .tenant(tenant)
                .owner(owner)
                .startDate(contractDTO.getStartDate())
                .endDate(contractDTO.getEndDate())
                .rentalAmount(contractDTO.getRentalAmount())
                .depositAmount(contractDTO.getDepositAmount())
                .terms(contractDTO.getTerms())
                .status(Contract.ContractStatus.PENDING)
                .build();

        contractRepository.save(contract);
        room.setStatus(Room.RoomStatus.RENTED);
        roomRepository.save(room);

        return convertToContractDTO(contract);
    }

    @Override
    public ContractDTO updateContract(Long contractId, ContractDTO contractDTO) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new RuntimeException("Hợp đồng không tồn tại"));

        if (contractDTO.getTerms() != null) contract.setTerms(contractDTO.getTerms());
        if (contractDTO.getStatus() != null) {
            contract.setStatus(Contract.ContractStatus.valueOf(contractDTO.getStatus().toUpperCase()));
        }

        contractRepository.save(contract);
        return convertToContractDTO(contract);
    }

    @Override
    public ContractDTO getContractById(Long contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new RuntimeException("Hợp đồng không tồn tại"));
        return convertToContractDTO(contract);
    }

    @Override
    public List<ContractDTO> getAllContracts() {
        return contractRepository.findAll()
                .stream()
                .map(this::convertToContractDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ContractDTO> getContractsByOwner(Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Chủ nhà không tồn tại"));
        return contractRepository.findByOwner(owner)
                .stream()
                .map(this::convertToContractDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ContractDTO> getContractsByTenant(Long tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Khách thuê không tồn tại"));
        return contractRepository.findByTenant(tenant)
                .stream()
                .map(this::convertToContractDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ContractDTO> getContractsByRoom(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Phòng không tồn tại"));
        return contractRepository.findByRoom(room)
                .stream()
                .map(this::convertToContractDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ContractDTO> getContractsByStatus(String status) {
        return contractRepository.findByStatus(Contract.ContractStatus.valueOf(status.toUpperCase()))
                .stream()
                .map(this::convertToContractDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void terminateContract(Long contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new RuntimeException("Hợp đồng không tồn tại"));
        contract.setStatus(Contract.ContractStatus.TERMINATED);
        contract.getRoom().setStatus(Room.RoomStatus.AVAILABLE);
        contractRepository.save(contract);
        roomRepository.save(contract.getRoom());
    }

    private ContractDTO convertToContractDTO(Contract contract) {
        return ContractDTO.builder()
                .id(contract.getId())
                .contractNumber(contract.getContractNumber())
                .roomId(contract.getRoom().getId())
                .roomName(contract.getRoom().getRoomName())
                .tenantId(contract.getTenant().getId())
                .tenantName(contract.getTenant().getUser().getFullName())
                .ownerId(contract.getOwner().getId())
                .ownerName(contract.getOwner().getFullName())
                .startDate(contract.getStartDate())
                .endDate(contract.getEndDate())
                .rentalAmount(contract.getRentalAmount())
                .depositAmount(contract.getDepositAmount())
                .terms(contract.getTerms())
                .status(contract.getStatus().toString())
                .createdAt(contract.getCreatedAt())
                .updatedAt(contract.getUpdatedAt())
                .build();
    }
}
