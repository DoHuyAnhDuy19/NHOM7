package com.itro.services.impl;

import com.itro.dto.InvoiceDTO;
import com.itro.models.Invoice;
import com.itro.models.Contract;
import com.itro.models.Tenant;
import com.itro.repositories.InvoiceRepository;
import com.itro.repositories.ContractRepository;
import com.itro.repositories.TenantRepository;
import com.itro.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Override
    public InvoiceDTO createInvoice(InvoiceDTO invoiceDTO) {
        Contract contract = contractRepository.findById(invoiceDTO.getContractId())
                .orElseThrow(() -> new RuntimeException("Hợp đồng không tồn tại"));

        Tenant tenant = tenantRepository.findById(invoiceDTO.getTenantId())
                .orElseThrow(() -> new RuntimeException("Khách thuê không tồn tại"));

        String invoiceNumber = "HOA_DON_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Invoice invoice = Invoice.builder()
                .invoiceNumber(invoiceNumber)
                .contract(contract)
                .tenant(tenant)
                .issuedDate(invoiceDTO.getIssuedDate())
                .dueDate(invoiceDTO.getDueDate())
                .rentalAmount(invoiceDTO.getRentalAmount())
                .waterBill(invoiceDTO.getWaterBill() != null ? invoiceDTO.getWaterBill() : java.math.BigDecimal.ZERO)
                .electricityBill(invoiceDTO.getElectricityBill() != null ? invoiceDTO.getElectricityBill() : java.math.BigDecimal.ZERO)
                .otherCharges(invoiceDTO.getOtherCharges() != null ? invoiceDTO.getOtherCharges() : java.math.BigDecimal.ZERO)
                .totalAmount(invoiceDTO.getTotalAmount())
                .paidAmount(java.math.BigDecimal.ZERO)
                .status(Invoice.InvoiceStatus.PENDING)
                .notes(invoiceDTO.getNotes())
                .build();

        invoiceRepository.save(invoice);
        return convertToInvoiceDTO(invoice);
    }

    @Override
    public InvoiceDTO updateInvoice(Long invoiceId, InvoiceDTO invoiceDTO) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));

        if (invoiceDTO.getWaterBill() != null) invoice.setWaterBill(invoiceDTO.getWaterBill());
        if (invoiceDTO.getElectricityBill() != null) invoice.setElectricityBill(invoiceDTO.getElectricityBill());
        if (invoiceDTO.getOtherCharges() != null) invoice.setOtherCharges(invoiceDTO.getOtherCharges());
        if (invoiceDTO.getNotes() != null) invoice.setNotes(invoiceDTO.getNotes());

        // Recalculate total
        java.math.BigDecimal total = invoice.getRentalAmount()
                .add(invoice.getWaterBill())
                .add(invoice.getElectricityBill())
                .add(invoice.getOtherCharges());
        invoice.setTotalAmount(total);

        invoiceRepository.save(invoice);
        return convertToInvoiceDTO(invoice);
    }

    @Override
    public InvoiceDTO getInvoiceById(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));
        return convertToInvoiceDTO(invoice);
    }

    @Override
    public List<InvoiceDTO> getAllInvoices() {
        return invoiceRepository.findAll()
                .stream()
                .map(this::convertToInvoiceDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDTO> getInvoicesByContract(Long contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new RuntimeException("Hợp đồng không tồn tại"));
        return invoiceRepository.findByContract(contract)
                .stream()
                .map(this::convertToInvoiceDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDTO> getInvoicesByTenant(Long tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Khách thuê không tồn tại"));
        return invoiceRepository.findByTenant(tenant)
                .stream()
                .map(this::convertToInvoiceDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDTO> getInvoicesByStatus(String status) {
        return invoiceRepository.findByStatus(Invoice.InvoiceStatus.valueOf(status.toUpperCase()))
                .stream()
                .map(this::convertToInvoiceDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceDTO markAsPaid(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));
        invoice.setStatus(Invoice.InvoiceStatus.PAID);
        invoice.setPaidAmount(invoice.getTotalAmount());
        invoiceRepository.save(invoice);
        return convertToInvoiceDTO(invoice);
    }

    @Override
    public List<InvoiceDTO> getOverdueInvoices() {
        return invoiceRepository.findByDueDateBefore(LocalDate.now())
                .stream()
                .filter(inv -> inv.getStatus() != Invoice.InvoiceStatus.PAID)
                .map(this::convertToInvoiceDTO)
                .collect(Collectors.toList());
    }

    private InvoiceDTO convertToInvoiceDTO(Invoice invoice) {
        return InvoiceDTO.builder()
                .id(invoice.getId())
                .invoiceNumber(invoice.getInvoiceNumber())
                .contractId(invoice.getContract().getId())
                .contractNumber(invoice.getContract().getContractNumber())
                .tenantId(invoice.getTenant().getId())
                .tenantName(invoice.getTenant().getUser().getFullName())
                .issuedDate(invoice.getIssuedDate())
                .dueDate(invoice.getDueDate())
                .rentalAmount(invoice.getRentalAmount())
                .waterBill(invoice.getWaterBill())
                .electricityBill(invoice.getElectricityBill())
                .otherCharges(invoice.getOtherCharges())
                .totalAmount(invoice.getTotalAmount())
                .paidAmount(invoice.getPaidAmount())
                .status(invoice.getStatus().toString())
                .notes(invoice.getNotes())
                .createdAt(invoice.getCreatedAt())
                .updatedAt(invoice.getUpdatedAt())
                .build();
    }
}
