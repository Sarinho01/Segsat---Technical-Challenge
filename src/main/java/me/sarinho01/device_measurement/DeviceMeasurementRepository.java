package me.sarinho01.device_measurement;

import me.sarinho01.device_measurement.presenter.req.DeviceMeasurementSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DeviceMeasurementRepository extends JpaRepository<DeviceMeasurement, Long> {
    @Query("""
			    SELECT dm FROM DeviceMeasurement dm
			    WHERE
			    (:#{#searchDTO.deviceId()} IS NULL OR :#{#searchDTO.deviceId()} = dm.deviceId)
			""")
    Page<DeviceMeasurement> findAllUsingFilter(@Param("searchDTO") DeviceMeasurementSearchDTO searchDTO,
                                                 Pageable pageable);
}
