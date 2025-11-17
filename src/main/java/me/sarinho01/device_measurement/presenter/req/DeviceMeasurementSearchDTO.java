package me.sarinho01.device_measurement.presenter.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

public record DeviceMeasurementSearchDTO(
        @Min(value = 0, message = "Page cannot be negative")
        Integer page,

        @Positive(message = "Device ID must be greater than 0")
        Long deviceId
) {
}
