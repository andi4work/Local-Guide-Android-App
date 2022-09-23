package com.maximoapps.in.localgudie_mapnavigation.model;


public class FavouritePlace {

    String placeName;
    String placeAddress;

    public FavouritePlace(String placeName, String placeAddress) {
        this.placeName = placeName;
        this.placeAddress = placeAddress;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

}
