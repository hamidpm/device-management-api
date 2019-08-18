package com.gunnebo.test.devicemanagementapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.gunnebo.test.devicemanagementapi.model.Device;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DeviceManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class DeviceManagementApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void contextLoads() {

	}

	@Test
	public void testGetAllDevices() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/devices",
				HttpMethod.GET, entity, String.class);
		assertNotNull(response.getBody());
	}

	@Test
	public void testGetDeviceById() {
		Device device = restTemplate.getForObject(getRootUrl() + "/devices/1", Device.class);
		System.out.println(device.getTenant());
		assertNotNull(device);
	}

	@Test
	public void testCreateDevice() {
		Device device = new Device();
		device.setType("Gate");
		device.setTenant("H&M");
		ResponseEntity<Device> postResponse = restTemplate.postForEntity(getRootUrl() + "/devices", device, Device.class);
		assertNotNull(postResponse);
		assertNotNull(postResponse.getBody());
	}

	@Test
	public void testUpdateDevice() {
		int id = 1;
		Device device = restTemplate.getForObject(getRootUrl() + "/devices/" + id, Device.class);
		device.setType("admin1");
		device.setTenant("admin2");
		restTemplate.put(getRootUrl() + "/devices/" + id, device);
		Device updatedDevice = restTemplate.getForObject(getRootUrl() + "/devices/" + id, Device.class);
		assertNotNull(updatedDevice);
	}

	@Test
	public void testSearchDevices() {
		Device device1 = new Device();
		device1.setType("Gate");
		device1.setTenant("H&M");
		ResponseEntity<Device> postResponse1 = restTemplate.postForEntity(getRootUrl() + "/devices", device1, Device.class);

		Device device2 = new Device();
		device2.setType("Camera");
		device2.setTenant("Coop");
		ResponseEntity<Device> postResponse2 = restTemplate.postForEntity(getRootUrl() + "/devices", device2, Device.class);

		String field = "cam";
		ResponseEntity<Device> postResponse = restTemplate.postForEntity(getRootUrl() + "/devices/search?field=" + field,null, Device.class);
		assertNotNull(postResponse);
		assertNotNull(postResponse.getBody());
	}

	@Test
	public void testDeleteDevice() {
		int id = 2;
		Device device = restTemplate.getForObject(getRootUrl() + "/devices/" + id, Device.class);
		assertNotNull(device);
		restTemplate.delete(getRootUrl() + "/devices/" + id);
		try {
			device = restTemplate.getForObject(getRootUrl() + "/devices/" + id, Device.class);
		} catch (final HttpClientErrorException e) {
			assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}
}
