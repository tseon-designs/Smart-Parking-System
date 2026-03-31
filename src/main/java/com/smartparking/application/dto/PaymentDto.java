package com.smartparking.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private Long paymentId;
    private Long reservationId;
    private BigDecimal amount;
    private String paymentMethod;
    private String status;
    private LocalDateTime paymentTime;
}