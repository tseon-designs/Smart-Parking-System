package com.smartparking.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    private Long reservationId;
    private Long spotId;
    private String vehicleLicensePlate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
}