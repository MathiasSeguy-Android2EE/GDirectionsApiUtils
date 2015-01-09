package com.android2ee.formation.librairies.google.map.utils.direction;

import com.android2ee.formation.librairies.google.map.utils.direction.model.GDLegs;
import com.android2ee.formation.librairies.google.map.utils.direction.model.GDPath;
import com.android2ee.formation.librairies.google.map.utils.direction.model.GDirection;
import com.google.android.gms.maps.model.Marker;

/**
 * @author florian
 * Interface between client and method drawGDirection
 * Can custom the display of infoWindows, or just change title and snippet
 */
public interface IGDFormatter {

	/**
	 * Method to get the title of this path
	 * @param path, the current path which display on maps
	 * @return the title of this path
	 */
	public abstract String getTitle(GDPath path);
	
	/**
	 * Method to get the string in snippet to display of this path
	 * @param path, the current path which display on maps
	 * @return the snippet string of this path
	 */
	public abstract String getSnippet(GDPath path);
	
	/**
	 * is a info windows or not ?
	 * for custom snippet it will be true, else true
	 * @return yes or no
	 */
	public abstract boolean isInfoWindows();
	
	/**
	 * Display custom snippet if isInfoWindows is true
	 * Construct your custom view here
	 * @param marker, the current marker
	 * @param direction, the current direction
	 * @param legs, the current legs
	 * @param path, the current path
	 */
	public abstract void setContents(Marker marker,GDirection direction, GDLegs legs, GDPath path);
}
