package me.sarinho01.device_measurement.presenter.consumer;

public record DeviceMeasurementEventDTO(
        Integer deviceId,

        Double temperature,
        Double humidity,
        Double pressure
) {
}
