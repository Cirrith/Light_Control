package com.bambrough.light;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Light_ControlActivity extends Activity {

	private SharedPreferences prefs;

	private TextView title;

	private Button control;
	private Button connect;
	private Button preferences;
	
	BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		title = (TextView) findViewById(R.id.Title);

		control = (Button) findViewById(R.id.Control);
		connect = (Button) findViewById(R.id.Connect);
		preferences = (Button) findViewById(R.id.Preferences);

		control.setOnClickListener(buttonListener);
		connect.setOnClickListener(buttonListener);
		preferences.setOnClickListener(buttonListener);

		prefs = PreferenceManager.getDefaultSharedPreferences(this
				.getApplicationContext());
		
		bluetooth.enable();

	}

	private OnClickListener buttonListener = new OnClickListener() {

		public void onClick(View v) {

			int pushed = v.getId();

			if (pushed == control.getId()) {
				int activityCode = 0;

				Intent controller = new Intent(Light_ControlActivity.this,
						controlActivity.class);

				startActivityForResult(controller, activityCode);
			}

			if (pushed == connect.getId()) {
				int activityCode = 1;

				Intent connector = new Intent(Light_ControlActivity.this,
						connectActivity.class);

				startActivityForResult(connector, activityCode);
			}

			if (pushed == preferences.getId()) {
				int activityCode = 2;

				Intent preferences = new Intent(Light_ControlActivity.this,
						preferencesActivity.class);

				startActivityForResult(preferences, activityCode);
			}
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0) {

		}

		if (requestCode == 1) {

		}

		if (requestCode == 2) {
			update();
		}
	};

	public void update()
	{
		if(prefs.getBoolean(preferencesActivity.auto, false))
			{
			
			}
		else
			{
			
			}
	}


}