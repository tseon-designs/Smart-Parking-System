package com.smartparking.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSpotDto {
    private Long spotId;
    private String location;
    private boolean occupied;
    private BigDecimal pricePerHour;
    private Long parkingLotId;
}