package io.gangozero.isfdriver.di;

import io.gangozero.isfdriver.App;
import io.gangozero.isfdriver.di.components.CoreComponent;
import io.gangozero.isfdriver.di.components.DaggerCoreComponent;
import io.gangozero.isfdriver.di.modules.ManagersModule;
import io.gangozero.isfdriver.di.modules.ProdGoogleDirectionsModule;
import io.gangozero.isfdriver.di.modules.ProdRestServiceModule;

/**
 * Created by eleven on 10/09/2016.
 */
public class DIHelper {

	private static CoreComponent coreComponent;

	public static void init(App app) {
		coreComponent = DaggerCoreComponent.builder()
				.prodGoogleDirectionsModule(new ProdGoogleDirectionsModule())
				.prodRestServiceModule(new ProdRestServiceModule())
				.managersModule(new ManagersModule(app))
				.build();
	}

	public static CoreComponent getCoreComponent() {
		return coreComponent;
	}
}
