package com.android2ee.formation.librairies.google.map.utils.direction;

public enum UnitSystem {
		
	US_METRIC("metric"),
	US_IMPERIAL("imperial");
	
	private final String name;       

    private UnitSystem(String s) {
        name = s;
    }

    public boolean equalsName(String otherName){
        return (otherName == null)? false:name.equals(otherName);
    }

    public String toString(){
       return name;
    }
}
