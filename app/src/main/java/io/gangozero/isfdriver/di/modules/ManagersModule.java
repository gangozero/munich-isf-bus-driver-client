package io.gangozero.isfdriver.di.modules;

import dagger.Module;
import dagger.Provides;
import io.gangozero.isfdriver.managers.GoogleMapsDirections;
import io.gangozero.isfdriver.managers.RestService;
import io.gangozero.isfdriver.managers.RoutesManager;
import io.gangozero.isfdriver.managers.RoutesManagerImpl;

import javax.inject.Singleton;

/**
 * Created by eleven on 10/09/2016.
 */
@Module
public class ManagersModule {
	@Provides @Singleton RoutesManager provideRoutesManager(
			RestService restService,
			GoogleMapsDirections googleMapsDirections
	) {
		return new RoutesManagerImpl(
				restService,
				googleMapsDirections
		);
	}
}
