package io.gangozero.isfdriver;

import com.google.android.gms.maps.model.LatLng;
import io.gangozero.isfdriver.models.GoogleRouteResponse;
import io.gangozero.isfdriver.models.RoutePoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eleven on 10/09/2016.
 */
public class MapUtils {
	public static String getDirectionsUrl(List<RoutePoint> routePoints) {

		RoutePoint start = routePoints.remove(0);
		RoutePoint end = routePoints.remove(routePoints.size() - 1);
		String result = "";
		result += "origin=" + start.lat + "," + start.lon;
		result += "&destination=" + end.lat + "," + end.lon;
		result += "waypoints=";
		for (int i = 0; i < routePoints.size(); i++) {
			RoutePoint point = routePoints.get(i);
			result += point.lat + "," + point.lon + "|";
		}
		return result;
	}

	public static List<LatLng> decodePoly(String encoded) {

		List<LatLng> poly = new ArrayList<>();
		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;

		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;

			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;

			LatLng p = new LatLng((((double) lat / 1E5)),
					(((double) lng / 1E5)));
			poly.add(p);
		}

		return poly;
	}

	public static List<RoutePoint> responseToPoints(GoogleRouteResponse response) {
		List<RoutePoint> result = new ArrayList<>();

		GoogleRouteResponse.Route route = response.routes.get(0);
		List<GoogleRouteResponse.Leg> legs = route.legs;

		for (GoogleRouteResponse.Leg leg : legs) {
			List<GoogleRouteResponse.Step> steps = leg.steps;

			for (GoogleRouteResponse.Step step : steps) {
				List<LatLng> latLngs = decodePoly(step.polyline.points);
				for (LatLng latLng : latLngs) {
					result.add(new RoutePoint(latLng.latitude, latLng.longitude));
				}
			}

		}
		return result;
	}
}
