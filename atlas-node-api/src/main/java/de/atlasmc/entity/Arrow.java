package de.atlasmc.entity;

import de.atlasmc.Color;
import de.atlasmc.inventory.component.PotionContentsComponent;

public interface Arrow extends AbstractArrow {

	Color getColor();
	
	void setColor(Color color);
	
	int getColorRGB();
	
	void setColorRGB(int color);
	
	PotionContentsComponent getPotionContents();
	
	void setPotionContents(PotionContentsComponent contents);
	
}
