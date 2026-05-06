// Thành viên 3 (Lê Hoàng Nam : 06/05/2026) — Dịch vụ & Hóa đơn
// Chức năng: Controller xử lý các API liên quan đến quản lý hóa đơn (tạo hóa đơn, thanh toán)
package com.itro.controllers;

import com.itro.dto.ApiResponse;
import com.itro.dto.InvoiceDTO;
import com.itro.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoices")
@CrossOrigin(origins = "*", maxAge = 3600)
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<ApiResponse> createInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        try {
            var invoice = invoiceService.createInvoice(invoiceDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Tạo hóa đơn thành công", invoice));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Tạo hóa đơn thất bại", e.getMessage()));
        }
    }

    @PutMapping("/{invoiceId}")
    public ResponseEntity<ApiResponse> updateInvoice(@PathVariable Long invoiceId, @RequestBody InvoiceDTO invoiceDTO) {
        try {
            var invoice = invoiceService.updateInvoice(invoiceId, invoiceDTO);
            return ResponseEntity.ok(ApiResponse.success("Cập nhật hóa đơn thành công", invoice));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Cập nhật hóa đơn thất bại", e.getMessage()));
        }
    }

    @GetMapping("/{invoiceId}")
    public ResponseEntity<ApiResponse> getInvoiceById(@PathVariable Long invoiceId) {
        try {
            var invoice = invoiceService.getInvoiceById(invoiceId);
            return ResponseEntity.ok(ApiResponse.success("Lấy thông tin hóa đơn thành công", invoice));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Lấy thông tin hóa đơn thất bại", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllInvoices() {
        try {
            var invoices = invoiceService.getAllInvoices();
            return ResponseEntity.ok(ApiResponse.success("Lấy danh sách hóa đơn thành công", invoices));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Lấy danh sách hóa đơn thất bại", e.getMessage()));
        }
    }

    @GetMapping("/contract/{contractId}")
    public ResponseEntity<ApiResponse> getInvoicesByContract(@PathVariable Long contractId) {
        try {
            var invoices = invoiceService.getInvoicesByContract(contractId);
            return ResponseEntity.ok(ApiResponse.success("Lấy danh sách hóa đơn của hợp đồng thành công", invoices));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Lấy danh sách hóa đơn thất bại", e.getMessage()));
        }
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<ApiResponse> getInvoicesByTenant(@PathVariable Long tenantId) {
        try {
            var invoices = invoiceService.getInvoicesByTenant(tenantId);
            return ResponseEntity.ok(ApiResponse.success("Lấy danh sách hóa đơn của khách thuê thành công", invoices));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Lấy danh sách hóa đơn thất bại", e.getMessage()));
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse> getInvoicesByStatus(@PathVariable String status) {
        try {
            var invoices = invoiceService.getInvoicesByStatus(status);
            return ResponseEntity.ok(ApiResponse.success("Lấy danh sách hóa đơn theo trạng thái thành công", invoices));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Lấy danh sách hóa đơn thất bại", e.getMessage()));
        }
    }

    @PatchMapping("/{invoiceId}/pay")
    public ResponseEntity<ApiResponse> markAsPaid(@PathVariable Long invoiceId) {
        try {
            var invoice = invoiceService.markAsPaid(invoiceId);
            return ResponseEntity.ok(ApiResponse.success("Đánh dấu hóa đơn đã thanh toán thành công", invoice));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Đánh dấu hóa đơn thất bại", e.getMessage()));
        }
    }

    @GetMapping("/overdue")
    public ResponseEntity<ApiResponse> getOverdueInvoices() {
        try {
            var invoices = invoiceService.getOverdueInvoices();
            return ResponseEntity.ok(ApiResponse.success("Lấy danh sách hóa đơn quá hạn thành công", invoices));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Lấy danh sách hóa đơn thất bại", e.getMessage()));
        }
    }
}
