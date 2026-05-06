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
