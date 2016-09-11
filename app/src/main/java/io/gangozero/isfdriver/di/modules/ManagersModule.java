package io.gangozero.isfdriver.di.modules;

import dagger.Module;
import dagger.Provides;
import io.gangozero.isfdriver.App;
import io.gangozero.isfdriver.managers.*;

import javax.inject.Singleton;

/**
 * Created by eleven on 10/09/2016.
 */
@Module
public class ManagersModule {

	private App app;

	public ManagersModule(App app) {
		this.app = app;
	}

	@Provides @Singleton LocationManager provideLocationManager() {
		return new LocationManagerImpl();
	}

	@Provides @Singleton RoutesManager provideRoutesManager(
			RestService restService,
			GoogleMapsDirections googleMapsDirections
	) {
		return new RoutesManagerImpl(
				restService,
				googleMapsDirections
		);
	}

	@Provides @Singleton NotificationsManager provideNotificationsManager() {
		return new NotificationsManagerImpl();
	}

	@Provides @Singleton MqttManager provideMqttManager() {
		MqttManagerImpl mqttManager = new MqttManagerImpl(app);
		mqttManager.init();
		return mqttManager;
	}
}
