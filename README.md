GDirectionsApiUtils
===================

This project aims to simplify the usage of the Google Map Direction Rest Api
This project is done to help you using the Google Rest Api Direction : http://maps.googleapis.com/maps/api/directions/json?

How it works, definitly simply. You download the jar (gdirectionsapiutils.jar under GDirectionsApiUtils\bin\ ) in the libs folder of your application (under bin) and use it:
```JAVA

    public class MainActivity extends ActionBarActivity implements DCACallBack{
    /**
     * Get the Google Direction between mDevice location and the touched location using the     Walk
     * @param point
     */
    private void getDirections(LatLng point) {
         GDirectionsApiUtils.getDirection(mDcaCallBack, startPoint, endPoint,     GDirectionsApiUtils.MODE_WALKING);
    }

    /*
     * The callback
     * When the direction is built from the google server and parsed, this method is called and give you the expected direction
     */
    @Override
    public void onDirectionLoaded(List<GDirection> directions) {        
        // Display the direction or use the DirectionsApiUtils
        for(GDirection direction:directions) {
            Log.e("MainActivity", "onDirectionLoaded : Draw GDirections Called with path " + directions);
            GDirectionsApiUtils.drawGDirection(direction, mMap);
        }
    }
