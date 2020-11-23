package de.atlasmc.inventory.meta;

import de.atlasmc.Color;

public interface LeatherArmorMeta extends DamageableMeta {
	
	public LeatherArmorMeta clone();
	public Color getColor();
	public void setColor(Color color);

}
