package com.smartparking.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private Long paymentId;
    private Long reservationId;
    private BigDecimal amount;
    private String paymentMethod;
    private String status;
    private LocalDateTime paymentTime;
}