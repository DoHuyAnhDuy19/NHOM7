package com.itro.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractDTO {
    private Long id;
    private String contractNumber;
    private Long roomId;
    private String roomName;
    private Long tenantId;
    private String tenantName;
    private Long ownerId;
    private String ownerName;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal rentalAmount;
    private BigDecimal depositAmount;
    private String terms;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
