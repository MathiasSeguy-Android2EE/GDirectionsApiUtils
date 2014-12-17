package com.android2ee.formation.librairies.google.map.utils.direction.util;

public enum Avoid {
	
	AVOID_HIGHWAYS("highways"),
	AVOID_FERRIES("ferries"),
	AVOID_TOLLS("tolls");
	
	private final String name;       

    private Avoid(String s) {
        name = s;
    }

    public boolean equalsName(String otherName){
        return (otherName == null)? false:name.equals(otherName);
    }

    public String toString(){
       return name;
    }
}
