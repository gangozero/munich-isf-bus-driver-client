package io.gangozero.isfdriver.models;

import java.util.List;

/**
 * Created by eleven on 10/09/2016.
 */
public class GoogleRouteResponse {
	public List<Object> geocoded_waypoints;
	public List<Route> routes;

	public static class Route {
		public List<Leg> legs;

	}

	public static class Polyline {
		public String points;
	}

	public static class Leg {
		public Loc start_location;
		public Loc end_location;
		public List<Step> steps;
	}

	public static class Step {
		public Polyline polyline;
	}

	public static class Loc {
		public double lat;
		public double lng;
	}
}
