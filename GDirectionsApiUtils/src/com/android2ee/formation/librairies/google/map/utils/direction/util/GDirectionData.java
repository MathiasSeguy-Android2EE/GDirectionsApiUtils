package com.android2ee.formation.librairies.google.map.utils.direction.util;

import com.google.android.gms.maps.model.LatLng;

/**
 * 
 * @param mode
 *            The direction mode (driving,walking...)
 * @param waypoints
 *            The waypoints
 * @param alternative
 *            alternatives routes
 * @param avoid
 *           avoid route type ( highways, tolls, ferries)
 * @param language
 *            The language (fr, es, ...) need language code here
 * @param us
 *             The unit system (metric , imperial)
 * @param region
 *            The region (fr, es, ...) need country code here 
 * @param departure time
 *            the departure time only if we are in transit or driving mode
 * @param arrival time
 *            The arrival time wanted, only if we are in transit mode
**/

public class GDirectionData {
	
	LatLng start;
	LatLng end;
	Mode mode;
	String waypoints;
	boolean alternative;
	Avoid avoid;
	String language;
	UnitSystem us;
	String region;
	String departure_time; 
	String arrival_time;
	
	
	/**
	 * Constructor private, we can just build this class by the Builder
	 * must have a startPoint and endPoint
	 * @param start
	 * @param end
	 */
	private GDirectionData(LatLng start, LatLng end) {
		super();
		this.start = start;
		this.end = end;
	}
	
	public LatLng getStart() {
		return start;
	}

	public LatLng getEnd() {
		return end;
	}
	
	public Mode getMode() {
		return mode;
	}

	public String getWaypoints() {
		return waypoints;
	}

	public boolean isAlternative() {
		return alternative;
	}

	public Avoid getAvoid() {
		return avoid;
	}

	public String getLanguage() {
		return language;
	}

	public UnitSystem getUs() {
		return us;
	}

	public String getRegion() {
		return region;
	}

	public String getDeparture_time() {
		return departure_time;
	}

	public String getArrival_time() {
		return arrival_time;
	}

	/**
	 * Builder
	 * @author florian
	 *
	 */
	public static class Builder {
		
		LatLng start;
		LatLng end;
		Mode mode;
		String waypoints;
		boolean alternative;
		Avoid avoid;
		String language;
		UnitSystem us;
		String region;
		
		/**
		 * Constructor
		 * @param start
		 * @param end
		 */
		public Builder(LatLng start, LatLng end) {
			super();
			this.start = start;
			this.end = end;
		}
		
		public LatLng getStart() {
			return start;
		}
		
		public LatLng getEnd() {
			return end;
		}
		
		public Mode getMode() {
			return mode;
		}
		
		public Builder setMode(Mode mode) {
			this.mode = mode;
			return this;
		}
		
		public String getWaypoints() {
			return waypoints;
		}
		
		public Builder setWaypoints(String waypoints) {
			this.waypoints = waypoints;
			return this;
		}
		
		public boolean isAlternative() {
			return alternative;
		}
		
		public Builder setAlternative(boolean alternative) {
			this.alternative = alternative;
			return this;
		}
		
		public Avoid getAvoid() {
			return avoid;
		}
		
		public Builder setAvoid(Avoid avoid) {
			this.avoid = avoid;
			return this;
		}
		
		public String getLanguage() {
			return language;
		}
		
		public Builder setLanguage(String language) {
			this.language = language;
			return this;
		}
		
		public UnitSystem getUs() {
			return us;
		}
		
		public Builder setUs(UnitSystem us) {
			this.us = us;
			return this;
		}
		
		public String getRegion() {
			return region;
		}
		
		public Builder setRegion(String region) {
			this.region = region;
			return this;
		}
		
		/**
		 * Build Method
		 * @return
		 */
		public GDirectionData build() {
			
			GDirectionData result = new GDirectionData(start, end);
			result.mode = mode;
			result.waypoints = waypoints;
			result.alternative = alternative;
			result.avoid = avoid;
			result.language = language;
			result.us = us;
			result.region = region;
			result.departure_time = null;
			result.arrival_time = null;
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
		
		public abstract GDirectionData build();
		
	}
	
	/**
	 * DepartureCreator
	 * @author florian
	 *
	 */
	public static class DepartureCreator extends Creator {
		
		String departure_time;

		public DepartureCreator() {
		} 
		
		public DepartureCreator(Builder builder) {
			setBuilder(builder);
		}
		
		public String getDeparture_time() {
			return departure_time;
		}

		public DepartureCreator setDeparture_time(String departure_time) {
			this.departure_time = departure_time;
			return this;
		}

		@Override
		public GDirectionData build() {
			checkBuilder();
			if (mBuilder.getMode() != Mode.MODE_TRANSIT && mBuilder.getMode() != Mode.MODE_DRIVING) {
				throw new IllegalArgumentException("Mode is not correct requires a Transit/Driving mode for the DepartureCreator");
			}
			GDirectionData data = mBuilder.build();
			data.departure_time = departure_time;
			return data;
		}
		
		
	}

	/**
	 * TransitCreator
	 * @author florian
	 *
	 */
	public static class TansitCreator extends Creator {
		
		String departure_time; 
		String arrival_time;
		
		public TansitCreator() {
		} 
		
		public TansitCreator(Builder builder) {
			setBuilder(builder);
		}
		
		public String getDeparture_time() {
			return departure_time;
		}

		public TansitCreator setDeparture_time(String departure_time) {
			this.departure_time = departure_time;
			return this;
		}

		public String getArrival_time() {
			return arrival_time;
		}

		public TansitCreator setArrival_time(String arrival_time) {
			this.arrival_time = arrival_time;
			return this;
		}

		@Override
		public GDirectionData build() {
			checkBuilder();
			if (mBuilder.getMode() == Mode.MODE_TRANSIT) {
				throw new IllegalArgumentException("Mode is not correct requires a Transit mode for the TransitCreator");
			}
			GDirectionData data = mBuilder.build();
			data.departure_time = departure_time;
			data.arrival_time = arrival_time;
			return data;
		}
	}

}
