package com.android2ee.formation.librairies.google.map.utils.direction.model;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author florian
 * Class GDColor
 * contains the color of path and pin for the api
 * 
 */
public class GDColor implements Parcelable{
	
	/**
	 *  value for the color of path (int) resource
	 */
	public Integer colorLine;
	/**
	 *  value for the color of pin (float) BitmapDescriptorFactory.X
	 */
	public Float colorPin;
	
	
	/**
	 * Constructor of GDColor
	 * @param colorLine : the color of path
	 * @param colorPin : color of pin
	 */
	public GDColor(Integer colorLine, Float colorPin) {
		super();
		this.colorLine = colorLine;
		this.colorPin = colorPin;
	};


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(this.colorLine);
		dest.writeValue(this.colorPin);
	}

	protected GDColor(Parcel in) {
		this.colorLine = (Integer) in.readValue(Integer.class.getClassLoader());
		this.colorPin = (Float) in.readValue(Float.class.getClassLoader());
	}

	public static final Creator<GDColor> CREATOR = new Creator<GDColor>() {
		@Override
		public GDColor createFromParcel(Parcel source) {
			return new GDColor(source);
		}

		@Override
		public GDColor[] newArray(int size) {
			return new GDColor[size];
		}
	};
}
