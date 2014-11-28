package com.android2ee.formation.librairies.google.map.utils.direction;

import com.android2ee.formation.librairies.google.map.utils.direction.model.GDLegs;
import com.android2ee.formation.librairies.google.map.utils.direction.model.GDPath;
import com.android2ee.formation.librairies.google.map.utils.direction.model.GDirection;
import com.google.android.gms.maps.model.Marker;

public interface IGDFormatter {

	public abstract String getTitle(GDPath path);
	public abstract String getSnippet(GDPath path);
	
	public abstract boolean isInfoWindows();
	public abstract void setContents(Marker marker,GDirection direction, GDLegs legs, GDPath path);
}
