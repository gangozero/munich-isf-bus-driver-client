package io.gangozero.isfdriver.di.components;

import dagger.Component;
import io.gangozero.isfdriver.di.modules.ManagersModule;
import io.gangozero.isfdriver.di.modules.ProdGoogleDirectionsModule;
import io.gangozero.isfdriver.di.modules.ProdRestServiceModule;
import io.gangozero.isfdriver.presenters.DriverDashboardPresenter;

import javax.inject.Singleton;

/**
 * Created by eleven on 10/09/2016.
 */
@Singleton
@Component(modules = {
		ProdGoogleDirectionsModule.class,
		ProdRestServiceModule.class,
		ManagersModule.class
})
public interface CoreComponent {
	void inject(DriverDashboardPresenter routesManager);
}
