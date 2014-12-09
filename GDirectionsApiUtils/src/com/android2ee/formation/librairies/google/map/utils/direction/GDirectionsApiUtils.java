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
	 * @param waypoints
	 *            the waypoints
	 * @param alternative
	 *            The Alternative Route boolean
	 * @param avoid
	 *            The avoid highway
	 * @param language
	 *            specifies language (es, en, fr, ...) need to set language code
	 * @param unit
	 *            the unit system (metric, imperial)
	 * @param region
	 *            specifies region (es, en, fr, ...) need to set code country here
	 * @param departureTime
	 *            departureTime in ms or "now"
	 * @param arrivalTime
	 *            arrivalTime in ms or "now"
	 */
	public static void getDirection(DCACallBack callback, LatLng start, LatLng end, Mode mode, String waypoints, boolean alternative, Avoid avoid, String language,
										UnitSystem us, String region, String departureTime, String arrivalTime) {
		GoogleDirectionAsyncRestCall async = new GoogleDirectionAsyncRestCall(callback, mode, waypoints, alternative, avoid, language, us, region, departureTime, arrivalTime);
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
	 * @param waypoints
	 *            the waypoints
	 * @param alternative
	 *            The Alternative Route boolean
	 * @param avoid
	 *            The avoid highway
	 * @param language
	 *            specifies language (es, en, fr, ...) need to set language code
	 * @param unit
	 *            the unit system (metric, imperial)
	 * @param region
	 *            specifies region (es, en, fr, ...) need to set code country here
	 */
	public static void getDirection(DCACallBack callback, LatLng start, LatLng end, Mode mode, String waypoints, boolean alternative, Avoid avoid, String language,
										UnitSystem us, String region) {
		getDirection(callback, start, end, mode, waypoints, alternative, avoid, language, us, region, null, null);
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
	 * @param waypoints
	 *            the waypoints
	 * @param alternative
	 *            The Alternative Route boolean
	 * @param avoid
	 *            The avoid highway
	 * @param language
	 *            specifies language (es, en, fr, ...) need to set language code
	 * @param region
	 *            specifies region (es, en, fr, ...) need to set code country here
	 */
	public static void getDirection(DCACallBack callback, LatLng start, LatLng end, Mode mode, String waypoints, boolean alternative, Avoid avoid, String language, UnitSystem us) {
		getDirection(callback, start, end, mode, waypoints, alternative, avoid, language, us, null, null, null);
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
	 * @param waypoints
	 *            the waypoints
	 * @param alternative
	 *            The Alternative Route boolean
	 * @param avoid
	 *            The avoid highway
	 * @param language
	 *            specifies language (es, en, fr, ...) need to set language code
	 */
	public static void getDirection(DCACallBack callback, LatLng start, LatLng end, Mode mode, String waypoints, boolean alternative, Avoid avoid, String language) {
		getDirection(callback, start, end, mode, waypoints, alternative, avoid, language , null, null, null, null);
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
	 * @param waypoints
	 *            the waypoints
	 * @param alternative
	 *            The Alternative Route boolean
	 * @param avoid
	 *            The avoid highway boolean
	 */
	public static void getDirection(DCACallBack callback, LatLng start, LatLng end, Mode mode, String waypoints, boolean alternative, Avoid avoid) {
		getDirection(callback, start, end, mode, waypoints, alternative, avoid, null, null, null, null, null);
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
	 * @param waypoints
	 *            the waypoints
	 */
	public static void getDirection(DCACallBack callback, LatLng start, LatLng end, Mode mode, String waypoints, boolean alternative) {
		getDirection(callback, start, end, mode, waypoints,  alternative, null, null, null, null, null, null);
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
	 * @param waypoints
	 *            the waypoints
	 */
	public static void getDirection(DCACallBack callback, LatLng start, LatLng end, Mode mode, String waypoints) {
		getDirection(callback, start, end, mode, waypoints, false, null, null, null, null, null, null);
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
	public static void getDirectionTransit(DCACallBack callback, LatLng start, LatLng end, String departureTime, String arrivalTime) {
		getDirection(callback, start, end, Mode.MODE_TRANSIT, null, false , null, null, null, null, departureTime, arrivalTime);
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
	public static void getDirection(DCACallBack callback, LatLng start, LatLng end, Mode mode) {
		getDirection(callback, start, end, mode, null, false , null, null, null, null, null, null);
	}

	/******************************************************************************************/
	/** Private Method : The big dark gas factory **************************************************************************/
	/******************************************************************************************/

	/**
	 * @author Mathias Seguy (Android2EE)
	 * mode (defaults to driving) — Specifies the mode of transport to use when calculating directions. Valid values are specified in Travel Modes. If you set the mode to "transit" you must also specify either a departure_time or an arrival_time.
	 * waypoints — Specifies an array of waypoints. Waypoints alter a route by routing it through the specified location(s). A waypoint is specified as either a latitude/longitude coordinate or as an address which will be geocoded. Waypoints are only supported for driving, walking and bicycling directions. (For more information on waypoints, see Using Waypoints in Routes below.)
	 * alternatives — If set to true, specifies that the Directions service may provide more than one route alternative in the response. Note that providing route alternatives may increase the response time from the server.
	 * avoid — Indicates that the calculated route(s) should avoid the indicated features. This parameter supports the following arguments:
	 * tolls indicates that the calculated route should avoid toll roads/bridges.
	 * highways indicates that the calculated route should avoid highways.
	 * ferries indicates that the calculated route should avoid ferries.
	 * For more information see Route Restrictions below.
	 * language — The language in which to return results. See the list of supported domain languages. Note that we often update supported languages so this list may not be exhaustive. If language is not supplied, the service will attempt to use the native language of the domain from which the request is sent.
	 * units — Specifies the unit system to use when displaying results. Valid values are specified in Unit Systems below.
	 * region — The region code, specified as a ccTLD ("top-level domain") two-character value. (For more information see Region Biasing below.)
	 * departure_time specifies the desired time of departure as seconds since midnight, January 1, 1970 UTC. The departure time may be specified in two cases:
	 * For Transit Directions: One of departure_time or arrival_time must be specified when requesting directions.
	 * For Driving Directions: Google Maps API for Work customers can specify the departure_time to receive trip duration considering current traffic conditions. The departure_time must be set to within a few minutes of the current time.
	 * A special departure_time value "now" can also be used to automatically calculate the current departure time. Note that a numeric departure_time must be specified as an integer.
	 * arrival_time specifies the desired time of arrival for transit directions as seconds since midnight, January 1, 1970 UTC. One of departure_time or arrival_time must be specified when requesting transit directions. Note that arrival_time must be specified as an integer.
	 * Either the arrival_time or the departure_time parameter must be specified any time you request transit directions.
	 * 
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
		private UnitSystem mUs = null;
		private Mode mDirectionMode = null;
		private String mWaypoints = null;
		private boolean mAlternative = false;
		private Avoid mAvoid = null;
		private String mLanguage = null;
		private String mRegion = null;
		private String mDepartureTime = null;
		private String mArrivalTime = null;
		
		/**
		 * The CallBack which waiting for the GDirection object
		 */
		private DCACallBack callback;
		
		/**
		 * @param callback
		 *            The callBack waiting for the GDirection Object
		 * @param mDirectionMode
		 *            The direction mode (driving,walking...)
		 * @param waypoints
		 *            The waypoints
		 * @param alternative
		 *            alternatives routes
		 * @param avoid
		 *           avoid route type ( highways, tolls, ferries)
		 * @param mLanguage
		 *            The language (fr, es, ...) need language code here
		 * @param mUs
		 *            The unit system (metric , imperial)
		 * @param mRegion
		 *            The region (fr, es, ...) need country code here
		 */
		public GoogleDirectionAsyncRestCall(DCACallBack callback, Mode mDirectionMode, String waypoints, boolean alternative, Avoid avoid, 
				String language, UnitSystem us, String region, String departureTime, String arrivalTime) {
			super();
			
			this.mDirectionMode = mDirectionMode;
			this.mWaypoints = waypoints;
			this.mAlternative = alternative;
			this.mAvoid = avoid;
			this.mLanguage = language;
			this.mUs = us;
			this.mRegion = region;
			this.mDepartureTime = departureTime;
			this.mArrivalTime = arrivalTime;
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
			String json = getJSONDirection(arg0[0], arg0[1], mDirectionMode, mWaypoints, mAlternative, mAvoid, mLanguage, mUs, mRegion, mDepartureTime, mArrivalTime);
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
	 * @param unitSystem
	 *            The unitSystem
	 * @param mode
	 *            The mode (walking,driving..)
	 * @param waypoints
	 *            waypoints
	 * @param alternative
	 *            alternative route
	 * @param avoid
	 *            avoid type route
	 * @param language
	 *            language (fr, es, ...)
	 * @param us
	 *            unit system (metric, imperial)
	 * @param region
	 *            country (es, fr, ...)
	 * @return The json returned by the webServer
	 */
	private static String getJSONDirection(LatLng start, LatLng end, Mode mode, String waypoints, boolean alternative, Avoid avoid, String language, 
			UnitSystem us, String region, String departure_time, String arrival_time) {
		String url = "http://maps.googleapis.com/maps/api/directions/json?" + "origin=" + start.latitude + ","
				+ start.longitude + "&destination=" + end.latitude + "," + end.longitude
				+ "&sensor=false";
		
		
		// mode
		if (mode != null) {
			url += "&mode=" + mode;
		}
		
		// mode
		if (waypoints != null) {
			url += "&waypoints=" + waypoints;
		}
		
		// alternative
		url += "&alternatives=" + alternative;
		
		// avoid
		if (avoid != null) {
			url += "&avoid=" + avoid;
		}
		
		// language
		if (language != null) {
			url += "&language=" + language;
		}
		
		// units
		if (us != null) {
			url += "&units=" + us;
		}
				
		// region
		if (region != null) {
			url += "&region=" + region;
		}
		
		// units
		if (departure_time != null && mode != null && (mode == Mode.MODE_DRIVING || mode == Mode.MODE_TRANSIT)) {
			url += "&departure_time=" + departure_time;
		}
				
		// region
		if (arrival_time != null && mode != null && mode == Mode.MODE_TRANSIT) {
			url += "&arrival_time=" + arrival_time;
		}
		
		Log.e("TAG", "url = " + url);
		
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
