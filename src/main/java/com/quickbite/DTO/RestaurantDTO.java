package com.quickbite.DTO;

public class RestaurantDTO {

    private Long id;
    private String name;
    private String location;
    private String cuisine;
    private String phone;

    public RestaurantDTO() {
    }

    public RestaurantDTO(Long id, String name, String location, String cuisine, String phone) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.cuisine = cuisine;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}