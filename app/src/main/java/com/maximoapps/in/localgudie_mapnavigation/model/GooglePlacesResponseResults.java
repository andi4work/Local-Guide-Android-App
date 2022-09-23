package com.maximoapps.in.localgudie_mapnavigation.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ${ChandraMohanReddy} on 1/21/2017.
 */

public class GooglePlacesResponseResults {


    @SerializedName("name")
    public String name;

    @SerializedName("icon")
    public String icon;

    @SerializedName("photos")
    public List<Photo> photos;

    @SerializedName("geometry")
    private Geometry geometry;

    @SerializedName("place_id")
    public String place_id;

    @SerializedName("rating")
    public String rating;

    @SerializedName("vicinity")
    public String vicinity;

    public GooglePlacesResponseResults(String name, List<Photo> photo, Geometry geometry, String place_id, String rating, String address) {
        this.name = name;
        this.photos = photo;
        this.geometry = geometry;
        this.place_id = place_id;
        this.rating = rating;
        this.vicinity = address;

    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }


    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

}
