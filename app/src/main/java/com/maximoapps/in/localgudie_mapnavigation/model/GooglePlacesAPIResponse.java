package com.maximoapps.in.localgudie_mapnavigation.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${ChandraMohanReddy} on 1/21/2017.
 */

public class GooglePlacesAPIResponse<T> {

    @SerializedName("results")
    public T results;

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }


    @SerializedName("next_page_token")
    public String next_page_token;



    public String getNext_page_token() {
        return next_page_token;
    }

    public void setNext_page_token(String next_page_token) {
        this.next_page_token = next_page_token;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @SerializedName("status")
    public String status;
}
