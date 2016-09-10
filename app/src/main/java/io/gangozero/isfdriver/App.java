package io.gangozero.isfdriver;

import android.app.Application;
import io.gangozero.isfdriver.di.DIHelper;

/**
 * Created by eleven on 10/09/2016.
 */
public class App extends Application {
	@Override public void onCreate() {
		super.onCreate();
		DIHelper.init();
	}
}
