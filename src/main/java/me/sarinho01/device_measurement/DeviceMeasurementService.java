package me.sarinho01.device_measurement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.sarinho01.device_measurement.presenter.consumer.DeviceMeasurementEventDTO;
import me.sarinho01.device_measurement.presenter.req.DeviceMeasurementSearchDTO;
import me.sarinho01.device_measurement.presenter.res.DeviceMeasurementDTO;
import me.sarinho01.device_measurement.presenter.res.SearchResultDeviceMeasurementDTO;
import me.sarinho01.exception_handler.BasicHttpException;
import me.sarinho01.kafka.KafkaConsumerException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeviceMeasurementService {
    private final DeviceMeasurementRepository deviceMeasurementRepository;

    @KafkaListener(
            topics = "device-measurement-topic",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeDeviceMeasurement(ConsumerRecord<String, DeviceMeasurementEventDTO> consumerRecord) {
        DeviceMeasurementEventDTO eventDTO = consumerRecord.value();
        LocalDateTime kafkaTimestamp = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(consumerRecord.timestamp()),
                ZoneId.systemDefault()
        );

        if (eventDTO.deviceId() < 0) {
            throw new KafkaConsumerException("Invalid device id");
        }

        if (eventDTO.humidity() < 0) {
            throw new KafkaConsumerException("Invalid humidity");
        }

        if (eventDTO.temperature() < -273) {
            throw new KafkaConsumerException("Invalid temperature");
        }

        if (eventDTO.pressure() < 0) {
            throw new KafkaConsumerException("Invalid pressure");
        }

        DeviceMeasurement deviceMeasurement = new DeviceMeasurement();
        deviceMeasurement.setTimestamp(kafkaTimestamp);
        BeanUtils.copyProperties(eventDTO, deviceMeasurement);

        DeviceMeasurement savedDeviceMeasurement = deviceMeasurementRepository.save(deviceMeasurement);
        log.info("Device measurement saved : {}", savedDeviceMeasurement.getId());
    }

    public DeviceMeasurementDTO findDeviceMeasurementById(Long id) {
        DeviceMeasurement deviceMeasurement = deviceMeasurementRepository.findById(id)
                .orElseThrow(() -> new BasicHttpException("DEVICE MEASUREMENT EXCEPTION", "Device measurement id not found",
                        HttpStatus.NOT_FOUND));

        return DeviceMeasurementDTO.toDTO(deviceMeasurement);
    }

    public SearchResultDeviceMeasurementDTO searchDeviceMeasurement(DeviceMeasurementSearchDTO searchDTO) {
        Page<DeviceMeasurement> page = executePageableSearch(searchDTO, 10);

        return new SearchResultDeviceMeasurementDTO(
                page.getTotalElements(),
                page.getTotalPages(),
                page.stream().map(DeviceMeasurementDTO::toDTO).toList()
        );
    }

    public Page<DeviceMeasurement> executePageableSearch(DeviceMeasurementSearchDTO searchDTO, Integer itemsPage) {
        Pageable pageable = PageRequest.of(searchDTO.page(), itemsPage);
        return deviceMeasurementRepository.findAllUsingFilter(searchDTO, pageable);
    }


}
