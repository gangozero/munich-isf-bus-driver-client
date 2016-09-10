package io.gangozero.isfdriver.presenters;

import io.gangozero.isfdriver.MapUtils;
import io.gangozero.isfdriver.managers.RoutesManager;
import io.gangozero.isfdriver.models.RoutePoint;
import io.gangozero.isfdriver.views.DriverDashboardView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by eleven on 10/09/2016.
 */
public class DriverDashboardPresenter {

	public @Inject RoutesManager routesManager;
	
	private DriverDashboardView view;

	public void onViewCreated(DriverDashboardView view) {
		this.view = view;
		routesManager
				.getRouteWayPoints("006")
				.flatMap(wayPoints -> routesManager.getFullRoute(wayPoints))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(response -> {

					List<RoutePoint> result = MapUtils.responseToPoints(response);

					view.showRoute(result);
				}, view::showErrorRouteLoading);
	}


}
