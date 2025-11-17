package me.sarinho01.device_measurement;

import me.sarinho01.device_measurement.presenter.consumer.DeviceMeasurementEventDTO;
import me.sarinho01.kafka.KafkaConsumerException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeviceMeasurementTest {
    @Mock
    private DeviceMeasurementRepository deviceMeasurementRepository;

    @InjectMocks
    private DeviceMeasurementService deviceMeasurementService;

    @Test
    void consumeKafkaTopicSaveMeasurementSuccessfully() {
        DeviceMeasurementEventDTO eventDTO = new DeviceMeasurementEventDTO(
                1,
                25.5,
                60.0,
                50.25
        );

        long kafkaTimestamp = System.currentTimeMillis();

        ConsumerRecord<String, DeviceMeasurementEventDTO> consumerRecord =
                new ConsumerRecord<>("device-measurement-topic", 0, 0, "key", eventDTO);

        consumerRecord.headers().add("timestamp", String.valueOf(kafkaTimestamp).getBytes());

        LocalDateTime expectedTimestamp = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(consumerRecord.timestamp()),
                ZoneId.systemDefault()
        );

        DeviceMeasurement expected = new DeviceMeasurement();
        expected.setTimestamp(expectedTimestamp);
        BeanUtils.copyProperties(eventDTO, expected);

        when(deviceMeasurementRepository.save(any(DeviceMeasurement.class)))
                .thenReturn(expected);

        deviceMeasurementService.consumeDeviceMeasurement(consumerRecord);

        ArgumentCaptor<DeviceMeasurement> captor = ArgumentCaptor.forClass(DeviceMeasurement.class);
        verify(deviceMeasurementRepository).save(captor.capture());

        DeviceMeasurement actual = captor.getValue();

        assertEquals(expected, actual);
    }

    @Test
    void consumeKafkaTopicThrowExceptionWhenInvalidTemperature() {
        DeviceMeasurementEventDTO eventDTO = new DeviceMeasurementEventDTO(
                1, -300.0, 50.0, 50.0
        );

        ConsumerRecord<String, DeviceMeasurementEventDTO> consumerRecord =
                new ConsumerRecord<>("topic", 0, 0, "k", eventDTO);

        assertThrows(KafkaConsumerException.class, () -> deviceMeasurementService.consumeDeviceMeasurement(consumerRecord));

        verify(deviceMeasurementRepository, never()).save(any());
    }

    @Test
    void consumeKafkaTopicThrowExceptionWhenInvalidHumidity() {
        DeviceMeasurementEventDTO eventDTO = new DeviceMeasurementEventDTO(
                1, 25.0, -50.0, 50.0
        );

        ConsumerRecord<String, DeviceMeasurementEventDTO> consumerRecord =
                new ConsumerRecord<>("topic", 0, 0, "k", eventDTO);

        assertThrows(KafkaConsumerException.class, () -> deviceMeasurementService.consumeDeviceMeasurement(consumerRecord));

        verify(deviceMeasurementRepository, never()).save(any());
    }

    @Test
    void consumeKafkaTopicThrowExceptionWhenInvaliPressure() {
        DeviceMeasurementEventDTO eventDTO = new DeviceMeasurementEventDTO(
                1, 25.0, 50.0, -50.0
        );

        ConsumerRecord<String, DeviceMeasurementEventDTO> consumerRecord =
                new ConsumerRecord<>("topic", 0, 0, "k", eventDTO);

        assertThrows(KafkaConsumerException.class, () -> deviceMeasurementService.consumeDeviceMeasurement(consumerRecord));

        verify(deviceMeasurementRepository, never()).save(any());
    }
}
