// Thành viên 3 (Lê Hoàng Nam : 06/05/2026) — Dịch vụ & Hóa đơn
// Chức năng: Repository interface cho entity Invoice, cung cấp các phương thức truy vấn dữ liệu hóa đơn
package com.itro.repositories;

import com.itro.models.Invoice;
import com.itro.models.Contract;
import com.itro.models.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);
    List<Invoice> findByContract(Contract contract);
    List<Invoice> findByTenant(Tenant tenant);
    List<Invoice> findByStatus(Invoice.InvoiceStatus status);
    List<Invoice> findByDueDateBefore(LocalDate date);
    List<Invoice> findByContractAndStatus(Contract contract, Invoice.InvoiceStatus status);
}
