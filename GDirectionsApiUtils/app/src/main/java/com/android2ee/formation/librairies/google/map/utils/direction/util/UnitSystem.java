package com.android2ee.formation.librairies.google.map.utils.direction.util;

/**
 * @author florian
 * Unit System enum
 * METRIC, IMPERIAL
 * 
 */
public enum UnitSystem {
		
	US_METRIC("metric"),
	US_IMPERIAL("imperial");
	
	/**
	 *  contains value in string of enum
	 */
	private final String name;       

	/**
	 * Constructor 
	 * @param s, the string value
	 */
    private UnitSystem(String s) {
        name = s;
    }

    /**
     * Compare two UnitSystem
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
