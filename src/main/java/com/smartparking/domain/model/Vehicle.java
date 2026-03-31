package com.smartparking.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    private Long vehicleId;
    private String licensePlate;
    private String type;
    private String ownerName;
}