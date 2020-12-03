package de.atlasmc.inventory.meta;

public interface DamageableMeta extends ItemMeta {
	
	public int getMaxDurability();
	public DamageableMeta clone();
	public void setDurablity(int durablity);
	public int getDurablity();

}
