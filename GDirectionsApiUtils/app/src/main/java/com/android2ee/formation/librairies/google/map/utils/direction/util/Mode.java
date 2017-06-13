package com.android2ee.formation.librairies.google.map.utils.direction.util;

/**
 * @author florian
 * Mode enum
 * DRIVING, WALKING, BICYCLING, TRANSIT
 * 
 */
public enum Mode {
		
	MODE_DRIVING("driving"),
	MODE_WALKING("walking"),
	MODE_BICYCLING("bicycling"),
	MODE_TRANSIT("transit");
	
	/**
	 * contains value in string of enum
	 */
	private final String name;       

	/**
	 * Constructor of enum 
	 * @param s, string value
	 */
    private Mode(String s) {
        name = s;
    }

    /**
     * Compare two Mode
     * @param otherName
     * @return if same or not
     */
    public boolean equalsName(String otherName){
        return (otherName == null)? false:name.equals(otherName);
    }

    /**
     * @return string value
     */
    public String toString(){
       return name;
    }
}
