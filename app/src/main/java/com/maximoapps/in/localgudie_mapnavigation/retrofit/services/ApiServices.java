package com.maximoapps.in.localgudie_mapnavigation.retrofit.services;

import com.maximoapps.in.localgudie_mapnavigation.model.GooglePlacesAPIResponse;
import com.maximoapps.in.localgudie_mapnavigation.model.GooglePlacesResponseResults;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ${ChandraMohanReddy} on 1/21/2017.
 */

public interface ApiServices {

    // Google places api : /*https://maps.googleapis.com/maps/api/place/nearbysearch/json?
    // location=-33.8670522,151.1957362&
    // radius=5000&
    // type=restaurant&
    // keyword=food&
    // key=AIzaSyDPkQUjqZmZDhSk5RdUGeP7We4hXw_nox4*/

    @GET("json")
    Call<GooglePlacesAPIResponse<List<GooglePlacesResponseResults>>> googlePlaces(@Query("location") String location,
                                                                                  @Query("rankby") String rankby,
                                                                                  @Query("type") String type,
                                                                                  @Query("keyword") String keyword,
                                                                                  @Query("key") String key,
                                                                                  @Query("language") String lan,
                                                                                  @Query("pagetoken") String pagetoken);

    @GET("json")
    Call<GooglePlacesAPIResponse<List<GooglePlacesResponseResults>>> googlePlaceDetails(
                                                                                  @Query("place_id") String placeId);

}
