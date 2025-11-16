package me.sarinho01.device_measurement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.sarinho01.device_measurement.presenter.consumer.DeviceMeasurementEventDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.BeanUtils;
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

        if(eventDTO.deviceId() < 0){
            throw new RuntimeException("Invalid device id");
        }

        if(eventDTO.humidity() < 0){
            throw new RuntimeException("Invalid humidity");
        }

        if(eventDTO.temperature() < -273){
            throw new RuntimeException("Invalid temperature");
        }

        if(eventDTO.pressure() < 0 ){
            throw new RuntimeException("Invalid pressure");
        }

        DeviceMeasurement deviceMeasurement =  new DeviceMeasurement();
        deviceMeasurement.setTimestamp(kafkaTimestamp);
        BeanUtils.copyProperties(eventDTO, deviceMeasurement);

        DeviceMeasurement savedDeviceMeasurement = deviceMeasurementRepository.save(deviceMeasurement);
        log.info("Device measurement saved : {}", savedDeviceMeasurement.getId());
    }
}
