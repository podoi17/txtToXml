package com.company;

import java.util.List;

public class Denkmal {

    private String id;
    private String district;
    private String postal;
    private List<String> streets;
    private String discription;

    public Denkmal(String id, String district, String postal, List<String> streets, String discription) {
        this.id = id;
        this.district = district;
        this.postal = postal;
        this.streets = streets;
        this.discription = discription;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public List<String> getStreets() {
        return streets;
    }

    public void setStreets(List<String> streets) {
        this.streets = streets;
    }

    @Override
    public String toString() {
        return "Denkmal{" +
                "id='" + id + '\'' +
                ", district='" + district + '\'' +
                ", postal='" + postal + '\'' +
                ", streets=" + streets +
                ", discription='" + discription + '\'' +
                '}';
    }
}
