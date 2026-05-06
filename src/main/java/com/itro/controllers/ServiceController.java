// Thành viên 3 (Lê Hoàng Nam : 06/05/2026) — Dịch vụ & Hóa đơn
// Chức năng: Controller xử lý các API liên quan đến quản lý dịch vụ
package com.itro.controllers;

import com.itro.dto.ApiResponse;
import com.itro.dto.ServiceDTO;
import com.itro.services.ServiceManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/services")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ServiceController {

    @Autowired
    private ServiceManagementService serviceManagementService;

    @PostMapping
    public ResponseEntity<ApiResponse> createService(@RequestBody ServiceDTO serviceDTO) {
        try {
            var service = serviceManagementService.createService(serviceDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Tạo dịch vụ thành công", service));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Tạo dịch vụ thất bại", e.getMessage()));
        }
    }

    @PutMapping("/{serviceId}")
    public ResponseEntity<ApiResponse> updateService(@PathVariable Long serviceId, @RequestBody ServiceDTO serviceDTO) {
        try {
            var service = serviceManagementService.updateService(serviceId, serviceDTO);
            return ResponseEntity.ok(ApiResponse.success("Cập nhật dịch vụ thành công", service));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Cập nhật dịch vụ thất bại", e.getMessage()));
        }
    }

    @GetMapping("/{serviceId}")
    public ResponseEntity<ApiResponse> getServiceById(@PathVariable Long serviceId) {
        try {
            var service = serviceManagementService.getServiceById(serviceId);
            return ResponseEntity.ok(ApiResponse.success("Lấy thông tin dịch vụ thành công", service));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Lấy thông tin dịch vụ thất bại", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllServices() {
        try {
            var services = serviceManagementService.getAllServices();
            return ResponseEntity.ok(ApiResponse.success("Lấy danh sách dịch vụ thành công", services));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Lấy danh sách dịch vụ thất bại", e.getMessage()));
        }
    }

    @GetMapping("/type/{serviceType}")
    public ResponseEntity<ApiResponse> getServicesByType(@PathVariable String serviceType) {
        try {
            var services = serviceManagementService.getServicesByType(serviceType);
            return ResponseEntity.ok(ApiResponse.success("Lấy danh sách dịch vụ theo loại thành công", services));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Lấy danh sách dịch vụ thất bại", e.getMessage()));
        }
    }

    @DeleteMapping("/{serviceId}")
    public ResponseEntity<ApiResponse> deleteService(@PathVariable Long serviceId) {
        try {
            serviceManagementService.deleteService(serviceId);
            return ResponseEntity.ok(ApiResponse.success("Xóa dịch vụ thành công"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Xóa dịch vụ thất bại", e.getMessage()));
        }
    }
}
