// Thành viên 3 (Lê Hoàng Nam : 06/05/2026) — Dịch vụ & Hóa đơn
// Chức năng: Interface định nghĩa các phương thức xử lý logic nghiệp vụ liên quan đến hóa đơn
package com.itro.services;

import com.itro.dto.InvoiceDTO;
import java.util.List;

public interface InvoiceService {
    InvoiceDTO createInvoice(InvoiceDTO invoiceDTO);
    InvoiceDTO updateInvoice(Long invoiceId, InvoiceDTO invoiceDTO);
    InvoiceDTO getInvoiceById(Long invoiceId);
    List<InvoiceDTO> getAllInvoices();
    List<InvoiceDTO> getInvoicesByContract(Long contractId);
    List<InvoiceDTO> getInvoicesByTenant(Long tenantId);
    List<InvoiceDTO> getInvoicesByStatus(String status);
    InvoiceDTO markAsPaid(Long invoiceId);
    List<InvoiceDTO> getOverdueInvoices();
}
