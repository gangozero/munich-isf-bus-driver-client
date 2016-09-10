package io.gangozero.isfdriver.managers;

import io.gangozero.isfdriver.models.GoogleRouteResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by eleven on 10/09/2016.
 */
public interface GoogleMapsDirections {

	@GET("maps/api/directions/json")
	Observable<GoogleRouteResponse> getFullRoute(
			@Query("origin") String origin,
			@Query("destination") String destination,
			@Query("waypoints") String waypoints
	);

}
