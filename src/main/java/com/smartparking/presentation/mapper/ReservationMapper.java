package com.smartparking.presentation.mapper;

import com.smartparking.application.dto.ReservationDto;
import com.smartparking.domain.model.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    public ReservationDto toDto(Reservation reservation) {
        return new ReservationDto(
                reservation.getReservationId(),
                reservation.getSpotId(),
                reservation.getVehicleLicensePlate(),
                reservation.getStartTime(),
                reservation.getEndTime(),
                reservation.getStatus());
    }

    public Reservation toDomain(ReservationDto dto) {
        return new Reservation(
                dto.getReservationId(),
                dto.getSpotId(),
                dto.getVehicleLicensePlate(),
                dto.getStartTime(),
                dto.getEndTime(),
                dto.getStatus());
    }
}