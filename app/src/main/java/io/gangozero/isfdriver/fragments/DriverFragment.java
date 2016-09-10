package io.gangozero.isfdriver.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import io.gangozero.isfdriver.R;
import io.gangozero.isfdriver.di.DIHelper;
import io.gangozero.isfdriver.models.RoutePoint;
import io.gangozero.isfdriver.presenters.DriverDashboardPresenter;
import io.gangozero.isfdriver.views.DriverDashboardView;

import java.util.List;

/**
 * Created by eleven on 09/09/2016.
 */
public class DriverFragment extends Fragment implements DriverDashboardView {

	@BindView(R.id.map_view) MapView mapView;

	private Unbinder binder;
	private DriverDashboardPresenter presenter;
	private GoogleMap googleMap;

	public static DriverFragment create() {
		return new DriverFragment();
	}

	public DriverFragment() {
		setRetainInstance(true);
	}

	@Override public void onResume() {
		super.onResume();
		mapView.onResume();
	}

	@Override public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		presenter = new DriverDashboardPresenter();
		DIHelper.getCoreComponent().inject(presenter);
	}

	@Override public void onPause() {
		super.onPause();
		mapView.onPause();
	}

	@Override public void onLowMemory() {
		super.onLowMemory();
		mapView.onLowMemory();
	}

	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	@Override public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	@Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View result = inflater.inflate(R.layout.fragment_layout, container, false);
		binder = ButterKnife.bind(this, result);
		MapsInitializer.initialize(getContext());
		mapView.onCreate(savedInstanceState);
		mapView.getMapAsync(this::onMapCreated);
		return result;
	}

	private void onMapCreated(GoogleMap googleMap) {
		this.googleMap = googleMap;
		presenter.onViewCreated(this);
	}

	@Override public void onDestroyView() {
		mapView.onDestroy();
		binder.unbind();
		super.onDestroyView();
	}

	@Override public void showRoute(List<RoutePoint> routePoints) {
		Toast.makeText(getContext(), "Route loaded:" + routePoints.size(), Toast.LENGTH_LONG).show();
		PolylineOptions polylineOpts = new PolylineOptions();

		for (int i = 0; i < routePoints.size(); i++) {
			polylineOpts.add(new LatLng(routePoints.get(i).lat, routePoints.get(i).lon));
		}

		googleMap.addPolyline(polylineOpts);
	}

	@Override public void showErrorRouteLoading(Throwable throwable) {
		Toast.makeText(getContext(), "Route loading error", Toast.LENGTH_LONG).show();
		throwable.printStackTrace();
		if (throwable == null) {

		}
	}
}
