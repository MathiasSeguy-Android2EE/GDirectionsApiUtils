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

import java.util.List;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 * This class aims toThis class aims to define a GoogleDirection Path which is bound to the JSon structure
 *        returned by the webService :
 *        "http://maps.googleapis.com/maps/api/directions/json?" + "origin=" + start.latitude + ","
 *        + start.longitude + "&destination=" + end.latitude + "," + end.longitude
 *        + "&sensor=false&units=metric&mode=driving";
 */
public class GDPath implements Parcelable{

	/**
	 * A path is a list of GDPoints
	 */
	List<GDPoint> mPath;
	/**
	 * The distance of the path
	 */
	int mDistance;
	/**
	 * The duration of the path
	 */
	int mDuration;
	/**
	 * The travel mode of the path
	 */
	String mTravelMode;
	/**
	 * The Html text associated with the path
	 */
	String mHtmlText;
	/**
	 * The weight of the path
	 * (number of points)
	 * It's used to reduce the numbers of dots displayed
	 */
	int weight=-1;

	/**
	 * @param path The list of GDPoint that makes the path
	 */
	public GDPath(List<GDPoint> path) {
		super();
		this.mPath = path;
	}

	/**
	 * @return the mPath
	 */
	public final List<GDPoint> getPath() {
		return mPath;
	}

	/**
	 * @param mPath the mPath to set
	 */
	public final void setPath(List<GDPoint> mPath) {
		this.mPath = mPath;
	}

	/**
	 * @return the mPath
	 */
	public final List<GDPoint> getmPath() {
		return mPath;
	}

	/**
	 * @return the mDistance
	 */
	public final int getDistance() {
		return mDistance;
	}

	/**
	 * @return the mDuration
	 */
	public final int getDuration() {
		return mDuration;
	}

	/**
	 * @return the mTravelMode
	 */
	public final String getTravelMode() {
		return mTravelMode;
	}

	/**
	 * @return the mHtmlText
	 */
	public final String getHtmlText() {
		return mHtmlText;
	}

	/**
	 * @param mPath the mPath to set
	 */
	public final void setmPath(List<GDPoint> mPath) {
		this.mPath = mPath;
	}

	/**
	 * @param distance the mDistance to set
	 */
	public final void setDistance(int distance) {
		this.mDistance = distance;
	}

	/**
	 * @param duration the mDuration to set
	 */
	public final void setDuration(int duration) {
		this.mDuration = duration;
	}

	/**
	 * @param travelMode the mTravelMode to set
	 */
	public final void setTravelMode(String travelMode) {
		this.mTravelMode = travelMode;
	}

	/**
	 * @param htmlText the mHtmlText to set
	 */
	public final void setHtmlText(String htmlText) {
		this.mHtmlText = htmlText;
	}

	/**
	 *
	 * @return the weight of this path
	 */
	public int getWeight() {
		if(weight==-1){
			if(mPath!=null){
				weight=mPath.size();
			}else{
				weight=0;
			}

		}
		return weight;
	}

	/* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
	@Override
	public String toString() {
		StringBuilder strB=new StringBuilder("GPath\r\n");
		for(GDPoint point:mPath) {
			strB.append(point.toString());
			strB.append(point.toString());
			strB.append(",");
		}
		return strB.toString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeTypedList(this.mPath);
		dest.writeInt(this.mDistance);
		dest.writeInt(this.mDuration);
		dest.writeString(this.mTravelMode);
		dest.writeString(this.mHtmlText);
		dest.writeInt(this.weight);
	}

	protected GDPath(Parcel in) {
		this.mPath = in.createTypedArrayList(GDPoint.CREATOR);
		this.mDistance = in.readInt();
		this.mDuration = in.readInt();
		this.mTravelMode = in.readString();
		this.mHtmlText = in.readString();
		this.weight = in.readInt();
	}

	public static final Creator<GDPath> CREATOR = new Creator<GDPath>() {
		@Override
		public GDPath createFromParcel(Parcel source) {
			return new GDPath(source);
		}

		@Override
		public GDPath[] newArray(int size) {
			return new GDPath[size];
		}
	};
}
