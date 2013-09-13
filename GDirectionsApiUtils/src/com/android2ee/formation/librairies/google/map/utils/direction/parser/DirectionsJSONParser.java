package com.android2ee.formation.librairies.google.map.utils.direction.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.android2ee.formation.librairies.google.map.utils.direction.model.GDLegs;
import com.android2ee.formation.librairies.google.map.utils.direction.model.GDPath;
import com.android2ee.formation.librairies.google.map.utils.direction.model.GDPoint;
import com.android2ee.formation.librairies.google.map.utils.direction.model.GDirection;
import com.google.android.gms.maps.model.LatLng;

//http://wptrafficanalyzer.in/blog/author/george/
public class DirectionsJSONParser {
	String tag = "DirectionsJSONParser";

	/**
	 * Receives a JSONObject and returns a GDirection
	 * 
	 * @param jObject
	 *            The Json to parse
	 * @return The GDirection defined by the JSon Object
	 */
	public List<GDirection> parse(JSONObject jObject) {
		// The returned direction
		List<GDirection> directionsList = new ArrayList<GDirection>();
		// The current GDirection
		GDirection currentGDirection = null;
		// The legs
		List<GDLegs> legs = new ArrayList<GDLegs>();	
		// The current leg
		GDLegs currentLeg = null;
		// The paths
		List<GDPath> paths = new ArrayList<GDPath>();
		// The current path
		GDPath currentPath = null;
		// JSON Array representing Routes
		JSONArray jRoutes = null;
		JSONObject jRoute;
		JSONObject jBound;
		// JSON Array representing Legs
		JSONArray jLegs = null;
		JSONObject jLeg;
		// JSON Array representing Step
		JSONArray jSteps = null;
		JSONObject jStep;
		String polyline = "";
		try {
			jRoutes = jObject.getJSONArray("routes");
			Log.v(tag, "routes found : " + jRoutes.length());
			/** Traversing all routes */
			for (int i = 0; i < jRoutes.length(); i++) {
				jRoute=(JSONObject) jRoutes.get(i);
				jLegs = jRoute.getJSONArray("legs");
				Log.v(tag, "routes[" + i + "]contains jLegs found : " + jLegs.length());
				/** Traversing all legs */
				for (int j = 0; j < jLegs.length(); j++) {
					jLeg=(JSONObject) jLegs.get(j);
					jSteps = jLeg.getJSONArray("steps");
					Log.v(tag, "routes[" + i + "]:legs[" + j + "] contains jSteps found : " + jSteps.length());
					/** Traversing all steps */
					for (int k = 0; k < jSteps.length(); k++) {
						jStep = (JSONObject) jSteps.get(k);
						polyline = (String) ((JSONObject) (jStep).get("polyline")).get("points");
						// Build the List of GDPoint that define the path
						List<GDPoint> list = decodePoly(polyline);
						// Create the GDPath
						currentPath = new GDPath(list);
						currentPath.setDistance(((JSONObject)jStep.get("distance")).getInt("value"));
						currentPath.setDuration(((JSONObject)jStep.get("duration")).getInt("value"));
						currentPath.setHtmlText(jStep.getString("html_instructions"));
						currentPath.setTravelMode(jStep.getString("travel_mode"));
						Log.v(tag,
								"routes[" + i + "]:legs[" + j + "]:Step[" + k + "] contains Points found : "
										+ list.size());
						// Add it to the list of Path of the Direction
						paths.add(currentPath);
					}
					// 
					currentLeg=new GDLegs(paths);
					currentLeg.setmDistance(((JSONObject)jLeg.get("distance")).getInt("value"));
					currentLeg.setmDuration(((JSONObject)jLeg.get("duration")).getInt("value"));
					currentLeg.setmEndAddress(jLeg.getString("end_address"));
					currentLeg.setmStartAddress(jLeg.getString("start_address"));
					legs.add(currentLeg);
					
					Log.v(tag, "Added a new Path and paths size is : " + paths.size());
				}
				// Build the GDirection using the paths found
				currentGDirection = new GDirection(legs);
				jBound=(JSONObject)jRoute.get("bounds");
				currentGDirection.setmNorthEastBound(new LatLng(
						((JSONObject)jBound.get("northeast")).getDouble("lat"),
						((JSONObject)jBound.get("northeast")).getDouble("lng")));
				currentGDirection.setmSouthWestBound(new LatLng(
						((JSONObject)jBound.get("southwest")).getDouble("lat"),
						((JSONObject)jBound.get("southwest")).getDouble("lng")));
				currentGDirection.setCopyrights(jRoute.getString("copyrights"));
				directionsList.add(currentGDirection);
			}

		} catch (JSONException e) {
			Log.e(tag, "Parsing JSon from GoogleDirection Api failed, see stack trace below:", e);
		} catch (Exception e) {
			Log.e(tag, "Parsing JSon from GoogleDirection Api failed, see stack trace below:", e);
		}
		return directionsList;
	}

	/**
	 * Method to decode polyline points
	 * Courtesy :
	 * http://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction
	 * -api-with-java
	 */
	private List<GDPoint> decodePoly(String encoded) {

		List<GDPoint> poly = new ArrayList<GDPoint>();
		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;

		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;

			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;
			poly.add(new GDPoint((double) lat / 1E5, (double) lng / 1E5));
		}

		return poly;
	}
}