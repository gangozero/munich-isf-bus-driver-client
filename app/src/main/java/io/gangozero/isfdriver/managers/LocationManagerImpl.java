package io.gangozero.isfdriver.managers;

import com.google.android.gms.maps.model.LatLng;
import rx.Observable;

/**
 * Created by eleven on 10/09/2016.
 */
public class LocationManagerImpl implements LocationManager {
	@Override public Observable<LatLng> currentLocation() {
		return Observable.just(new LatLng(47.3800251, 8.5364232));
	}
}
