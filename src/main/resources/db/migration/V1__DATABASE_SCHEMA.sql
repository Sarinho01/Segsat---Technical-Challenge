CREATE TABLE tb_device_measurement
(
    device_measurement_id UUID PRIMARY KEY     DEFAULT gen_random_uuid(),

    device_id             BIGSERIAL   NOT NULL,

    timestamp             TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    temperature           DOUBLE PRECISION,
    humidity              DOUBLE PRECISION,
    pressure              DOUBLE PRECISION,

    created_at            TIMESTAMPTZ NOT NULL DEFAULT NOW()
);