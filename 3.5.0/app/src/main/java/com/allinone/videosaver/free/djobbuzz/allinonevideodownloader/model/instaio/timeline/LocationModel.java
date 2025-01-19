package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.timeline;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LocationModel implements Serializable {
    @SerializedName("address")
    private String address;
    @SerializedName("city")
    private String city;
    @SerializedName("external_source")
    private String external_source;
    @SerializedName("facebook_places_id")
    private long facebook_places_id;
    @SerializedName("lat")
    private double lat;
    @SerializedName("lng")
    private double lng;
    @SerializedName("name")
    private String name;
    @SerializedName("pk")
    private long pk;
    @SerializedName("short_name")
    private String short_name;

    public long getPk() {
        return this.pk;
    }

    public void setPk(long j) {
        this.pk = j;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String str) {
        this.address = str;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String str) {
        this.city = str;
    }

    public String getShort_name() {
        return this.short_name;
    }

    public void setShort_name(String str) {
        this.short_name = str;
    }

    public double getLng() {
        return this.lng;
    }

    public void setLng(double d) {
        this.lng = d;
    }

    public double getLat() {
        return this.lat;
    }

    public void setLat(double d) {
        this.lat = d;
    }

    public String getExternal_source() {
        return this.external_source;
    }

    public void setExternal_source(String str) {
        this.external_source = str;
    }

    public long getFacebook_places_id() {
        return this.facebook_places_id;
    }

    public void setFacebook_places_id(long j) {
        this.facebook_places_id = j;
    }
}
