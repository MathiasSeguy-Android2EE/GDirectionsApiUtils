package com.android2ee.formation.librairies.google.map.utils.direction;

public enum Mode {
		
	MODE_DRIVING("driving"),
	MODE_WALKING("walking"),
	MODE_BICYCLING("bicycling"),
	MODE_TRANSIT("transit");
	
	private final String name;       

    private Mode(String s) {
        name = s;
    }

    public boolean equalsName(String otherName){
        return (otherName == null)? false:name.equals(otherName);
    }

    public String toString(){
       return name;
    }
}
