package com.android2ee.formation.librairies.google.map.utils.direction.com;


import com.android2ee.formation.librairies.google.map.utils.direction.model.GDirection;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Safi on 02/05/2017.
 */
public interface IGDirectionServer {
    public final String BASE_URL="http://maps.googleapis.com/";

    @GET("maps/api/directions/json?")
    Call<List<GDirection>> getGDirections(
            @Path("origin") String origin,//data.getStart().latitude + ","+ data.getStart().longitude
            @Path("destination") String destination,//data.getEnd().latitude + "," + data.getEnd().longitude
            @Path("sensor") boolean sensor,//"&sensor=false"
            @Path("mode") String mode,//data.getMode()
            @Path("waypoints") String waypoints,//data.getWaypoints()
            @Path("alternatives") boolean alternatives,//data.isAlternative()
            @Path("avoid") String avoid,//data.getAvoid()
            @Path("language") String language,//data.getLanguage()
            @Path("units") String units,//data.getUs()
            @Path("region") String region,//data.getRegion()
            @Path("departure_time") String departure_time,//data.getDeparture_time()
            @Path("arrival_time") String arrival_time//data.getArrival_time()

    );
}
