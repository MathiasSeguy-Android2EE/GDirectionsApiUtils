/**<ul>
 * <li>GoogleMapSample</li>
 * <li>com.android2ee.formation.librairies.google.map.utils.direction</li>
 * <li>12 sept. 2013</li>
 * 
 * <li>======================================================</li>
 *
 * <li>Projet : Mathias Seguy Project</li>
 * <li>Produit par MSE.</li>
 *
 /**
 * <ul>
 * Android Tutorial, An <strong>Android2EE</strong>'s project.</br> 
 * Produced by <strong>Dr. Mathias SEGUY</strong>.</br>
 * Delivered by <strong>http://android2ee.com/</strong></br>
 *  Belongs to <strong>Mathias Seguy</strong></br>
 ****************************************************************************************************************</br>
 * This code is free for any usage except training and can't be distribute.</br>
 * The distribution is reserved to the site <strong>http://android2ee.com</strong>.</br>
 * The intelectual property belongs to <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * 
 * *****************************************************************************************************************</br>
 *  Ce code est libre de toute utilisation mais n'est pas distribuable.</br>
 *  Sa distribution est reservée au site <strong>http://android2ee.com</strong>.</br> 
 *  Sa propriété intellectuelle appartient à <strong>Mathias Seguy</strong>.</br>
 *  <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * *****************************************************************************************************************</br>
 */
package com.android2ee.formation.librairies.google.map.utils.direction;

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

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.android2ee.formation.librairies.google.map.utils.direction.model.GDColor;
import com.android2ee.formation.librairies.google.map.utils.direction.model.GDLegs;
import com.android2ee.formation.librairies.google.map.utils.direction.model.GDPath;
import com.android2ee.formation.librairies.google.map.utils.direction.model.GDPoint;
import com.android2ee.formation.librairies.google.map.utils.direction.model.GDirection;
import com.android2ee.formation.librairies.google.map.utils.direction.parser.DirectionsJSONParser;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 *        This class aims to make a layer over the Direction Api.
 *        To have a Google Direction and Draw it You just have to:
 *        <ul>
 *        <li>Implements the DCACallBack and its method public void onDirectionLoaded(GDirection
 *        direction)</li>
 *        <li>Then you call GDirectionApiUtils.getDirection(DCACallBack, start point, end point,
 *        GDirectionsApiUtils.MODE_***); When the GDirection is build, the DCACallBack is called
 *        giving you the GDirection you are waiting for.</li>
 *        <li>Then you can handle yourself the GDirection or you can call drawGDirection(GDirection
 *        direction, GoogleMap map)</li>
 *        </ul>
 *        d
 *        public class MainActivity extends ActionBarActivity implements DCACallBack {</br>
 *        private void getDirections(LatLng point) {</br>
 *        GDirectionsApiUtils.getDirection(this, mDeviceLatlong, point,
 *        GDirectionsApiUtils.MODE_WALKING);</br>
 *        }</br>
 *        public void onDirectionLoaded(GDirection direction) {</br>
 *        Log.e("MainActivity", "onDirectionLoaded : Draw GDirections Called with path " +
 *        direction);</br>
 *        // Display the direction or use the DirectionsApiUtils</br>
 *        GDirectionsApiUtils.drawGDirection(direction, mMap);</br>
 *        }
 */
public class GDirectionsApiUtils {
	/******************************************************************************************/
	/** Attributes **************************************************************************/
	/******************************************************************************************/
	public final static String MODE_DRIVING = "driving";
	public final static String MODE_WALKING = "walking";
	public final static String MODE_BICYCLING = "bicycling";
	public final static String MODE_TRANSIT = "transit";
	private static String tag = "GDirectionsApiUtils";

	/******************************************************************************************/
	/** Public Method **************************************************************************/
	/******************************************************************************************/

	/**
	 * Draw on the given map the given GDirection object
	 * 
	 * @param direction
	 *            The google direction to draw
	 * @param map
	 *            The map to draw on
	 */
	public static void drawGDirection(GDirection direction, GoogleMap map){
		drawGDirection(direction, map, new ArrayList<GDColor>(), null);
	}	
	
	/**
	 * Draw on the given map the given GDirection object
	 * 
	 * @param direction
	 *            The google direction to draw
	 * @param map
	 *            The map to draw on
	 * @param color
	 *            color for legs
	 */
	public static void drawGDirection(GDirection direction, GoogleMap map, GDColor color){
		
		ArrayList<GDColor> colors = new ArrayList<GDColor>();
		colors.add(color);
		drawGDirection(direction, map, colors, null);
	}
	
	/**
	 * Draw on the given map the given GDirection object
	 * 
	 * @param direction
	 *            The google direction to draw
	 * @param map
	 *            The map to draw on
	 * @param color
	 *            color for legs
	 * @param IGDText
	 * 			  interface to format display title and snippet
	 */
	public static void drawGDirection(GDirection direction, GoogleMap map, GDColor color, IGDFormatter iGDText){
		
		ArrayList<GDColor> colors = new ArrayList<GDColor>();
		colors.add(color);
		drawGDirection(direction, map, colors, iGDText);
	}
	
	/**
	 * Draw on the given map the given GDirection object
	 * 
	 * @param direction
	 *            The google direction to draw
	 * @param map
	 *            The map to draw on
	 * @param colors
	 *            colors for legs
	 * @param IGDText
	 * 			  interface to format display title and snippet
	 */
	public static void drawGDirection(GDirection direction, GoogleMap map, ArrayList<GDColor> colors, IGDFormatter formatter) {
		// The polylines option to create polyline
		PolylineOptions lineOptions = null;
		// index of GDPoint within the current GDPath
		int i = 0;
		// index of the current GDPath
		int pathIndex = 0;
		// index of the current GDLegs
		int legsIndex = 0;
		// Browse the directions' legs and then the leg's paths
		for (GDLegs legs : direction.getLegsList()) {
			for (GDPath path : legs.getPathsList()) {
				// Create the polyline
				lineOptions = new PolylineOptions();
				// manage indexes
				i = 0;
				pathIndex++;
				// browse the GDPoint that define the path
				for (GDPoint point : path.getPath()) {
					i++;
					// Add the point to the polyline
					lineOptions.add(point.getLatLng());
					// Mark the last GDPoint of the path with a HUE_AZURE marker
					if (i == path.getPath().size() - 1) {
						Marker marker = map.addMarker(new MarkerOptions().position(point.getLatLng())
								.title(formatter != null ? formatter.getTitle(path) : "Step + i")
								.snippet(formatter != null ? formatter.getSnippet(path) : "Step + i")
								.icon( ((colors != null && colors.size() > 0) ?
										BitmapDescriptorFactory.defaultMarker(colors.get(legsIndex % colors.size()).colorPin) :
										BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))));
						if (formatter != null && formatter.isInfoWindows()) {
							formatter.setContents(marker, direction, legs, path);
							marker.showInfoWindow();
						}
					}
				}
				// A 5 width Polyline please
				lineOptions.width(5);
				// The polyline color (alternating green/blue path)
				if (colors != null && colors.size() > 0) {
					lineOptions.color(colors.get(legsIndex % colors.size()).colorLine);
				} else {
					if (pathIndex % 2 == 0) {
						lineOptions.color(Color.GREEN);
					} else {
						lineOptions.color(Color.BLUE);
					}
				}
				// Drawing polyline in the Google Map for the i-th route
				map.addPolyline(lineOptions);
				
			}
			legsIndex++;
		}
	}
	

	/**
	 * Find the direction between two points on the maps (direction is the path to follow to go from
	 * start to end points)
	 * 
	 * @param callback
	 *            The DCACallBack to prevent when data have been retrieve and built. It will receive
	 *            a GDirection
	 * @param start
	 *            The starting point
	 * @param end
	 *            The ending point
	 * @param mode
	 *            The Mode (see constant of this)
	 * @param alternative
	 *            The Alternative Route boolean
	 */
	public static void getDirection(DCACallBack callback, LatLng start, LatLng end, String mode, boolean alternative) {
		GoogleDirectionAsyncRestCall async = new GoogleDirectionAsyncRestCall(callback, mode, alternative);
		async.execute(start, end);
	}
	
	
	/**
	 * Find the direction between two points on the maps (direction is the path to follow to go from
	 * start to end points)
	 * 
	 * @param callback
	 *            The DCACallBack to prevent when data have been retrieve and built. It will receive
	 *            a GDirection
	 * @param start
	 *            The starting point
	 * @param end
	 *            The ending point
	 * @param mode
	 *            The Mode (see constant of this)
	 */
	public static void getDirection(DCACallBack callback, LatLng start, LatLng end, String mode) {
		GoogleDirectionAsyncRestCall async = new GoogleDirectionAsyncRestCall(callback, mode, false);
		async.execute(start, end);
	}

	/******************************************************************************************/
	/** Private Method : The big dark gas factory **************************************************************************/
	/******************************************************************************************/

	/**
	 * @author Mathias Seguy (Android2EE)
	 * @goals
	 *        This class aims to make an async call to the server and retrieve the Json representing
	 *        the Direction
	 *        Then build the GDirection object
	 *        Then post it to the DCACallBack in the UI Thread
	 */
	public static final class GoogleDirectionAsyncRestCall extends AsyncTask<LatLng, String, List<GDirection>> {
		/**
		 * The direction modes (Driving,walking, @see constant of GDirectionsApiUtils)
		 */
		private String mDirectionMode = null;
		private boolean mAlternative = false;
		/**
		 * The CallBack which waiting for the GDirection object
		 */
		private DCACallBack callback;

		/**
		 * @param callback
		 *            The callBack waiting for the GDirection Object
		 * @param mDirectionMode
		 *            The direction mode (driving,walking...)
		 */
		public GoogleDirectionAsyncRestCall(DCACallBack callback, String mDirectionMode, boolean alternative) {
			super();
			this.mDirectionMode = mDirectionMode;
			this.mAlternative = alternative;
			this.callback = callback;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
		 */
		@Override
		protected List<GDirection> doInBackground(LatLng... arg0) {
			// Do the rest http call
			String json = getJSONDirection(arg0[0], arg0[1], mDirectionMode, mAlternative);
			// Parse the element and return it
			return parseJsonGDir(json);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(List<GDirection> result) {
			super.onPostExecute(result);
			// Just call the callback
			callback.onDirectionLoaded(result);
		}
	}

	/**
	 * Simple Rest Call to the Google Direction WebService
	 * http://maps.googleapis.com/maps/api/directions/json?
	 * 
	 * @param start
	 *            Starting Point
	 * @param end
	 *            Ending Point
	 * @param mode
	 *            The mode (walking,driving..)
	 * @return The json returned by the webServer
	 */
	private static String getJSONDirection(LatLng start, LatLng end, String mode, boolean alternative) {
		String url = "http://maps.googleapis.com/maps/api/directions/json?" + "origin=" + start.latitude + ","
				+ start.longitude + "&destination=" + end.latitude + "," + end.longitude
				+ "&sensor=false&units=metric&mode=" + mode + "&alternatives=" + alternative;
		
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
			Log.e(tag, e.getMessage());
		} catch (IOException e) {
			Log.e(tag, e.getMessage());
		}
		if (responseBody != null) {
			Log.e(tag, responseBody);
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
	private static List<GDirection> parseJsonGDir(String json) {
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
				Log.e(tag, "Parsing JSon from GoogleDirection Api failed, see stack trace below:", e);
			}
		} else {
			directions = new ArrayList<GDirection>();
		}
		return directions;
	}
}
