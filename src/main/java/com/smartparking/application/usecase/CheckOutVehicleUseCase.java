package com.smartparking.application.usecase;

import com.smartparking.application.dto.PaymentDto;
import com.smartparking.domain.model.Payment;
import com.smartparking.domain.model.Reservation;
import com.smartparking.domain.service.ParkingSpotService;
import com.smartparking.domain.service.PaymentService;
import com.smartparking.domain.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CheckOutVehicleUseCase {

    private final ReservationService reservationService;
    private final ParkingSpotService parkingSpotService;
    private final PaymentService paymentService;

    public PaymentDto checkOut(Long reservationId, String paymentMethod) {
        // Find reservation
        Reservation reservation = reservationService.findById(reservationId);
        if (!"ACTIVE".equals(reservation.getStatus())) {
            throw new IllegalStateException("Reservation is not active");
        }

        // Calculate cost
        LocalDateTime endTime = LocalDateTime.now();
        reservation.setEndTime(endTime);
        long hours = Duration.between(reservation.getStartTime(), endTime).toHours();
        if (hours == 0)
            hours = 1;
        var spot = parkingSpotService.findById(reservation.getSpotId());
        BigDecimal amount = spot.getPricePerHour().multiply(BigDecimal.valueOf(hours));

        // Create payment
        Payment payment = new Payment(null, reservationId, amount, paymentMethod, "COMPLETED", LocalDateTime.now());

        Payment processed = paymentService.processPayment(payment);

        // Update reservation status
        reservation.setStatus("COMPLETED");
        reservationService.save(reservation); // need a save method in service? We'll add it.

        // Free the spot
        spot.setOccupied(false);
        parkingSpotService.save(spot);

        // Return payment DTO
        return new PaymentDto(
                processed.getPaymentId(),
                processed.getReservationId(),
                processed.getAmount(),
                processed.getPaymentMethod(),
                processed.getStatus(),
                processed.getPaymentTime());
    }
}