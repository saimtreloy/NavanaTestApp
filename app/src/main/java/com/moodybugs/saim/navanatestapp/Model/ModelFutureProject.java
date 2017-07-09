package com.moodybugs.saim.navanatestapp.Model;

/**
 * Created by Android on 7/9/2017.
 */

public class ModelFutureProject {
    public String id, name, type, status, size, city, area_name, address, longitude, latitude, image;

    public ModelFutureProject(String id, String name, String type, String status, String size, String city, String area_name, String address, String longitude, String latitude, String image) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
        this.size = size;
        this.city = city;
        this.area_name = area_name;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getSize() {
        return size;
    }

    public String getCity() {
        return city;
    }

    public String getArea_name() {
        return area_name;
    }

    public String getAddress() {
        return address;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getImage() {
        return image;
    }
}
