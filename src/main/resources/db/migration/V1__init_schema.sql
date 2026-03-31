-- ============================================================
-- Smart Parking System — Initial Schema (DDL)
-- Compatible with: PostgreSQL 13+
-- Run order: V1 (Flyway) or manually on a fresh DB
-- ============================================================

-- ── Parking Lots ───────────────────────────────────────────
CREATE TABLE IF NOT EXISTS parking_lots (
    parking_lot_id   VARCHAR(36)  NOT NULL,
    name             VARCHAR(150) NOT NULL,
    total_spots      INTEGER      NOT NULL CHECK (total_spots > 0),
    address          VARCHAR(300),
    city             VARCHAR(100),
    phone            VARCHAR(20),
    active           BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at       TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMP,
    CONSTRAINT pk_parking_lots PRIMARY KEY (parking_lot_id)
);

-- ── Parking Spots ──────────────────────────────────────────
CREATE TABLE IF NOT EXISTS parking_spots (
    spot_id          VARCHAR(36)    NOT NULL,
    location         VARCHAR(100)   NOT NULL,
    occupied         BOOLEAN        NOT NULL DEFAULT FALSE,
    price_per_hour   NUMERIC(10,2)  NOT NULL CHECK (price_per_hour >= 0),
    parking_lot_id   VARCHAR(36)    NOT NULL,
    spot_type        VARCHAR(30)    NOT NULL DEFAULT 'STANDARD',
    floor_level      INTEGER        NOT NULL DEFAULT 1,
    created_at       TIMESTAMP      NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMP,
    CONSTRAINT pk_parking_spots  PRIMARY KEY (spot_id),
    CONSTRAINT fk_spot_lot       FOREIGN KEY (parking_lot_id)
                                 REFERENCES parking_lots(parking_lot_id)
                                 ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_spot_lot      ON parking_spots(parking_lot_id);
CREATE INDEX IF NOT EXISTS idx_spot_occupied ON parking_spots(occupied);

-- ── Vehicles ───────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS vehicles (
    license_plate   VARCHAR(20)  NOT NULL,
    vehicle_type    VARCHAR(30)  NOT NULL,
    owner_name      VARCHAR(150) NOT NULL,
    owner_email     VARCHAR(200),
    owner_phone     VARCHAR(20),
    make            VARCHAR(60),
    model           VARCHAR(60),
    color           VARCHAR(40),
    year            INTEGER,
    active          BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP,
    CONSTRAINT pk_vehicles PRIMARY KEY (license_plate)
);

CREATE INDEX IF NOT EXISTS idx_vehicle_owner ON vehicles(owner_name);
CREATE INDEX IF NOT EXISTS idx_vehicle_type  ON vehicles(vehicle_type);

-- ── Reservations ───────────────────────────────────────────
CREATE TABLE IF NOT EXISTS reservations (
    reservation_id        VARCHAR(36)    NOT NULL,
    spot_id               VARCHAR(36)    NOT NULL,
    vehicle_license_plate VARCHAR(20)    NOT NULL,
    start_time            TIMESTAMP      NOT NULL,
    end_time              TIMESTAMP,
    status                VARCHAR(20)    NOT NULL DEFAULT 'PENDING',
    total_amount          NUMERIC(10,2),
    check_in_time         TIMESTAMP,
    check_out_time        TIMESTAMP,
    qr_code_data          VARCHAR(500),
    notes                 VARCHAR(500),
    created_at            TIMESTAMP      NOT NULL DEFAULT NOW(),
    updated_at            TIMESTAMP,
    CONSTRAINT pk_reservations        PRIMARY KEY (reservation_id),
    CONSTRAINT fk_res_spot            FOREIGN KEY (spot_id)
                                      REFERENCES parking_spots(spot_id),
    CONSTRAINT fk_res_vehicle         FOREIGN KEY (vehicle_license_plate)
                                      REFERENCES vehicles(license_plate),
    CONSTRAINT chk_reservation_status CHECK (status IN ('PENDING','ACTIVE','COMPLETED','CANCELLED'))
);

CREATE INDEX IF NOT EXISTS idx_res_spot_status ON reservations(spot_id, status);
CREATE INDEX IF NOT EXISTS idx_res_license     ON reservations(vehicle_license_plate);
CREATE INDEX IF NOT EXISTS idx_res_start_time  ON reservations(start_time);

-- ── Payments ───────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS payments (
    payment_id            VARCHAR(36)   NOT NULL,
    reservation_id        VARCHAR(36)   NOT NULL,
    amount                NUMERIC(10,2) NOT NULL CHECK (amount >= 0),
    payment_method        VARCHAR(50)   NOT NULL,
    status                VARCHAR(20)   NOT NULL DEFAULT 'PENDING',
    payment_time          TIMESTAMP,
    transaction_reference VARCHAR(100),
    receipt_number        VARCHAR(50),
    created_at            TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at            TIMESTAMP,
    CONSTRAINT pk_payments          PRIMARY KEY (payment_id),
    CONSTRAINT fk_payment_res       FOREIGN KEY (reservation_id)
                                    REFERENCES reservations(reservation_id),
    CONSTRAINT chk_payment_method   CHECK (payment_method IN ('CASH','CARD','MOBILE','ONLINE')),
    CONSTRAINT chk_payment_status   CHECK (status IN ('PENDING','COMPLETED','FAILED','REFUNDED'))
);

CREATE INDEX IF NOT EXISTS idx_payment_reservation ON payments(reservation_id);
CREATE INDEX IF NOT EXISTS idx_payment_status      ON payments(status);
