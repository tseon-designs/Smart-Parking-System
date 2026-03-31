package com.smartparking.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    private Long reservationId;
    private Long spotId;
    private String vehicleLicensePlate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
}