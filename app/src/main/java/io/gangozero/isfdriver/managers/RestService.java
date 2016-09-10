package io.gangozero.isfdriver.managers;

import io.gangozero.isfdriver.models.RoutePoint;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

import java.util.List;

/**
 * Created by eleven on 10/09/2016.
 */
public interface RestService {
	@GET("prod/get/route/{routeId}")
	Observable<List<RoutePoint>> getRoutes(@Path("routeId") String routeId);
}
