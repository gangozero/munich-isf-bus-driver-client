package io.gangozero.isfdriver.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import io.gangozero.isfdriver.R;
import io.gangozero.isfdriver.adapters.NotificationsAdapter;
import io.gangozero.isfdriver.di.DIHelper;
import io.gangozero.isfdriver.models.NotificationModel;
import io.gangozero.isfdriver.models.RoutePoint;
import io.gangozero.isfdriver.presenters.DashboardPresenter;
import io.gangozero.isfdriver.views.DriverDashboardView;
import io.gangozero.isfdriver.views.NotificationView;

import java.util.List;

/**
 * Created by eleven on 09/09/2016.
 */
public class DashboardFragment extends MapFragment implements DriverDashboardView {

	@BindView(R.id.btn_load_route) Button btnLoadRoute;
	@BindView(R.id.btn_start_route) Button btnStartRoute;
	@BindView(R.id.btn_reset) Button btnReset;
	@BindView(R.id.notifications_view) NotificationView notificationView;

	private STATE state = STATE.WAITING_FOR_LOC;
	private Unbinder binder;
	private DashboardPresenter presenter;
	private NotificationsAdapter notificationsAdapter;

	public static DashboardFragment create() {
		return new DashboardFragment();
	}

	@Override public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		presenter = new DashboardPresenter();
		notificationsAdapter = new NotificationsAdapter(getContext());
		DIHelper.getCoreComponent().inject(presenter);
	}

	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	@OnClick(R.id.btn_load_route) public void loadRoute() {
		updateState(STATE.LOADING_ROUTE);
		presenter.loadRoute();
	}

	@OnClick(R.id.btn_start_route) public void startRoute() {
		presenter.startRoute();
	}

	@OnClick(R.id.btn_reset) public void reset() {
		presenter.loadCurrentLocation();
		super.removeRoute();
		updateState(STATE.IDLE);
	}

	@Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View result = super.onCreateView(inflater, container, savedInstanceState);
		binder = ButterKnife.bind(this, result);
		notificationView.setAdapter(notificationsAdapter);
		return result;
	}

	@Override protected void onMapCreated(GoogleMap googleMap) {
		super.onMapCreated(googleMap);
		updateState(STATE.WAITING_FOR_LOC);
		presenter.onViewCreated(this);
		presenter.loadCurrentLocation();
	}

	@Override public void onDestroyView() {
		binder.unbind();
		super.onDestroyView();
	}

	@Override public void showRoute(List<RoutePoint> routePoints) {
		super.showRoute(routePoints);
		updateState(DashboardFragment.STATE.ROUTE_LOADED);
	}

	@Override public void showErrorRouteLoading(Throwable throwable) {
		btnLoadRoute.setEnabled(true);
		Toast.makeText(getContext(), "Route loading error", Toast.LENGTH_LONG).show();
		throwable.printStackTrace();
	}

	@Override public void errorLoadingLocation(Throwable throwable) {

	}

	@Override public void routeStarted() {
		showRouteView();
		updateState(STATE.IN_ROUTE);
	}

	@Override public void handleNotification(NotificationModel model) {
		notificationsAdapter.add(model);
	}

	@Override public void handleErrorNotification(Throwable throwable) {

	}

	@Override public void showCurrentLocation(LatLng latLng) {
		updateState(DashboardFragment.STATE.IDLE);
		super.showCurrentLocation(latLng);
	}

	private void updateState(STATE state) {

		if (state == STATE.WAITING_FOR_LOC) {
			btnStartRoute.setEnabled(false);
			btnLoadRoute.setEnabled(false);
			btnReset.setEnabled(false);
		} else if (state == STATE.IDLE) {
			btnStartRoute.setEnabled(false);
			btnLoadRoute.setEnabled(true);
			btnReset.setEnabled(false);
		} else if (state == STATE.LOADING_ROUTE) {
			btnStartRoute.setEnabled(false);
			btnLoadRoute.setEnabled(false);
			btnReset.setEnabled(false);
		} else if (state == STATE.ROUTE_LOADED) {
			btnStartRoute.setEnabled(true);
			btnLoadRoute.setEnabled(false);
			btnReset.setEnabled(true);
		} else if (state == STATE.IN_ROUTE) {
			btnStartRoute.setEnabled(false);
			btnLoadRoute.setEnabled(false);
			btnReset.setEnabled(true);
		}

		this.state = state;
	}

	enum STATE {
		WAITING_FOR_LOC,
		IDLE,
		LOADING_ROUTE,
		ROUTE_LOADED,
		IN_ROUTE
	}
}
