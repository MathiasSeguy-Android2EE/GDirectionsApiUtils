/**<ul>
 * <li>GoogleMapSample</li>
 * <li>com.android2ee.formation.librairies.google.map.utils.direction.model</li>
 * <li>13 sept. 2013</li>
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
package com.android2ee.formation.librairies.google.map.utils.direction.model;

import java.util.List;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 * This class aims to describes a GoogleDirection Legs which is bound to the JSon structure
 *        returned by the webService :
 *        "http://maps.googleapis.com/maps/api/directions/json?" + "origin=" + start.latitude + ","
 *        + start.longitude + "&destination=" + end.latitude + "," + end.longitude
 *        + "&sensor=false&units=metric&mode=driving";
 */
public class GDLegs {
	/**
	 * A GDLegs is a list of GDPath
	 */
	List<GDPath> mPathsList;
	/**
	 * The distance of the leg
	 */
	int mDistance;
	/**
	 * The duration of the leg
	 */
	int mDuration;
	/**
	 * Starting address
	 */
	String mStartAddress;
	/**
	 * Ending Address
	 */
	String mEndAddress;

	/**
	 * @param pathsList
	 */
	public GDLegs(List<GDPath> pathsList) {
		super();
		this.mPathsList = pathsList;
	}

	/**
	 * @return the mLegsList
	 */
	public final List<GDPath> getPathsList() {
		return mPathsList;
	}

	/**
	 * @param mLegsList the mLegsList to set
	 */
	public final void setPathsList(List<GDPath> mPathsList) {
		this.mPathsList = mPathsList;
	}

	/**
	 * @return the mDistance
	 */
	public final int getmDistance() {
		return mDistance;
	}

	/**
	 * @param mDistance the mDistance to set
	 */
	public final void setmDistance(int mDistance) {
		this.mDistance = mDistance;
	}

	/**
	 * @return the mDuration
	 */
	public final int getmDuration() {
		return mDuration;
	}

	/**
	 * @param mDuration the mDuration to set
	 */
	public final void setmDuration(int mDuration) {
		this.mDuration = mDuration;
	}

	/**
	 * @return the mStartAddress
	 */
	public final String getmStartAddress() {
		return mStartAddress;
	}

	/**
	 * @param mStartAddress the mStartAddress to set
	 */
	public final void setmStartAddress(String mStartAddress) {
		this.mStartAddress = mStartAddress;
	}

	/**
	 * @return the mEndAddress
	 */
	public final String getmEndAddress() {
		return mEndAddress;
	}

	/**
	 * @param mEndAddress the mEndAddress to set
	 */
	public final void setmEndAddress(String mEndAddress) {
		this.mEndAddress = mEndAddress;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder strB=new StringBuilder("GLegs\r\n");
		for(GDPath path:mPathsList) {
			strB.append(path.toString());
			strB.append("\r\n");
		}
		return strB.toString();
	}
}
