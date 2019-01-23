package com.android2ee.formation.librairies.google.map.utils.direction.com;

import com.android2ee.formation.librairies.google.map.utils.direction.model.GDirection;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Safi on 02/05/2017.
 * Updated by Anthony St. on 22/01/2019.
 */
public interface IGDirectionServer {

    @GET("maps/api/directions/json")
    Call<List<GDirection>> getGDirections(
            @Query("origin") String origin,                   //data.getStart().latitude + ","+ data.getStart().longitude
            @Query("destination") String destination,         //data.getEnd().latitude + "," + data.getEnd().longitude
            @Query("mode") String mode,                       //data.getMode()
            @Query("waypoints") String waypoints,             //data.getWaypoints()
            @Query("alternatives") boolean alternatives,      //data.isAlternative()
            @Query("avoid") String avoid,                     //data.getAvoid()
            @Query("language") String language,               //data.getLanguage()
            @Query("units") String units,                     //data.getUs()
            @Query("region") String region,                   //data.getRegion()
            @Query("departure_time") String departure_time,   //data.getDeparture_time()
            @Query("arrival_time") String arrival_time,       //data.getArrival_time()
            @Query("key") String googleApiKey                 //data.getGoogle_api_key()
    );

}
