package io.gangozero.isfdriver.managers;

import com.google.android.gms.maps.model.LatLng;
import rx.Observable;

/**
 * Created by eleven on 10/09/2016.
 */
public interface LocationManager {
	Observable<LatLng> currentLocation();
}
