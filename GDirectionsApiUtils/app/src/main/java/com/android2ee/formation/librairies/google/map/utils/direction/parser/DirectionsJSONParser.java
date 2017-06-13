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
/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 * This class aims to parse a json object like that:
 * {
   "routes" : [
      {
         "bounds" : {
            "northeast" : {
               "lat" : 50.63918229999999,
               "lng" : 3.0378181
            },
            "southwest" : {
               "lat" : 50.6359351,
               "lng" : 3.027635
            }
         },
         "copyrights" : "Données cartographiques ©2014 Google",
         "legs" : [
            {
               "distance" : {
                  "text" : "1,0 km",
                  "value" : 1022
               },
               "duration" : {
                  "text" : "13 minutes",
                  "value" : 761
               },
               "end_address" : "D751, 59000 Lille, France",
               "end_location" : {
                  "lat" : 50.6362271,
                  "lng" : 3.0378181
               },
               "start_address" : "1-5 D933, 59130 Lambersart, France",
               "start_location" : {
                  "lat" : 50.6359351,
                  "lng" : 3.027635
               },
               "steps" : [
                  {
                     "distance" : {
                        "text" : "0,5 km",
                        "value" : 535
                     },
                     "duration" : {
                        "text" : "6 minutes",
                        "value" : 383
                     },
                     "end_location" : {
                        "lat" : 50.63918229999999,
                        "lng" : 3.0331989
                     },
                     "html_instructions" : "Prendre la direction \u003cb\u003enord-est\u003c/b\u003e sur \u003cb\u003eAv. du Colisée/D933\u003c/b\u003e vers \u003cb\u003eRésidence le Clos du Colissee\u003c/b\u003e\u003cdiv style=\"font-size:0.9em\"\u003eContinuer de suivre Av. du Colisée\u003c/div\u003e",
                     "polyline" : {
                        "points" : "sy`tHwinQCOEKGOEMCEOc@GSS_@g@y@S_@KMEQWc@i@eACIk@sAa@{@a@{@k@iAS_@MQGIQWYa@KQS_@k@gA}@gB[s@CKOc@"
                     },
                     "start_location" : {
                        "lat" : 50.6359351,
                        "lng" : 3.027635
                     },
                     "travel_mode" : "WALKING"
                  },
                  {
                     "distance" : {
                        "text" : "0,5 km",
                        "value" : 487
                     },
                     "duration" : {
                        "text" : "6 minutes",
                        "value" : 378
                     },
                     "end_location" : {
                        "lat" : 50.6362271,
                        "lng" : 3.0378181
                     },
                     "html_instructions" : "Prendre \u003cb\u003eà droite\u003c/b\u003e sur \u003cb\u003eAv. de l'Hippodrome/D751\u003c/b\u003e\u003cdiv style=\"font-size:0.9em\"\u003eContinuer de suivre D751\u003c/div\u003e\u003cdiv style=\"font-size:0.9em\"\u003eVotre destination se trouvera sur la droite\u003c/div\u003e",
                     "maneuver" : "turn-right",
                     "polyline" : {
                        "points" : "{matHoloQLKb@]JILQDEDCROFGBAHIROJIJILMLIDENMp@_@vAgARWJMPWLUP[HSHUFSFYDQBUFS@IHc@BK?CLw@@APgA@IN{@?E@GTwA"
                     },
                     "start_location" : {
                        "lat" : 50.63918229999999,
                        "lng" : 3.0331989
                     },
                     "travel_mode" : "WALKING"
                  }
               ],
               "via_waypoint" : []
            }
         ],
         "overview_polyline" : {
            "points" : "sy`tHwinQQk@a@kA{AgCkAeCmAoCmAeCa@q@Ya@e@s@_AgByA{CSo@p@i@X[f@a@tAgATSp@_@vAgA^e@^m@Zo@Pi@XuAr@iEf@aD"
         },
         "summary" : "Av. du Colisée et D751",
         "warnings" : [
            "Le calcul d'itinéraires piétons est en bêta. Faites attention – Cet itinéraire n'est peut-être pas complètement aménagé pour les piétons."
         ],
         "waypoint_order" : []
      }
   ],
   "status" : "OK"
}
 */
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
		List<GDirection> directionsList = null;
		// The current GDirection
		GDirection currentGDirection = null;
		// The legs
		List<GDLegs> legs = null;	
		// The current leg
		GDLegs currentLeg = null;
		// The paths
		List<GDPath> paths = null;
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
			directionsList = new ArrayList<GDirection>();
			/** Traversing all routes */
			for (int i = 0; i < jRoutes.length(); i++) {
				jRoute=(JSONObject) jRoutes.get(i);
				jLegs = jRoute.getJSONArray("legs");
				Log.v(tag, "routes[" + i + "]contains jLegs found : " + jLegs.length());
				/** Traversing all legs */
				legs = new ArrayList<GDLegs>();
				for (int j = 0; j < jLegs.length(); j++) {
					jLeg=(JSONObject) jLegs.get(j);
					jSteps = jLeg.getJSONArray("steps");
					Log.v(tag, "routes[" + i + "]:legs[" + j + "] contains jSteps found : " + jSteps.length());
					/** Traversing all steps */
					paths = new ArrayList<GDPath>();
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
					currentLeg = new GDLegs(paths);
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