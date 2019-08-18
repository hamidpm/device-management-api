package com.gunnebo.test.devicemanagementapi.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.gunnebo.test.devicemanagementapi.exception.ResourceNotFoundException;
import com.gunnebo.test.devicemanagementapi.model.Device;
import com.gunnebo.test.devicemanagementapi.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class DeviceController {
    @Autowired
    private DeviceRepository deviceRepository;

    @GetMapping("/devices")
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    @GetMapping("/devices/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable(value = "id") Long deviceId)
            throws ResourceNotFoundException {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found for this id :: " + deviceId));
        return ResponseEntity.ok().body(device);
    }

    @PostMapping("/devices")
    public Device createDevice(@Valid @RequestBody Device device) {
        return deviceRepository.save(device);
    }

    @PutMapping("/devices/{id}")
    public ResponseEntity<Device> updateDevice(@PathVariable(value = "id") Long deviceId,
                                               @Valid @RequestBody Device deviceDetails) throws ResourceNotFoundException {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found for this id :: " + deviceId));

        device.setType(deviceDetails.getType());
        device.setTenant(deviceDetails.getTenant());
        final Device updatedDevice = deviceRepository.save(device);
        return ResponseEntity.ok(updatedDevice);
    }

    @PostMapping(value = "/devices/search")
    public List<Device> searchDevices(@RequestParam String field) {
        return deviceRepository.findDevicesByTypeContainingOrTenantContaining(field, field);
    }

    @DeleteMapping("/devices/{id}")
    public Map<String, Boolean> deleteDevice(@PathVariable(value = "id") Long deviceId)
            throws ResourceNotFoundException {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found for this id :: " + deviceId));

        deviceRepository.delete(device);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
