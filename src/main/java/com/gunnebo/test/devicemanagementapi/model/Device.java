package com.gunnebo.test.devicemanagementapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "device")
public class Device {

    private long id;
    private String type;
    private String tenant;

    public Device() {

    }

    public Device(String type, String tenant) {
        this.type = type;
        this.tenant = tenant;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "type", nullable = false)
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "tenant", nullable = false)
    public String getTenant() {
        return tenant;
    }
    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    @Override
    public String toString() {
        return "Device [id=" + id + ", type=" + type + ", tenant=" + tenant
                + "]";
    }

}
