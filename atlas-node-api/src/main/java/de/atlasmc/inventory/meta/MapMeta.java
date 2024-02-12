package de.atlasmc.inventory.meta;

import de.atlasmc.Color;
import de.atlasmc.map.MapView;

public interface MapMeta extends ItemMeta {
	
	public MapMeta clone();

	public Color getColor();
	
	public int getMapId();
	
	public MapView getMapView();
	
	public boolean hasColor();
	
	public boolean hasMapView();
	
	public void setColor(Color color);
	
	public void setMapView(MapView view);
	
	public void setMapID(int mapID);
	
}
