package io.gangozero.isfdriver.presenters;

import io.gangozero.isfdriver.managers.LocationManager;
import io.gangozero.isfdriver.managers.MqttManager;
import io.gangozero.isfdriver.managers.NotificationsManager;
import io.gangozero.isfdriver.managers.RoutesManager;
import io.gangozero.isfdriver.views.DriverDashboardView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import javax.inject.Inject;

import static io.gangozero.isfdriver.MapUtils.responseToPoints;

/**
 * Created by eleven on 10/09/2016.
 */
public class DashboardPresenter {

	public @Inject RoutesManager routesManager;
	public @Inject LocationManager locationManager;
	public @Inject NotificationsManager notificationsManager;
	public @Inject MqttManager mqttManager;

	private DriverDashboardView view;
	private CompositeSubscription sub;

	public void onViewCreated(DriverDashboardView view) {
		this.view = view;
		sub = new CompositeSubscription();

		sub.add(mqttManager.getSubscription()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(view::handleNotification, view::handleErrorNotification));
	}

	public void loadCurrentLocation() {
		sub.add(locationManager.currentLocation()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(view::showCurrentLocation, view::errorLoadingLocation));
	}

	public void loadRoute() {
		sub.add(routesManager
				.getRouteWayPoints("006")
				.flatMap(wayPoints -> routesManager.getFullRoute(wayPoints))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(response -> view.showRoute(responseToPoints(response)), view::showErrorRouteLoading));
	}

	public void onViewDestroyed() {
		if (sub != null) {
			sub.unsubscribe();
		}
	}

	public void startRoute() {
		view.routeStarted();
	}
}
