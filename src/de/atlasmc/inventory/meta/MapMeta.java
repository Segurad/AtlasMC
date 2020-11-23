package de.atlasmc.inventory.meta;

import de.atlasmc.Color;
import de.atlasmc.map.MapView;

public interface MapMeta extends ItemMeta {
	
	public MapMeta clone();
	public Color getColor();
	public String getLocationName();
	public int getMapId();
	public MapView getMapView();
	public boolean hasColor();
	public boolean hasLocationName();
	public boolean hasMapId();
	public boolean hasMapView();
	public boolean isScaling();
	public void setColor(Color color);
	public void setLocationName(String name);
	public void setMapId(int id);
	public void setMapView(MapView map);
	public void setScaling(boolean value);
	
}
