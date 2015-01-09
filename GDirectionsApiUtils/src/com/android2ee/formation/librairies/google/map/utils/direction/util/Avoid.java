package com.android2ee.formation.librairies.google.map.utils.direction.util;

/**
 * @author florian
 * Enum of parameter Avoid
 * HIGHWAYS, FERRIES, TOLLS possible
 */
public enum Avoid {
	
	AVOID_HIGHWAYS("highways"),
	AVOID_FERRIES("ferries"),
	AVOID_TOLLS("tolls");
	
	/**
	 *  contains value in string of enum
	 */
	private final String name;       

	/**
	 * Constructor of enum with string value in parameter
	 * @param s
	 */
    private Avoid(String s) {
        name = s;
    }

    /**
     * Compare two Avoid
     * @param otherName
     * @return if same or not
     */
    public boolean equalsName(String otherName){
        return (otherName == null)? false:name.equals(otherName);
    }

    /**
	 * @return string value
     */
    public String toString() {
       return name;
    }
}
