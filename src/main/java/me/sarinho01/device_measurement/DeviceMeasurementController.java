package me.sarinho01.device_measurement;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.sarinho01.device_measurement.presenter.req.DeviceMeasurementSearchDTO;
import me.sarinho01.device_measurement.presenter.res.DeviceMeasurementDTO;
import me.sarinho01.device_measurement.presenter.res.SearchResultDeviceMeasurementDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/device-measurement")
@RequiredArgsConstructor
public class DeviceMeasurementController {
    private final DeviceMeasurementService deviceMeasurementService;

    @GetMapping("/{id}")
    public ResponseEntity<DeviceMeasurementDTO> findDeviceMeasurementById(@PathVariable Long id) {
        DeviceMeasurementDTO response = deviceMeasurementService.findDeviceMeasurementById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<SearchResultDeviceMeasurementDTO> searchDeviceMeasurement(@Valid DeviceMeasurementSearchDTO searchDTO) {
        SearchResultDeviceMeasurementDTO response = deviceMeasurementService.searchDeviceMeasurement(searchDTO);
        return ResponseEntity.ok(response);
    }
}
