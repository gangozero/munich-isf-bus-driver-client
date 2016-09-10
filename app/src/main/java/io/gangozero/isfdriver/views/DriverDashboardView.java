package io.gangozero.isfdriver.views;

import com.google.android.gms.maps.model.LatLng;
import io.gangozero.isfdriver.models.NotificationModel;
import io.gangozero.isfdriver.models.RoutePoint;

import java.util.List;

/**
 * Created by eleven on 10/09/2016.
 */
public interface DriverDashboardView {

	void showRoute(List<RoutePoint> routePoints);

	void showErrorRouteLoading(Throwable throwable);

	void showCurrentLocation(LatLng latLng);

	void errorLoadingLocation(Throwable throwable);

	void routeStarted();

	void handleNotification(NotificationModel notificationModel);

	void handleErrorNotification(Throwable throwable);
}
