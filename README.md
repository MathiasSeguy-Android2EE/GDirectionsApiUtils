GDirectionsApiUtils
===================

This project aims to simplify the usage of the Google Map Direction Rest Api
This project is done to help you using the Google Rest Api Direction : http://maps.googleapis.com/maps/api/directions/json?

How it works, definitly simply. You download the jar (gdirectionsapiutils.jar under GDirectionsApiUtils\bin\ ) in the libs folder of your application (under bin) and use it:
```JAVA

    public class MainActivity extends ActionBarActivity implements DCACallBack{
    /**
     * Get the Google Direction between mDevice location and the touched location using the Walk
     * @param point
     */
    private void getDirections(LatLng startPoint, LatLng endPoint) {
    	GDirectionData.Builder builder = new GDirectionData.Builder(startPoint, endPoint)
    	 										.setMode(Mode.MODE_DRIVING)
    	 										.setAlternative(true)
    	 										.setAvoid(Avoid.AVOID_HIGHWAYS)
    	 										.setLanguage("fr")
    	 										.setUs(UnitSystem.US_METRIC);
    	 GDirectionData data = new GDirectionData.DepartureCreator(builder)
    	 										.setDeparture_time("now")
    	 										.build();
    	 GDirectionsApiUtils.getDirection(this, data);
    }

    /*
     * The callback
     * When the direction is built from the google server and parsed, this method is called and give you the expected direction
     */
    @Override
    public void onDirectionLoaded(List<GDirection> directions) { 
        ArrayList<GDColor> colors = new ArrayList<GDColor>();
    	colors.add(new GDColor(Color.BLACK, BitmapDescriptorFactory.HUE_VIOLET));
    	colors.add(new GDColor(Color.BLUE, BitmapDescriptorFactory.HUE_AZURE));
    	colors.add(new GDColor(Color.YELLOW, BitmapDescriptorFactory.HUE_YELLOW));
    	colors.add(new GDColor(Color.CYAN, BitmapDescriptorFactory.HUE_CYAN));
    	int MAX_COLOR = colors.size();
    	
    	
        for(int i = 0; i < directions.size(); i ++) {
        	GDirectionsApiUtils.drawGDirection(directions.get(i), map, colors.get(i % MAX_COLOR));
        }
    }

## Snippet

### Snippet Simple

   	To add the comments what you want in snippet you just ahve to add a formatter when you call the drawGDirection 

	GDirectionsApiUtils.drawGDirection(directions.get(i), map, colors.get(i % MAX_COLOR), new FormatterSimple());

	And implements this class
	public class FormatterSimple implements IGDFormatter {

		@Override
		public String getTitle(GDPath path) {
			return "Distance :" + path.getDistance();
		}
		
		@Override
		public String getSnippet(GDPath path) {
			return Html.fromHtml(path.getHtmlText()).toString();
		}

		@Override
		public boolean isInfoWindows() {
			return false;
		}

		@Override
		public void setContents(Marker marker, GDirection direction,
				GDLegs legs, GDPath path) {
		}
	};



### Custom Snippet
	
	If you want a custom layout for snippet cause you don't like the basic you can customize this layout by add this code when you get the map :
	
	map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
		        .getMap();
	map.setInfoWindowAdapter(new InfoWindowAdapter() {
			
			@Override
			public View getInfoWindow(Marker marker) {
				View view = getLayoutInflater().inflate(R.layout.custom_view, null, false);
				
				// get data associate with the marker id in hash map, here a GDPath, see below
				GDPath path = hashMap.get(marker.getId());
				// TODO implement code here
				return view;
			}
			
			@Override
			public View getInfoContents(Marker marker) {
				return null;
			}
		});


	And call drawGDirection with a new formatter 
        GDirectionsApiUtils.drawGDirection(directions.get(i), map, colors.get(i % MAX_COLOR), new FormatterComplex());


	Next implements this class

        public class FormatterComplex implements IGDFormatter {
		
		@Override
		public String getTitle(GDPath path) {
			return "Distance :" + path.getDistance();
		}
		
		@Override
		public String getSnippet(GDPath path) {
			return Html.fromHtml(path.getHtmlText()).toString();
		}

		@Override
		public boolean isInfoWindows() {
			// here the information that we want customize the layout
			return true;
		}

		@Override
		public void setContents(Marker marker, GDirection direction,
				GDLegs legs, GDPath path) {
			// put the data associate with the marker id
			hashMap.put(marker.getId(), path);
		}
	};
