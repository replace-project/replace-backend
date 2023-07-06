package com.replace.re.place.store.dao;

public class Store {
    private long stored_id;
    private String name;
    private String latitude;
    private String longitude;
    private String address;


    public long getStored_id() {
        return stored_id;
    }

    public void setStored_id(long stored_id) {
        this.stored_id = stored_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
