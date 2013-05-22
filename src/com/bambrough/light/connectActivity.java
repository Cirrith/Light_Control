package com.bambrough.light;


import java.util.ArrayList;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class connectActivity extends ListActivity {

	private ListView screen = (ListView) findViewById(R.id.devicelist);

	BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();

	private ArrayList<BluetoothDevice> deviceList = new ArrayList<BluetoothDevice>();
	private ArrayList<String> deviceNames = new ArrayList<String>();

	private boolean discoveryFinished;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.connect);

		bluetooth.enable();

		IntentFilter discoveryFilter = new IntentFilter(
				BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		IntentFilter foundFilter = new IntentFilter(
				BluetoothDevice.ACTION_FOUND);

		registerReceiver(discoveryReceiver, discoveryFilter);
		registerReceiver(foundReceiver, foundFilter);

	}

	private BroadcastReceiver discoveryReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) 
		{
			unregisterReceiver(foundReceiver);
			unregisterReceiver(this);
			discoveryFinished = true;
		}

	};

	private BroadcastReceiver foundReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			BluetoothDevice device = intent
					.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
			deviceList.add(device);
			popNames();
		}

	};

	private void popNames()
	{
		if(deviceList.size() > 0)
		{
			for(int i = 0; i < deviceList.size(); i++)
			{
				
				StringBuilder b = new StringBuilder();
				BluetoothDevice device = deviceList.get(i);
				b.append(device.getAddress());
				b.append('\n');
				b.append(device.getName());
				deviceNames.add(b.toString());
				
				final ArrayAdapter<String> adapter = new ArrayAdapter<String>
				(this, android.R.layout.simple_list_item_1, deviceNames);
				
				
			}
		}
	}
	
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		Log.d("onListItemClick", "Device Clicked");
		Intent result = new Intent();
		result.putExtra(BluetoothDevice.EXTRA_DEVICE, deviceList.get(position));
		result.setClass(connectActivity.this, controlActivity.class);
		startActivity(result);
		finish();
	}

	private void discoverer() {
		bluetooth.startDiscovery();
		Log.d("Discoverer", "Starting Discovery");

		for (;;) {
			if (discoveryFinished) {
				Log.d("Discoverer", "Finished Discovering");
				break;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {

			}
		}
	}
}
