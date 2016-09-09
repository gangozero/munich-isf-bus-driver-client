package io.gangozero.isfdriver.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import io.gangozero.isfdriver.R;

/**
 * Created by eleven on 09/09/2016.
 */
public class DriverFragment extends Fragment {

	@BindView(R.id.map_view) MapView mapView;
	private Unbinder binder;

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
	}

	@Override public void onDestroy() {
		mapView.onDestroy();
		super.onDestroy();
	}

	@Override public void onPause() {
		super.onPause();
		mapView.onPause();
	}

	@Override public void onLowMemory() {
		super.onLowMemory();
		mapView.onLowMemory();
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
		
	}

	@Override public void onDestroyView() {
		binder.unbind();
		super.onDestroyView();
	}
}
