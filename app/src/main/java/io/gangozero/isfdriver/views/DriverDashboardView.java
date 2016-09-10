package io.gangozero.isfdriver.views;

import io.gangozero.isfdriver.models.RoutePoint;

import java.util.List;

/**
 * Created by eleven on 10/09/2016.
 */
public interface DriverDashboardView {

	void showRoute(List<RoutePoint> routePoints);

	void showErrorRouteLoading(Throwable throwable);
}
