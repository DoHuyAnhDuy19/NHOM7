package com.itro.controllers;

import com.itro.dto.ApiResponse;
import com.itro.dto.TenantDTO;
import com.itro.services.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tenants")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TenantController {

    @Autowired
    private TenantService tenantService;

    @PostMapping
    public ResponseEntity<ApiResponse> createTenant(@RequestParam Long userId, @RequestBody TenantDTO tenantDTO) {
        try {
            var tenant = tenantService.createTenant(userId, tenantDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Tạo hồ sơ khách thuê thành công", tenant));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Tạo hồ sơ khách thuê thất bại", e.getMessage()));
        }
    }

    @PutMapping("/{tenantId}")
    public ResponseEntity<ApiResponse> updateTenant(@PathVariable Long tenantId, @RequestBody TenantDTO tenantDTO) {
        try {
            var tenant = tenantService.updateTenant(tenantId, tenantDTO);
            return ResponseEntity.ok(ApiResponse.success("Cập nhật hồ sơ khách thuê thành công", tenant));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Cập nhật hồ sơ khách thuê thất bại", e.getMessage()));
        }
    }

    @GetMapping("/{tenantId}")
    public ResponseEntity<ApiResponse> getTenantById(@PathVariable Long tenantId) {
        try {
            var tenant = tenantService.getTenantById(tenantId);
            return ResponseEntity.ok(ApiResponse.success("Lấy thông tin khách thuê thành công", tenant));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Lấy thông tin khách thuê thất bại", e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getTenantByUser(@PathVariable Long userId) {
        try {
            var tenant = tenantService.getTenantByUser(userId);
            return ResponseEntity.ok(ApiResponse.success("Lấy thông tin khách thuê thành công", tenant));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Lấy thông tin khách thuê thất bại", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllTenants() {
        try {
            var tenants = tenantService.getAllTenants();
            return ResponseEntity.ok(ApiResponse.success("Lấy danh sách khách thuê thành công", tenants));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Lấy danh sách khách thuê thất bại", e.getMessage()));
        }
    }

    @DeleteMapping("/{tenantId}")
    public ResponseEntity<ApiResponse> deleteTenant(@PathVariable Long tenantId) {
        try {
            tenantService.deleteTenant(tenantId);
            return ResponseEntity.ok(ApiResponse.success("Xóa khách thuê thành công"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Xóa khách thuê thất bại", e.getMessage()));
        }
    }
}
