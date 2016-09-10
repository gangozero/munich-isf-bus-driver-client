package io.gangozero.isfdriver.managers;

import io.gangozero.isfdriver.models.GoogleRouteResponse;
import io.gangozero.isfdriver.models.RoutePoint;
import rx.Observable;

import java.util.List;

/**
 * Created by eleven on 10/09/2016.
 */
public interface RoutesManager {
	Observable<List<RoutePoint>> getRouteWayPoints(String routeId);

	Observable<GoogleRouteResponse> getFullRoute(List<RoutePoint> route);
}
