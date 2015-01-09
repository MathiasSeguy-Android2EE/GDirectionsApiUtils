package com.android2ee.formation.librairies.google.map.utils.direction.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.util.Log;

import com.android2ee.formation.librairies.google.map.utils.direction.model.GDirection;
import com.android2ee.formation.librairies.google.map.utils.direction.parser.DirectionsJSONParser;

/**
 * @author florian
 * Class Util
 * contains all method useful for GDirectionApi
 * 
 */
public class Util {
	
	/**
	 * Simple Rest Call to the Google Direction WebService
	 * http://maps.googleapis.com/maps/api/directions/json?
	 * 
	 * @param data
	 *           data to parse
	 * @return The json returned by the webServer
	 */
	public static String getJSONDirection(GDirectionData data) {
		String url = "http://maps.googleapis.com/maps/api/directions/json?" + "origin=" + data.getStart().latitude + ","
				+ data.getStart().longitude + "&destination=" + data.getEnd().latitude + "," + data.getEnd().longitude
				+ "&sensor=false";
		
		
		/**
		 *  mode
		 */
		if (data.getMode() != null) {
			url += "&mode=" + data.getMode();
		}
		
		/**
		 *  waypoints
		 */
		if (data.getWaypoints() != null) {
			url += "&waypoints=" + data.getWaypoints();
		}
		
		/**
		 *  alternative
		 */
		url += "&alternatives=" + data.isAlternative();
		
		/**
		 *  avoid
		 */
		if (data.getAvoid() != null) {
			url += "&avoid=" + data.getAvoid();
		}
		
		/**
		 *  language
		 */
		if (data.getLanguage() != null) {
			url += "&language=" + data.getLanguage();
		}
		
		/**
		 *  units
		 */
		if (data.getUs() != null) {
			url += "&units=" + data.getUs();
		}
				
		/**
		 *  region
		 */
		if (data.getRegion() != null) {
			url += "&region=" + data.getRegion();
		}
		
		/**
		 *  units
		 */
		if (data.getDeparture_time() != null && data.getMode() != null && (data.getMode() == Mode.MODE_DRIVING || data.getMode() == Mode.MODE_TRANSIT)) {
			url += "&departure_time=" + data.getDeparture_time();
		}
				
		/**
		 *  region
		 */
		if (data.getArrival_time() != null && data.getMode() != null && data.getMode() == Mode.MODE_TRANSIT) {
			url += "&arrival_time=" + data.getArrival_time();
		}
		
		String responseBody = null;
		// The HTTP get method send to the URL
		HttpGet getMethod = new HttpGet(url);
		// The basic response handler
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		// instantiate the http communication
		HttpClient client = new DefaultHttpClient();
		// Call the URL and get the response body
		try {
			responseBody = client.execute(getMethod, responseHandler);
		} catch (ClientProtocolException e) {
			Log.e(Util.class.getCanonicalName(), e.getMessage());
		} catch (IOException e) {
			Log.e(Util.class.getCanonicalName(), e.getMessage());
		}
		if (responseBody != null) {
			Log.e(Util.class.getCanonicalName(), responseBody);
		}
		// parse the response body
		return responseBody;
	}

	/**
	 * Parse the Json to build the GDirection object associated
	 * 
	 * @param json
	 *            The Json to parse
	 * @return The GDirection define by the JSon
	 */
	public static List<GDirection> parseJsonGDir(String json) {
		// JSon Object to parse
		JSONObject jObject;
		// The GDirection to return
		List<GDirection> directions = null;
		if (json != null) {
			try {
				// initialize the JSon
				jObject = new JSONObject(json);
				// initialize the parser
				DirectionsJSONParser parser = new DirectionsJSONParser();
				// Starts parsing data
				directions = parser.parse(jObject);
			} catch (Exception e) {
				Log.e(Util.class.getCanonicalName(), "Parsing JSon from GoogleDirection Api failed, see stack trace below:", e);
			}
		} else {
			directions = new ArrayList<GDirection>();
		}
		return directions;
	}
}
