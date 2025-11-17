package me.sarinho01.device_measurement.presenter.res;

import java.util.List;

public record SearchResultDeviceMeasurementDTO(
        long totalItems,
        int totalPages,
        List<DeviceMeasurementDTO> items
) {
}
