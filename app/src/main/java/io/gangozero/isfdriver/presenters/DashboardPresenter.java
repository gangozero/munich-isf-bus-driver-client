package io.gangozero.isfdriver.presenters;

import io.gangozero.isfdriver.managers.LocationManager;
import io.gangozero.isfdriver.managers.NotificationsManager;
import io.gangozero.isfdriver.managers.RoutesManager;
import io.gangozero.isfdriver.views.DriverDashboardView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import javax.inject.Inject;

import static io.gangozero.isfdriver.MapUtils.responseToPoints;

/**
 * Created by eleven on 10/09/2016.
 */
public class DashboardPresenter {

	public @Inject RoutesManager routesManager;
	public @Inject LocationManager locationManager;
	public @Inject NotificationsManager notificationsManager;

	private DriverDashboardView view;

	public void onViewCreated(DriverDashboardView view) {
		this.view = view;

		notificationsManager.notificationsObservable()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(view::handleNotification, view::handleErrorNotification);
	}

	public void loadCurrentLocation() {
		locationManager.currentLocation()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(view::showCurrentLocation, view::errorLoadingLocation);
	}

	public void loadRoute() {
		routesManager
				.getRouteWayPoints("006")
				.flatMap(wayPoints -> routesManager.getFullRoute(wayPoints))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(response -> view.showRoute(responseToPoints(response)), view::showErrorRouteLoading);
	}

	public void startRoute() {
		view.routeStarted();
	}
}
