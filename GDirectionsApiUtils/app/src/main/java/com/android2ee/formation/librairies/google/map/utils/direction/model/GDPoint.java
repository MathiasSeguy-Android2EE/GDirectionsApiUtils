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

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 *        This class aims to define a GoogleDirection Point which is bound to the JSon structure
 *        returned by the webService :
 *        "http://maps.googleapis.com/maps/api/directions/json?" + "origin=" + start.latitude + ","
 *        + start.longitude + "&destination=" + end.latitude + "," + end.longitude
 *        + "&sensor=false&units=metric&mode=driving";
 * This class is useless //todo remove it
 */
public class GDPoint implements Parcelable{
	/**
	 * The corresponding LatLng
	 * Not in the JSon Object. It's an helpful attribute
	 */
	private LatLng mLatLng = null;
	
	/**
	 * The builder
	 * @param coordinate retrieve from JSon
	 */
	public GDPoint(double lat,double lng) {
		super();
		mLatLng = new LatLng(lat,lng);
	}

	/**
	 * @return The LatLng Object linked with that point
	 */
	public LatLng getLatLng() {
		return mLatLng;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return mLatLng.toString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(this.mLatLng, flags);
	}

	protected GDPoint(Parcel in) {
		this.mLatLng = in.readParcelable(LatLng.class.getClassLoader());
	}

	public static final Creator<GDPoint> CREATOR = new Creator<GDPoint>() {
		@Override
		public GDPoint createFromParcel(Parcel source) {
			return new GDPoint(source);
		}

		@Override
		public GDPoint[] newArray(int size) {
			return new GDPoint[size];
		}
	};
}
