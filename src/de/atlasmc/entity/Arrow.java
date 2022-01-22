package de.atlasmc.entity;

import de.atlasmc.Color;

public interface Arrow extends AbstractArrow {

	public Color getColor();
	
	public void setColor(Color color);
	
	public int getColorRGB();
	
	public void setColor(int rgb);
	
}
