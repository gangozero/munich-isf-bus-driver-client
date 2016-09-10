package io.gangozero.isfdriver.managers;

import io.gangozero.isfdriver.models.GoogleRouteResponse;
import io.gangozero.isfdriver.models.RoutePoint;
import rx.Observable;

import java.util.List;

/**
 * Created by eleven on 10/09/2016.
 */
public class RoutesManagerImpl implements RoutesManager {

	public RestService restService;
	public GoogleMapsDirections googleMapsDirections;

	public RoutesManagerImpl(RestService restService, GoogleMapsDirections googleMapsDirections) {
		this.restService = restService;
		this.googleMapsDirections = googleMapsDirections;
	}

	@Override public Observable<List<RoutePoint>> getRouteWayPoints(String routeId) {
		return restService.getRoutes(routeId);
	}

	@Override public Observable<GoogleRouteResponse> getFullRoute(List<RoutePoint> routePoints) {
		RoutePoint start = routePoints.remove(0);
		RoutePoint end = routePoints.remove(routePoints.size() - 1);
		String wayPoints = "";

		for (int i = 0; i < routePoints.size(); i++) {
			RoutePoint point = routePoints.get(i);
			wayPoints += point.lat + "," + point.lon + "|";
		}

		return googleMapsDirections.getFullRoute(
				start.lat + "," + start.lon,
				end.lat + "," + end.lon,
				wayPoints
		);
	}

}
