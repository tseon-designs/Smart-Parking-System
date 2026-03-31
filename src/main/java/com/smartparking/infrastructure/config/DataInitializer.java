package com.smartparking.infrastructure.config;

import com.smartparking.domain.model.ParkingLot;
import com.smartparking.domain.model.ParkingSpot;
import com.smartparking.domain.model.Vehicle;
import com.smartparking.domain.repository.ParkingLotRepository;
import com.smartparking.domain.repository.ParkingSpotRepository;
import com.smartparking.domain.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Seeds the database with initial development data on startup.
 * Only active for the "dev" and "default" Spring profiles.
 * Uses the domain repository interfaces (not JPA directly) to respect
 * the clean-architecture boundary.
 */
@Slf4j
@Component
@Profile({"dev", "default"})
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final ParkingLotRepository parkingLotRepository;
    private final ParkingSpotRepository parkingSpotRepository;
    private final VehicleRepository vehicleRepository;

    @Override
    public void run(ApplicationArguments args) {
        log.info("============================================================");
        log.info("  DataInitializer: seeding development data...");
        log.info("============================================================");

        seedParkingLots();
        seedVehicles();

        long lots  = 0; // can't easily count without custom method; just log success
        log.info("  DataInitializer: seed complete.");
        log.info("============================================================");
    }

    // ── Parking Lots & Spots ──────────────────────────────────────────────────

    private void seedParkingLots() {
        // Lot 1 — Central Park Garage
        ParkingLot lot1 = parkingLotRepository.save(new ParkingLot(null, "Central Park Garage", 50));
        log.info("  [DB] Created parking lot: Central Park Garage (ID: {})", lot1.getParkingLotId());
        seedSpotsForLot(lot1.getParkingLotId(), BigDecimal.valueOf(15.00), 10);

        // Lot 2 — Airport Parking Hub
        ParkingLot lot2 = parkingLotRepository.save(new ParkingLot(null, "Airport Parking Hub", 30));
        log.info("  [DB] Created parking lot: Airport Parking Hub (ID: {})", lot2.getParkingLotId());
        seedSpotsForLot(lot2.getParkingLotId(), BigDecimal.valueOf(20.00), 5);

        // Lot 3 — Bole Business Center
        ParkingLot lot3 = parkingLotRepository.save(new ParkingLot(null, "Bole Business Center", 20));
        log.info("  [DB] Created parking lot: Bole Business Center (ID: {})", lot3.getParkingLotId());
        seedSpotsForLot(lot3.getParkingLotId(), BigDecimal.valueOf(18.00), 5);
    }

    private void seedSpotsForLot(Long lotId, BigDecimal pricePerHour, int count) {
        for (int i = 1; i <= count; i++) {
            String location = "ROW-" + (char) ('A' + (i - 1) / 5) + "-" + String.format("%02d", i);
            parkingSpotRepository.save(new ParkingSpot(null, location, false, pricePerHour, lotId));
        }
        log.info("  [DB] Seeded {} spots for lot ID: {}", count, lotId);
    }

    // ── Vehicles ─────────────────────────────────────────────────────────────

    private void seedVehicles() {
        List<Vehicle> vehicles = List.of(
                new Vehicle(null, "AA-12345", "CAR",        "Tsion Fikru"),
                new Vehicle(null, "AA-67890", "CAR",        "Abebe Kebede"),
                new Vehicle(null, "AA-11111", "MOTORCYCLE", "Hana Girma"),
                new Vehicle(null, "AA-22222", "TRUCK",      "Dawit Solomon"),
                new Vehicle(null, "AA-33333", "CAR",        "Meron Tadesse")
        );

        for (Vehicle v : vehicles) {
            if (!vehicleRepository.existsByLicensePlate(v.getLicensePlate())) {
                vehicleRepository.save(v);
                log.info("  [DB] Registered vehicle: {} ({})", v.getLicensePlate(), v.getOwnerName());
            }
        }
    }
}
