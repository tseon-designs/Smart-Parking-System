-- ============================================================
-- Smart Parking System — Seed / Development Data
-- Run AFTER V1__init_schema.sql
-- ============================================================

-- Parking Lots
INSERT INTO parking_lots (parking_lot_id, name, total_spots, address, city, phone, active)
VALUES
    ('lot-001', 'Central Park Garage',  50, '123 Main Street',   'Addis Ababa', '+251911000001', TRUE),
    ('lot-002', 'Airport Parking Hub',  30, '456 Airport Road',  'Addis Ababa', '+251911000002', TRUE),
    ('lot-003', 'Bole Business Center', 20, '789 Bole Avenue',   'Addis Ababa', '+251911000003', TRUE)
ON CONFLICT (parking_lot_id) DO NOTHING;

-- Parking Spots (Lot 001 — 10 sample spots)
INSERT INTO parking_spots (spot_id, location, occupied, price_per_hour, parking_lot_id, spot_type, floor_level)
VALUES
    ('spot-001', 'A-01', FALSE, 15.00, 'lot-001', 'STANDARD',   1),
    ('spot-002', 'A-02', FALSE, 15.00, 'lot-001', 'STANDARD',   1),
    ('spot-003', 'A-03', TRUE,  15.00, 'lot-001', 'STANDARD',   1),
    ('spot-004', 'B-01', FALSE, 20.00, 'lot-001', 'COMPACT',    1),
    ('spot-005', 'B-02', FALSE, 25.00, 'lot-001', 'EV_CHARGING', 1),
    ('spot-006', 'C-01', FALSE, 15.00, 'lot-001', 'HANDICAPPED', 1),
    ('spot-007', 'D-01', FALSE, 18.00, 'lot-001', 'STANDARD',   2),
    ('spot-008', 'D-02', FALSE, 18.00, 'lot-001', 'STANDARD',   2),
    ('spot-009', 'E-01', FALSE, 30.00, 'lot-001', 'OVERSIZED',  2),
    ('spot-010', 'E-02', FALSE, 15.00, 'lot-001', 'STANDARD',   2)
ON CONFLICT (spot_id) DO NOTHING;

-- Parking Spots (Lot 002 — 5 sample spots)
INSERT INTO parking_spots (spot_id, location, occupied, price_per_hour, parking_lot_id, spot_type, floor_level)
VALUES
    ('spot-011', 'F-01', FALSE, 20.00, 'lot-002', 'STANDARD', 1),
    ('spot-012', 'F-02', FALSE, 20.00, 'lot-002', 'STANDARD', 1),
    ('spot-013', 'G-01', FALSE, 25.00, 'lot-002', 'COMPACT',  1),
    ('spot-014', 'G-02', TRUE,  20.00, 'lot-002', 'STANDARD', 1),
    ('spot-015', 'H-01', FALSE, 35.00, 'lot-002', 'EV_CHARGING', 1)
ON CONFLICT (spot_id) DO NOTHING;

-- Vehicles
INSERT INTO vehicles (license_plate, vehicle_type, owner_name, owner_email, owner_phone, make, model, color, year)
VALUES
    ('AA-12345', 'CAR',        'Tsion Fikru',   'tsion@example.com',   '+251911001001', 'Toyota', 'Corolla',  'White',  2020),
    ('AA-67890', 'CAR',        'Abebe Kebede',  'abebe@example.com',   '+251911001002', 'Hyundai','Elantra',  'Black',  2021),
    ('AA-11111', 'MOTORCYCLE', 'Hana Girma',    'hana@example.com',    '+251911001003', 'Honda',  'CB500',    'Blue',   2019),
    ('AA-22222', 'TRUCK',      'Dawit Solomon', 'dawit@example.com',   '+251911001004', 'Isuzu',  'NPR',      'White',  2018),
    ('AA-33333', 'CAR',        'Meron Tadesse', 'meron@example.com',   '+251911001005', 'Toyota', 'RAV4',     'Silver', 2022)
ON CONFLICT (license_plate) DO NOTHING;

-- Reservations (one COMPLETED, one ACTIVE)
INSERT INTO reservations (reservation_id, spot_id, vehicle_license_plate, start_time, end_time, status, total_amount, check_in_time, check_out_time)
VALUES
    ('res-001', 'spot-003', 'AA-12345',
     NOW() - INTERVAL '2 hours', NOW() - INTERVAL '30 minutes',
     'COMPLETED', 22.50,
     NOW() - INTERVAL '2 hours', NOW() - INTERVAL '30 minutes'),
    ('res-002', 'spot-014', 'AA-67890',
     NOW() - INTERVAL '1 hour', NULL,
     'ACTIVE', NULL,
     NOW() - INTERVAL '1 hour', NULL)
ON CONFLICT (reservation_id) DO NOTHING;

-- Payments
INSERT INTO payments (payment_id, reservation_id, amount, payment_method, status, payment_time, transaction_reference, receipt_number)
VALUES
    ('pay-001', 'res-001', 22.50, 'CARD', 'COMPLETED', NOW() - INTERVAL '30 minutes',
     'TXN-REF-001', 'REC-2024-001')
ON CONFLICT (payment_id) DO NOTHING;
