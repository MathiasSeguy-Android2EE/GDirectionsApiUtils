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
	
	/**
	 *  start position to the request
	 */
	LatLng start;
	/**
	 *  end position to the request
	 */
	LatLng end;
	/**
	 *  mode The direction mode (driving,walking...)
	 */
	Mode mode;
	/**
	 *  waypoints, The waypoints do you want for the request
	 */
	String waypoints;
	/**
	 *  alternative, alternatives routes ?
	 */
	boolean alternative;
	/**
	 *  avoid, avoid route type ( highways, tolls, ferries)
	 */
	Avoid avoid;
	/**
	 *  language The language (fr, es, ...) need language code here
	 */
	String language;
	/**
	 *  us, The unit system (metric , imperial)
	 */
	UnitSystem us;
	/**
	 *  region, The region (fr, es, ...) need country code here 
	 */
	String region;
	/**
	 *  departure_time, the departure time only if we are in transit or driving mode
	 */
	String departure_time; 
	/**
	 *  arrival_time,  The arrival time wanted, only if we are in transit mode
	 */
	String arrival_time;
	
	
	/**
	 * Constructor private, we can just build this class by the Builder
	 * must have a startPoint and endPoint
	 * @param start  
	 * 				the start position
	 * @param end
	 * 				the end position
	 */
	private GDirectionData(LatLng start, LatLng end) {
		super();
		this.start = start;
		this.end = end;
	}
	
	/**
	 * Get start position
	 * @return start Position
	 */
	public LatLng getStart() {
		return start;
	}

	/**
	 * Get end position
	 * @return end position
	 */
	public LatLng getEnd() {
		return end;
	}
	
	/**
	 * Get mode
	 * @return mode
	 */
	public Mode getMode() {
		return mode;
	}

	/**
	 * get waypoints
	 * @return waypoints
	 */
	public String getWaypoints() {
		return waypoints;
	}

	/**
	 * Get alternative route
	 * @return alternative
	 */
	public boolean isAlternative() {
		return alternative;
	}

	/**
	 * Get avoid
	 * @return avoid
	 */
	public Avoid getAvoid() {
		return avoid;
	}

	/**
	 * Get language
	 * @return language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Get unit system
	 * @return unit system
	 */
	public UnitSystem getUs() {
		return us;
	}

	/**
	 * Get region
	 * @return region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * Get departure_time
	 * @return departure_time
	 */
	public String getDeparture_time() {
		return departure_time;
	}

	/**
	 * Get arrival_time
	 * @return arrival_time
	 */
	public String getArrival_time() {
		return arrival_time;
	}

	/**
	 * Builder
	 * @author florian
	 *
	 */
	public static class Builder {
		/**
		 * start position
		 */
		LatLng start;
		/**
		 * end position
		 */
		LatLng end;
		/**
		 *  mode The direction mode (driving,walking...)
		 */
		Mode mode;
		/**
		 *  waypoints, The waypoints do you want for the request
		 */
		String waypoints;
		/**
		 *  alternative, alternatives routes ?
		 */
		boolean alternative;
		/**
		 *  avoid, avoid route type ( highways, tolls, ferries)
		 */
		Avoid avoid;
		/**
		 * language, language type fr, es, etc.
		 */
		String language;
		/**
		 *  us, The unit system (metric , imperial)
		 */
		UnitSystem us;
		/**
		 * Region, region type fr, es, etc.
		 */
		String region;
		
		/**
		 * Constructor of the Builder, required start at end always
		 * @param start
		 * 				start position
		 * @param end
		 * 				end position
		 */
		public Builder(LatLng start, LatLng end) {
			super();
			this.start = start;
			this.end = end;
			
			this.mode = null;
			this.waypoints = null;
			this.alternative = false;
			this.avoid = null;
			this.language = null;
			this.us = null;
			this.region = null;
		}
		
		/**
		 * Get start position
		 * @return start position
		 */
		public LatLng getStart() {
			return start;
		}
		
		/**
		 * Get end position
		 * @return end position
		 */
		public LatLng getEnd() {
			return end;
		}
		
		/**
		 * Get mode
		 * @return mode
		 */
		public Mode getMode() {
			return mode;
		}
		
		/**
		 * Set Mode
		 * @param mode
		 * @return builder
		 */
		public Builder setMode(Mode mode) {
			this.mode = mode;
			return this;
		}
		
		/**
		 * Get waypoints
		 * @return waypoints
		 */
		public String getWaypoints() {
			return waypoints;
		}
		
		/**
		 * Set waypoints
		 * @param waypoints
		 * @return builder
		 */
		public Builder setWaypoints(String waypoints) {
			this.waypoints = waypoints;
			return this;
		}
		
		/**
		 * Get alternative
		 * @return alternative
		 */
		public boolean isAlternative() {
			return alternative;
		}
		
		/**
		 * Set Alternatives
		 * @param alternative
		 * @return builder
		 */
		public Builder setAlternative(boolean alternative) {
			this.alternative = alternative;
			return this;
		}
		
		/**
		 * Get avoid
		 * @return avoid
		 */
		public Avoid getAvoid() {
			return avoid;
		}
		
		/**
		 * Set Avoid
		 * @param avoid
		 * @return builder
		 */
		public Builder setAvoid(Avoid avoid) {
			this.avoid = avoid;
			return this;
		}
		
		/**
		 * Get language
		 * @return language
		 */
		public String getLanguage() {
			return language;
		}
		
		/**
		 * Set language
		 * @param language
		 * @return builder
		 */
		public Builder setLanguage(String language) {
			this.language = language;
			return this;
		}
		
		/**
		 * Get unit system
		 * @return unit system
		 */
		public UnitSystem getUs() {
			return us;
		}
		
		/**
		 * Set unit system
		 * @param us
		 * @return builder
		 */
		public Builder setUs(UnitSystem us) {
			this.us = us;
			return this;
		}
		
		/**
		 * Get region
		 * @return region
		 */
		public String getRegion() {
			return region;
		}
		
		/**
		 * Set region
		 * @param region
		 * @return builder
		 */
		public Builder setRegion(String region) {
			this.region = region;
			return this;
		}
		
		/**
		 * Build method, need to construct data to request gDirection of Google
		 * @return GDirectionData
		 */
		public GDirectionData build() {
			// create object
			GDirectionData result = new GDirectionData(start, end);
			// insert parameters given
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
