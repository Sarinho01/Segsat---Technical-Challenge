package me.sarinho01.device_measurement;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tb_device_measurement")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceMeasurement {
    @Id
    @GeneratedValue
    @Column(name = "device_measurement_id", nullable = false, updatable = false)
    private UUID id;

    @NotNull
    private Integer deviceId;

    @NotNull
    private LocalDateTime timestamp;

    @NotNull
    private Double temperature;

    @NotNull
    private Double humidity;

    @NotNull
    private Double pressure;

    @CreationTimestamp(source = SourceType.DB)
    private Date createdAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DeviceMeasurement that = (DeviceMeasurement) o;
        return Objects.equals(id, that.id) && Objects.equals(deviceId, that.deviceId) && Objects.equals(timestamp, that.timestamp) && Objects.equals(temperature, that.temperature) && Objects.equals(humidity, that.humidity) && Objects.equals(pressure, that.pressure);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, deviceId, timestamp, temperature, humidity, pressure);
    }

    @Override
    public String toString() {
        return "DeviceMeasurement{" +
                "id='" + id + '\'' +
                ", deviceId=" + deviceId +
                ", timestamp=" + timestamp +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", pressure=" + pressure +
                ", createdAt=" + createdAt +
                '}';
    }
}
