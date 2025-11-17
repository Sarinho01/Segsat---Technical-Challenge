CREATE TABLE tb_device_measurement
(
    device_measurement_id BIGSERIAL PRIMARY KEY,

    device_id             BIGSERIAL   NOT NULL,

    timestamp             TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    temperature           DOUBLE PRECISION,
    humidity              DOUBLE PRECISION,
    pressure              DOUBLE PRECISION,

    created_at            TIMESTAMPTZ NOT NULL DEFAULT NOW()
);