package de.atlasmc.inventory.meta;

public interface DamageableMeta extends ItemMeta {
	
	public DamageableMeta clone();
	public void setDamage(int damage);
	public int getDamage();
	public boolean hasDamage();

}
