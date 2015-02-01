package com.android2ee.formation.librairies.google.map.utils.direction.util;

import java.util.ArrayList;

import com.android2ee.formation.librairies.google.map.utils.direction.IGDFormatter;
import com.android2ee.formation.librairies.google.map.utils.direction.model.GDColor;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * 
 * @param formatter
 *           formatter, to know how to display the snippet on a marker 
 * @param colors (GDColor)
 *            The colors of line and marker
 * @param polylineoptions
 *           polyline options 
**/

public class GDirectionMapsOptions {
	
	/**
	 *  formatter snippet
	 */
	IGDFormatter formatter;
	/**
	 *  colors marker and line
	 */
	ArrayList<GDColor> colors;
	/**
	 *  line options
	 */
	PolylineOptions polylineOptions;
	
	/**
	 * Constructor private, we can just build this class by the Builder
	 */
	private GDirectionMapsOptions() {
		super();

	}
	
	
	/**
	 * get Formatter
	 * @return formatter
	 */
	public IGDFormatter getFormatter() {
		return formatter;
	}
	
	/**
	 * Set Formatter
	 * @param formatter
	 */
	public void setFormatter(IGDFormatter formatter) {
		this.formatter = formatter;
	}
	
	/**
	 * Get Colors
	 * @return colors
	 */
	public ArrayList<GDColor> getColors() {
		return colors;
	}
	
	/**
	 * Set Colors
	 * @param colors
	 */
	public void setColors(ArrayList<GDColor> colors) {
		this.colors = colors;
	}
	
	/**
	 * Get PolylineOption
	 * @return poylineOptions
	 */
	public PolylineOptions getPolylineOptions() {
		return polylineOptions;
	}
	
	/**
	 * Set PolylineOption
	 * @param polylineOptions
	 */
	public void setPolylineOptions(PolylineOptions polylineOptions) {
		this.polylineOptions = polylineOptions;
	}



	/**
	 * Builder
	 * @author florian
	 *
	 */
	public static class Builder {
		
		/**
		 *  formatter snippet
		 */
		IGDFormatter formatter;
		/**
		 *  colors marker and line
		 */
		ArrayList<GDColor> colors;
		/**
		 *  line options
		 */
		PolylineOptions polylineOptions;
		
		/**
		 * Constructor of the Builder
		 */
		public Builder() {
			super();
			
			this.formatter = null;
			this.colors = null;
			this.polylineOptions = null;
		}
		
		/**
		 * Get formatter
		 * @return formatter
		 */
		public IGDFormatter getFormatter() {
			return formatter;
		}
		
		/**
		 * Set Formatter
		 * @param formatter
		 * @return builder
		 */
		public Builder setFormatter(IGDFormatter formatter) {
			this.formatter = formatter;
			return this;
		}
		
		/**
		 * Get colors
		 * @return colors
		 */
		public ArrayList<GDColor> getColors() {
			return colors;
		}
		
		/**
		 * Set Colors
		 * @param colors
		 * @return builder
		 */
		public Builder setColors(ArrayList<GDColor> colors) {
			this.colors = colors;
			return this;
		}
		
		/**
		 * Set Colors
		 * @param color , add one color
		 * @return builder
		 */
		public Builder setColor(GDColor color) {
			this.colors = new ArrayList<>();
			this.colors.add(color);
			return this;
		}
		
		/**
		 * Get PolylineOption
		 * @return polylineOptions
		 */
		public PolylineOptions getPolylineOptions() {
			return polylineOptions;
		}
		
		/**
		 * Set PolylineOptions
		 * @param polylineOptions
		 * @return builder
		 */
		public Builder setPolylineOptions(PolylineOptions polylineOptions) {
			this.polylineOptions = polylineOptions;
			return this;
		}
		
		
		/**
		 * Build method, need to construct data to request gDirection of Google
		 * @return GDirectionData
		 */
		public GDirectionMapsOptions build() {
			// create object
			GDirectionMapsOptions result = new GDirectionMapsOptions();
			// insert parameters given
			result.formatter = formatter;
			result.colors = colors;
			result.polylineOptions = polylineOptions;
			return result; 
		}
		
	}
	
	/**
	 * Creator :)
	 * @author florian
	 *
	 */
	public abstract static class Creator {
		
		public Builder mBuilder;
		
		public void setBuilder(Builder builder) {
			if (mBuilder != builder) {
				mBuilder = builder;
			}
		}
		
		/**
		 * Check if Builder present else throw an error
		 */
		protected void checkBuilder() {
			if (mBuilder == null) {
		       throw new IllegalArgumentException("Creator requires a valid Builder object");
			}
		}
		
		public abstract GDirectionMapsOptions build();
		
	}
	
}
