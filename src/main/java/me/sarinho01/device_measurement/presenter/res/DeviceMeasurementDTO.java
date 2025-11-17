package me.sarinho01.device_measurement.presenter.res;

import me.sarinho01.device_measurement.DeviceMeasurement;

import java.time.LocalDateTime;

public record DeviceMeasurementDTO(
        Long id,
        Integer deviceId,
        LocalDateTime timestamp,
        Double temperature,
        Double humidity,
        Double pressure
) {
    public static DeviceMeasurementDTO toDTO(DeviceMeasurement deviceMeasurement) {
        if (deviceMeasurement == null) {
            return null;
        }

        return new DeviceMeasurementDTO(
                deviceMeasurement.getId(),
                deviceMeasurement.getDeviceId(),
                deviceMeasurement.getTimestamp(),
                deviceMeasurement.getTemperature(),
                deviceMeasurement.getHumidity(),
                deviceMeasurement.getPressure()
        );
    }
}
