package io.gangozero.isfdriver.activties;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import io.gangozero.isfdriver.R;
import io.gangozero.isfdriver.fragments.DashboardFragment;

public class DriverActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_driver);
		if (savedInstanceState == null) {
			getSupportFragmentManager()
					.beginTransaction()
					.add(R.id.root_container, DashboardFragment.create())
					.commit();
		}
	}
}