CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


CREATE TABLE IF NOT EXISTS reservations (
    id UUID NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
    arrival_date DATE NOT NULL,
    departure_date DATE NOT NULL,
    user_name VARCHAR(50) NOT NULL,
    user_email VARCHAR(50) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE
    );

CREATE INDEX IF NOT EXISTS idx_reservations_arrival_date ON reservations (arrival_date);
CREATE INDEX IF NOT EXISTS idx_reservations_departure_date ON reservations (departure_date);