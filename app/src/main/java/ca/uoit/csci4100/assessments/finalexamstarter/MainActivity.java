package ca.uoit.csci4100.assessments.finalexamstarter;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.*;
import android.Manifest;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v13.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.*;
import android.widget.*;

import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements StoreLocationsReadyListener, LocationListener {
	private final static String url_str = "http://csundergrad.science.uoit.ca/csci1040u/csci4100u_data/store_locations.xml";
	private boolean loadedStoreLocations = false;
	private List<Store> storeLocations = null;
	private PreferredStoreLocationDBHelper dbHelper = null;
	private int store_pref = -1;
	private LocationManager loc_man = null;
	private String loc_prov_str = null;
	private Location user_loc = null;

	private TextView label_message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		this.label_message = (TextView) findViewById( R.id.label_message);

		loadElements();
		setupGeolocation();
		loadPreferredStore();
	}

	private void loadElements(){}

	/**
	 * setupGeolocation
	 *
	 * This method will enable geolocation, and register this activity as a
	 * listener of location updates.
	 **/
	private void setupGeolocation() {
		if( ContextCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
			ActivityCompat.requestPermissions( this,
				new String[]{Manifest.permission.ACCESS_FINE_LOCATION});}

		this.loc_man = (LocationManager) getSystemService( LOCATION_SERVICE);

		String gps_prov = LocationManager.GPS_PROVIDER;
		this.loc_prov_str = gps_prov;
		if( ! loc_man.isProviderEnabled( gps_prov)){
			startActivity( new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS));}

		try {
			loc_man.requestLocationUpdates( loc_prov_str, 5000, 5, this);
			this.user_loc = loc_man.getLastKnownLocation( loc_prov_str);}
		catch( SecurityException ex){
			Util.log( "Location permissions denied! D: =\n%s", ex);}
	}

	/**
	 * loadPreferredStore
	 *
	 * This method will instantiate the PreferredStoreLocationDBHelper, and use
	 * it to load the preferred store saved by the user (if any) on a previous
	 * execution of this application (-1 if none was saved previously).
	 **/
	private void loadPreferredStore() {
		PreferredStoreLocationDBHelper db =
			new PreferredStoreLocationDBHelper( this);
		this.store_pref = db.getPreferredStore();}

	private void loadStores(){
		this.label_message.setText( R.string.label_message_loading);
		new DownloadStoreLocationsTask( this, this,
			user_loc.getLatitude(), user_loc.getLongitude(),store_pref)
			.execute( url_str);}
	private void sortStores(){}

	private void update(){}

	@Override
	public void onLocationChanged(Location location) {
		this.loadStores();}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {}
	@Override
	public void onProviderEnabled(String provider) {}
	@Override
	public void onProviderDisabled(String provider) {}

	@Override
	public void storeLocationsReady(List<Store> storeLocations){
		this.storeLocations = storeLocations;}
}
