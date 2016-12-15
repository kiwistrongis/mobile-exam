package ca.uoit.csci4100.assessments.finalexamstarter;

import android.content.Context;
import android.location.*;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;
import android.util.AttributeSet;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * TODO: Complete the rest of this class.
 */

public class DownloadStoreLocationsTask extends AsyncTask<String,Void,List<Store>> {
	private Context context = null;
	private StoreLocationsReadyListener listener = null;

	private double userLatitude;
	private double userLongitude;

	private int preferredStoreId;

	private Exception exception = null;

	public DownloadStoreLocationsTask(Context context, StoreLocationsReadyListener listener, double userLatitude, double userLongitude, int preferredStoreId) {
		this.context = context;
		this.listener = listener;

		this.userLatitude = userLatitude;
		this.userLongitude = userLongitude;

		this.preferredStoreId = preferredStoreId;
	}

	/**
	* calculateDistance
	*
	* This method determines the distance between two sets of latitude/longitude
	* coordinates.
	*
	* @param lat1 The first latitude
	* @param lng1 The first longitude
	* @param lat2 The second latitude
	* @param lng2 The second longitude
	*
	* @return The distance between lat1,lng1 and lat2,lng2, in kilometres
	**/
	private double calcDist(double lat1, double lng1, double lat2, double lng2) {
		double val =
			Math.sin( Math.toRadians( lat1)) * Math.sin( Math.toRadians( lat2)) +
			Math.cos( Math.toRadians( lat1)) * Math.cos( Math.toRadians( lat2)) *
			Math.cos( Math.toRadians( lng1 - lng2));
		return Math.toDegrees( Math.acos( val)) * 60 * 1.1515 * 1.609344;}
	private double calcUserDist( double sx, double sy) {
		return calcDist( userLatitude, userLongitude, sx, sy);}

	/**
	* geocode
	*
	* This method performs a forward geocode from a street address of a store
	* location, to a set of latitude/longitude coordinates
	*
	* @param address The street address for which we want to coordinates
	*
	* @return The geolocation (Address, which has latitude and longitude)
	**/
	private Address geocode(String address) {
		// TODO: Implement this method
		try {
			if( Geocoder.isPresent()){
				Geocoder geocoder = new Geocoder( this.context, Locale.getDefault());
				List<Address> ls = geocoder.getFromLocationName( address, 5);
				for (Address addr: ls) return addr;}}
		catch (IOException e){}
		return null;}

	/**
	* doInBackground
	*
	* This method loads Xml data from the URL passed as a string argument,
	* extracting the data into a list of stores.  The store data is extracted.
	* For the distance of the Store instance, you will use the method provided
	* above, called calculateDistance() and the mobile device's current
	* position.  To get the latitude/longitude for the Store instance, use
	* the geocode() function.
	*
	* @param params An array that contains the (string) URL use for downloading
	*
	* @return The list of stores
	**/
	protected List<Store> doInBackground( String... params){
		this.listener = listener;
		Vector<Store> results = new Vector<Store>();
		try {
			URL url = new URL( params[0]);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			if( conn.getResponseCode() == HttpURLConnection.HTTP_OK){
				BufferedReader reader = new BufferedReader(
					new InputStreamReader( conn.getInputStream()));
				XmlPullParser parser = Xml.newPullParser();
				parser.setInput( reader);
				AttributeSet attr = Xml.asAttributeSet( parser);
				Util.log( "attr count: %d", attr.getAttributeCount());}}
		catch( Exception ex){
			this.exception = ex;}
		return results;}

	/**
	* onPostExecute
	*
	* This method handles any exceptions that occurred during doInBackground,
	* and (if no exceptions occurred), passes the data to the activity class
	* using the StoreLocationsReadyListener interface.
	*
	* @param results The results from the doInBackground method call
	**/
	@Override
	protected void onPostExecute(List<Store> results) {
		if ( this.exception != null) {
			exception.printStackTrace();}
		else {
			listener.storeLocationsReady(results);}}

}
