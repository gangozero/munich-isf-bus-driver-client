package io.gangozero.isfdriver.models;

/**
 * Created by eleven on 10/09/2016.
 */
public class RoutePoint {
	public String type;
	public String name;
	public double lat;
	public double lon;

	public RoutePoint(double lat, double lon) {
		this.lat = lat;
		this.lon = lon;
	}
}