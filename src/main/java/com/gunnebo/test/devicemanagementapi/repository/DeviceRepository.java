package com.gunnebo.test.devicemanagementapi.repository;

import com.gunnebo.test.devicemanagementapi.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


@Repository
public interface DeviceRepository extends JpaRepository<Device, Long>{

    List<Device> findDevicesByTypeContainingOrTenantContaining(String type, String tenant);

}
