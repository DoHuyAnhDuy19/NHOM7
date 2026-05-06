package com.itro.controllers;

import com.itro.dto.ApiResponse;
import com.itro.dto.ContractDTO;
import com.itro.services.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contracts")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ContractController {

    @Autowired
    private ContractService contractService;

    @PostMapping
    public ResponseEntity<ApiResponse> createContract(@RequestBody ContractDTO contractDTO) {
        try {
            var contract = contractService.createContract(contractDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Tạo hợp đồng thành công", contract));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Tạo hợp đồng thất bại", e.getMessage()));
        }
    }

    @PutMapping("/{contractId}")
    public ResponseEntity<ApiResponse> updateContract(@PathVariable Long contractId, @RequestBody ContractDTO contractDTO) {
        try {
            var contract = contractService.updateContract(contractId, contractDTO);
            return ResponseEntity.ok(ApiResponse.success("Cập nhật hợp đồng thành công", contract));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Cập nhật hợp đồng thất bại", e.getMessage()));
        }
    }

    @GetMapping("/{contractId}")
    public ResponseEntity<ApiResponse> getContractById(@PathVariable Long contractId) {
        try {
            var contract = contractService.getContractById(contractId);
            return ResponseEntity.ok(ApiResponse.success("Lấy thông tin hợp đồng thành công", contract));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Lấy thông tin hợp đồng thất bại", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllContracts() {
        try {
            var contracts = contractService.getAllContracts();
            return ResponseEntity.ok(ApiResponse.success("Lấy danh sách hợp đồng thành công", contracts));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Lấy danh sách hợp đồng thất bại", e.getMessage()));
        }
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<ApiResponse> getContractsByOwner(@PathVariable Long ownerId) {
        try {
            var contracts = contractService.getContractsByOwner(ownerId);
            return ResponseEntity.ok(ApiResponse.success("Lấy danh sách hợp đồng của chủ nhà thành công", contracts));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Lấy danh sách hợp đồng thất bại", e.getMessage()));
        }
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<ApiResponse> getContractsByTenant(@PathVariable Long tenantId) {
        try {
            var contracts = contractService.getContractsByTenant(tenantId);
            return ResponseEntity.ok(ApiResponse.success("Lấy danh sách hợp đồng của khách thuê thành công", contracts));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Lấy danh sách hợp đồng thất bại", e.getMessage()));
        }
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<ApiResponse> getContractsByRoom(@PathVariable Long roomId) {
        try {
            var contracts = contractService.getContractsByRoom(roomId);
            return ResponseEntity.ok(ApiResponse.success("Lấy danh sách hợp đồng của phòng thành công", contracts));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Lấy danh sách hợp đồng thất bại", e.getMessage()));
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse> getContractsByStatus(@PathVariable String status) {
        try {
            var contracts = contractService.getContractsByStatus(status);
            return ResponseEntity.ok(ApiResponse.success("Lấy danh sách hợp đồng theo trạng thái thành công", contracts));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Lấy danh sách hợp đồng thất bại", e.getMessage()));
        }
    }

    @PatchMapping("/{contractId}/terminate")
    public ResponseEntity<ApiResponse> terminateContract(@PathVariable Long contractId) {
        try {
            contractService.terminateContract(contractId);
            return ResponseEntity.ok(ApiResponse.success("Chấm dứt hợp đồng thành công"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Chấm dứt hợp đồng thất bại", e.getMessage()));
        }
    }
}
