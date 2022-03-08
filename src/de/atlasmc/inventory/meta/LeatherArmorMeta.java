package de.atlasmc.inventory.meta;

import de.atlasmc.Color;

public interface LeatherArmorMeta extends DamageableMeta {
	
	public LeatherArmorMeta clone();
	
	@Override
	public default Class<? extends LeatherArmorMeta> getInterfaceClass() {
		return LeatherArmorMeta.class;
	}
	
	public Color getColor();
	
	public void setColor(Color color);

}
