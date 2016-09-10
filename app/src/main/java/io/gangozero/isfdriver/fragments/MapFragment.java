package io.gangozero.isfdriver.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.*;
import io.gangozero.isfdriver.R;
import io.gangozero.isfdriver.models.RoutePoint;

import java.util.List;

/**
 * Created by eleven on 10/09/2016.
 */
public class MapFragment extends Fragment {

	protected MapView mapView;
	protected GoogleMap googleMap;
	private LatLng currentLoc;
	private Polyline currenyRoute;

	public MapFragment() {
		setRetainInstance(true);
	}

	public void showCurrentLocation(LatLng latLng) {
		currentLoc = latLng;

		MarkerOptions markerOpts = new MarkerOptions();
		markerOpts.position(latLng);
		markerOpts.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_directions_bus_white_24dp));

		googleMap.addMarker(markerOpts);

		googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(
				new CameraPosition.Builder()
						.target(currentLoc)
						.zoom(17)
						.bearing(0)//orientation
						.tilt(0)//tilt
						.build()
		));

//		googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
	}

	@Override public void onResume() {
		super.onResume();
		mapView.onResume();
	}

	@Override public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	@Override public void onPause() {
		super.onPause();
		mapView.onPause();
	}

	@Override public void onDestroyView() {
		mapView.onDestroy();
		super.onDestroyView();
	}

	@Override public void onLowMemory() {
		super.onLowMemory();
		mapView.onLowMemory();
	}

	@Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View result = inflater.inflate(R.layout.fragment_dashboard, container, false);
		MapsInitializer.initialize(getContext());
		mapView = (MapView) result.findViewById(R.id.map_view);
		mapView.onCreate(savedInstanceState);
		mapView.getMapAsync(this::onMapCreated);
		return result;
	}

	protected void onMapCreated(GoogleMap googleMap) {
		this.googleMap = googleMap;
	}

	protected void showRouteView() {
		googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(
				new CameraPosition.Builder()
						.target(currentLoc)
						.zoom(17)
						.bearing(0)//orientation
						.tilt(85)//tilt
						.build()
		));
	}

	protected void removeRoute() {
		if (currenyRoute != null) {
			currenyRoute.remove();
			currenyRoute = null;
		}
	}

	public void showRoute(List<RoutePoint> routePoints) {

		Toast.makeText(getContext(), "Route loaded:" + routePoints.size(), Toast.LENGTH_LONG).show();
		PolylineOptions polylineOpts = new PolylineOptions();

		for (int i = 0; i < routePoints.size(); i++) {
			polylineOpts.add(new LatLng(routePoints.get(i).lat, routePoints.get(i).lon));
		}

		currenyRoute = googleMap.addPolyline(polylineOpts);
	}

}


